package cc;

import java.lang.Exception;
import java.util.*;
import com.google.gson.Gson;

public class UploadFile extends BaseAPIViewSet {

    public UploadFile(HttpReq request) {
        this.request = request;
    }

    @Override
    public void post(HttpReq request) throws Exception {
        request.FormData.entrySet().forEach(entry -> {
            System.out.println(entry.getKey() + " " + entry.getValue());
        });
        Map<String, String> m = new HashMap<>();
        m.put("message", "File Uploaded Successfully! ");
        Gson g = new Gson();
        new HttpResp().HttpRes200(request, new Gson().toJson(m).toString());
        // String filebase64 = request.data.get("file").getAsString();
        // byte[] decodedString =
        // Base64.getDecoder().decode(filebase64.getBytes("UTF-8"));
        // System.out.println(decodedString);
        // new HttpResp().HttpRes200(request, filebase64);
    }
}
