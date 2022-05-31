package com.soundsync;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Indexes;

import com.mongodb.MongoClient;  
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.TextSearchOptions;

import org.bson.Document;

import java.util.ArrayList;

import java.util.*;

import com.google.gson.Gson;


public class search_song extends BaseAPIViewSet {
    public search_song(HttpReq request) {
        this.request = request;
    }

    @Override
    public void get(HttpReq request) throws Exception {
        HttpResp response = new HttpResp();
        Map<String, ArrayList<Document>> m = new HashMap<>();

        MongoClient mongoClient = new MongoClient( "10.1.35.153" , 8000 );  
        
        try{    
            MongoDatabase db = mongoClient.getDatabase("sharddemo");  
            MongoCollection<Document> coll1Collection = db.getCollection("movies");
                        
            coll1Collection.createIndex(Indexes.text());
            Scanner sc = new Scanner(System.in);

            ArrayList<Document> query_output = new ArrayList<Document>();;

            System.out.println("Enter search query:");
            String str = request.QueryParams.get("search_query").toString();
            System.out.println(str);

            coll1Collection.find(Filters.text(str, new TextSearchOptions().language("None")))
                            .projection(Projections.metaTextScore("score"))
                            .sort(Sorts.metaTextScore("score")).forEach((Document doc) -> query_output.add(doc));

            sc.close();
            m.put("Response", query_output);
            Gson g = new Gson();
            response.HttpRes200(request, g.toJson(m).toString());
        }
        catch(Throwable e){
            Map<String, String> m1 = new HashMap<>();
            m1.put("Response", "Error Occured while connecting with the mongo");
            Gson g = new Gson();
            response.HttpRes200(request, g.toJson(m).toString());
            e.printStackTrace();
        }

        
    }

}
