package com.soundsync;

import java.util.*;
import com.google.gson.Gson;

import java.net.URI;
import java.util.Scanner;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import java.io.*;

public class hdfs_read_api extends BaseAPIViewSet {
    public hdfs_read_api(HttpReq request) {
        this.request = request;
    }

    @Override
    public void get(HttpReq request) throws Exception {
        HttpResp response = new HttpResp();
        Map<String, String> m = new HashMap<>();
        // Path of the file in HDFS 
        String uri = "hdfs://10.20.24.32:9000/test1/SoundSync_desc.txt";
        
        // Configuration
        Configuration conf = new Configuration();
        
        // Reading the Filesystem
        FileSystem fs = FileSystem.get(URI.create(uri),conf);
        
        // InputStream for reading the files in chunks or packets
        InputStream in = null;
        System.out.println("\n\nFile Content : \n");
        try{
            
            // Simple read txt
            in = fs.open(new Path(uri));
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            IOUtils.copyBytes(in, stream, 4096,false);
            String content = new String(stream.toByteArray());
            // System.out.println(content);
            m.put("File Content", content);
            Gson g = new Gson();
            response.HttpRes200(request, g.toJson(m).toString());
        }
        catch(Exception e)
        {
            System.out.println(e.getStackTrace());
        }
        finally{
            IOUtils.closeStream(in);
        }
        
    }

    @Override
    public void post(HttpReq request) throws Exception {
        HttpResp response = new HttpResp();
        Map<String, String> m = new HashMap<>();
        m.put("Response", "Post method not allowed");
        Gson g = new Gson();
        response.HttpRes200(request, g.toJson(m).toString());
    }
}
