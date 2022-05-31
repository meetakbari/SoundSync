package cc;

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
            // MongoDatabase db = mongoClient.getDatabase("sharddemo");  
            // MongoCollection<Document> coll1Collection = db.getCollection("movies");
            MongoDatabase db = mongoClient.getDatabase("song_metadata");  
            MongoCollection<Document> coll1Collection = db.getCollection("metadata_collection");
                        
            coll1Collection.createIndex(Indexes.text());

            ArrayList<Document> query_output = new ArrayList<Document>();;

            // System.out.println("Enter search query:");
            String str = request.QueryParams.get("search_query").toString();
            if(str.equals("null"))
            {
                coll1Collection.find().forEach((Document doc) -> { 
                    doc.put("id", doc.get("_id").toString());
                    query_output.add(doc);
                });
                // query_output = coll1Collection.find().toArray();
                m.put("Response", query_output);
                Gson g = new Gson();
                response.HttpRes200(request, g.toJson(m).toString());
            }
            else
            {
                coll1Collection.find(Filters.text(str, new TextSearchOptions().language("None")))
                                .projection(Projections.metaTextScore("score"))
                                .sort(Sorts.metaTextScore("score")).
                                forEach((Document doc) ->{ 
                                    doc.put("id", doc.get("_id").toString());
                                    query_output.add(doc);} );
                
                // System.out.println(query_output.get(0).get("_id"));
                for (int i = 0; i < query_output.size(); i++){
                    // System.out.print(query_output.get(i).get("_id") + " ");
                    String ss = query_output.get(i).get("_id").toString();
                    // System.out.println(ss);
                    // query_output.get(i).get("song_id") = ss;
                }
                m.put("Response", query_output);
                Gson gs = new Gson();
                response.HttpRes200(request, gs.toJson(m).toString());
            }
            
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