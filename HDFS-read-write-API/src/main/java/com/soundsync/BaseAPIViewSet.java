package com.soundsync;
import java.io.IOException;

public class BaseAPIViewSet implements Runnable {
    public HttpReq request;

    public void run() {
        try {
            String RequestType = this.request.Header.get("request_type");
            // System.out.println(RequestType);
            switch (RequestType) {
                case "GET":
                    this.get(this.request);
                    break;
                case "POST":
                    this.post(this.request);
                    break;
                case "PUT":
                    this.put(this.request);
                    break;
                case "DELETE":
                    this.delete(this.request);
                    break;
                default:
                    new HttpResp().HttpRes400(request, "Method not Allowed");
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void get(HttpReq request) throws Exception {
        new HttpResp().HttpRes400(request, "Method not Allowed");
    }

    public void post(HttpReq request) throws Exception {
        new HttpResp().HttpRes400(request, "Method not Allowed");
    }

    public void put(HttpReq request) throws Exception {
        new HttpResp().HttpRes400(request, "Method not Allowed");
    }

    public void delete(HttpReq request) throws Exception {
        new HttpResp().HttpRes400(request, "Method not Allowed");
    }

}
