package cc;

import java.io.*;
import java.net.*;
import java.util.*;
import com.google.gson.*;
import java.nio.charset.*;

public class HttpReq {
    public Socket ClientSocket;
    public Map<String, String> Header;
    public Map<String, String> QueryParams;
    public Map<String, Object> FormData;
    public JsonObject data;
    public DataInputStream InputDataStream;

    public HttpReq(Socket ClientSocket) throws Exception {
        this.ClientSocket = ClientSocket;
        this.Header = new HashMap<>();
        this.QueryParams = new HashMap<>();
        this.FormData = new HashMap<>();

        InputDataStream = new DataInputStream(new BufferedInputStream(ClientSocket.getInputStream()));

        // waite for data to come as stream of bytes
        while (InputDataStream.available() == 0) {
            // System.out.println("Waiting for data");
            continue;
        }
        List<Byte> ByteList = new ArrayList<Byte>();

        while (InputDataStream.available() > 0) {
            ByteList.add(InputDataStream.readByte());
        }

        byte ByteArray[] = new byte[ByteList.size()];
        Charset UTF8_CHARSET = Charset.forName("US-ASCII");
        int i = 0;
       
        for (Byte bb : ByteList) {
            ByteArray[i] = bb.byteValue();
            i++;
        }
        String s1 = new String(ByteArray, UTF8_CHARSET);
        // s1 +=" ";
        // System.out.println("s1 ========> " + " " + s1);
        // System.out.println("s1 ========> " + " " + s1.length());
        
        int headerlength = ExtractHeader(s1);
        
        if (headerlength > -1 && headerlength <= s1.length()) {
            
            s1 = s1.substring(headerlength);
            ExtractQueryParameters();
            if(headerlength < s1.length())
                ExtractBody(s1, Arrays.copyOfRange(ByteArray, headerlength, ByteArray.length));
        }


    }

    public int ExtractHeader(String headerString) {
        // headerString = headerString.strip();

        int HeaderLength = 0;
        String[] reqType = new String[] { "GET", "POST", "PUT", "DELETE" };
        for (String header : headerString.split("\n")) {
            // System.out.println(header);
            HeaderLength += header.length() + 1;

            if (header.isBlank() | header.isEmpty())
                break;

            String[] sarr = header.split(" ");
            if (Arrays.asList(reqType).contains(sarr[0])) {
                Header.put("request_type", sarr[0]);
                Header.put("path", sarr[1]);
                Header.put("http_type", sarr[2]);
            }
            if (sarr[0].startsWith("Content-Type") && sarr[1].startsWith("multipart/form-data")) {
                sarr[0] = sarr[0].substring(0, sarr[0].length() - 1);
                Header.put(sarr[0], sarr[1]);
                Header.put("form-boundry", sarr[2].substring(9));
            } else if (sarr[0].length() > 0) {
                sarr[0] = sarr[0].substring(0, sarr[0].length() - 1);
                Header.put(sarr[0], sarr[1]);
            }
            // if (sarr[0].startsWith("Content-Length")) {
            // break;
            // }

        }
        // System.out.println(Header.keySet());
        return HeaderLength;

    }

    public void ExtractQueryParameters() {
        System.out.println("inside here2");
        String[] Url = Header.get("path").replace("/?", "?").split("\\?");
        
        if (Url.length > 1) {
            Header.replace("path", Url[0]);
            String[] Params = Url[1].split("&");
            for (String param : Params) {
                QueryParams.put(param.split("=")[0], param.split("=")[1]);
            }
        }
    }

    public void ExtractBody(String bodyString, byte[] file_bytes) throws Exception {
        // S/ystem.out.println(bodyString);
        // bodyString = bodyString.strip();
        // System.out.println(bodyString.length() + " " + file_bytes.length);
        List<InMemoryFile> files = new ArrayList<InMemoryFile>();

        if (Header.get("Content-Type").startsWith("multipart/form-data")) {
            String Boundry = (Header.get("form-boundry"));

            for (String form_data : bodyString.split(Boundry)) {
                if (form_data.contains("Content-Disposition: form-data;")) {
                    String[] data = form_data.split("\n");

                    if (form_data.contains("filename")) {
                        String ContentType = data[2].split("Content-Type: ")[1];
                        String FileName = data[1].split("filename=")[1];
                        FileName = FileName.substring(1, FileName.length() - 2);

                        String FieldName = data[1].split("name")[1].split(";")[0];
                        FieldName = FieldName.substring(2, FieldName.length() - 1);

                        List<Byte> ByteList = new ArrayList<Byte>();
                        for (int i = 4; i < data.length - 1; i++) {
                            // FileContent += (data[i]) + "\n";
                            int ind = bodyString.indexOf(data[i]);
                            for (int index = ind; index < ind + data[i].length(); index++)
                                ByteList.add(file_bytes[index]);
                            ByteList.add((byte) '\n');
                        }

                        byte FileContent[] = new byte[ByteList.size()];
                        int i = 0;
                        for (Byte ByteObj : ByteList) {
                            FileContent[i] = ByteObj.byteValue();
                            i++;
                            if (i == ByteList.size())
                                break;
                        }

                        InMemoryFile file_object = new InMemoryFile(FileName, ContentType, FileContent);
                        files.add(file_object);

                        // FileOutputStream outputStream = new FileOutputStream(file_object.FileName);
                        // outputStream.write(file_object.FileContent);
                        // outputStream.close();

                    } else {
                        String form_key = data[1].split("\"")[1];
                        String form_value = data[3];
                        FormData.put(form_key, form_value);
                    }

                }
                if (files.size() > 0)
                    FormData.put("files", files);
            }
        } else {
            System.out.println("inside here");
            String payload = bodyString;
            JsonObject fromJson = new Gson().fromJson(payload, JsonObject.class);
            data = fromJson;
            System.out.println("data " + data);
        }
    }

}
