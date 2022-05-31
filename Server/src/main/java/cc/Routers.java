package cc;

import java.net.Socket;
import java.util.HashMap;

public class Routers {
    public HttpReq request;

    public Routers(HttpReq request) {
        this.request = request;
    }

    public HashMap<String, Runnable> getRoutes() {
        return new HashMap<String, Runnable>() {
            {
                put("/api/v1/hdfswrite", new hdfs_write_api(request));
                put("/api/v1/search", new search_song(request));
            }
        };
    }

    public Runnable getObject() {
        String path = this.request.Header.get("path");
        return this.getRoutes().get(path);
    }
}
