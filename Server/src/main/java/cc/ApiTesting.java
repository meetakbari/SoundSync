package cc;

import java.util.*;
import com.google.gson.Gson;

public class ApiTesting extends BaseAPIViewSet {
    public ApiTesting(HttpReq request) {
        this.request = request;
    }

    @Override
    public void get(HttpReq request) throws Exception {
        HttpResp response = new HttpResp();
        Map<String, String> m = new HashMap<>();
        m.put("name", "Rahul");
        Gson g = new Gson();
        response.HttpRes200(request, g.toJson(m).toString());
    }

    @Override
    public void post(HttpReq request) throws Exception {
        // request.FormData.entrySet().forEach(entry -> {
        // System.out.println(entry.getKey() + " " + entry.getValue());
        // });
        HttpResp response = new HttpResp();
        Map<String, String> m = new HashMap<>();
        m.put("name", "Rahul frompost");
        Gson g = new Gson();
        response.HttpRes200(request, g.toJson(m).toString());
    }
}
