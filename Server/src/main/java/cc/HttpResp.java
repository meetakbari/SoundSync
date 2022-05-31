package cc;

import java.net.*;
import java.io.*;
import java.util.*;
import com.google.gson.Gson;

public class HttpResp {
    public Map<Integer, String> statsCode = new HashMap<Integer, String>() {
        {
            put(200, "OK");
            put(400, "Bad Request");
            put(401, "Unauthorized");
            put(403, "Forbidden");
            put(404, "Not Found");
            put(405, "Method Not Allowed");
            put(500, "Internal Server Error");
        }
    };

    void baseResponse(HttpReq request, Integer resStatusCode, String resPayload) throws Exception {
        OutputStream out = request.ClientSocket.getOutputStream();
        PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(out)));
        pw.println("HTTP/1.1 " + resStatusCode.toString() + " " + this.statsCode.get(resStatusCode));
        pw.println("Access-Control-Allow-Origin: *");
        pw.println("Content-Type: application/json");
        pw.print("Content-Length: ");
        pw.println(resPayload.length());
        pw.println("Connection: close");
        pw.println("");
        pw.println(resPayload);

        pw.flush();
        pw.close();
        out.close();
        request.InputDataStream.close();
        request.ClientSocket.close();
        System.out.println(
                request.Header.get("request_type") + " " + request.Header.get("path") + " " + resStatusCode.toString());
    }

    void HttpRes400(HttpReq request, String errorMessage) throws Exception {
        this.baseResponse(request, 400, errorMessage);
    }

    void HttpRes200(HttpReq request, String resData) throws Exception {
        this.baseResponse(request, 200, resData);
    }

    void HttpRes404(HttpReq request) throws Exception {
        this.baseResponse(request, 404, "errorMessage");
    }

    void HttpRes500(HttpReq request) throws Exception {
        this.baseResponse(request, 500, "errorMessage");
    }
}