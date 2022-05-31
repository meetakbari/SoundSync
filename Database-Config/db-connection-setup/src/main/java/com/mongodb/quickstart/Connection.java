package com.mongodb.quickstart;

import com.mongodb.Block;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Indexes;

import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.TextSearchOptions;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import java.util.Arrays;
import java.util.*;

public class Connection {
    public static void main(String[] args) {
        // String connectionString = System.getProperty("mongodb.uri"); 
        // Above didn't work because couldn't find a way to add environment variable in maven project in VScode
        // https://stackoverflow.com/questions/55461075/visual-studio-code-environment-variables-not-recognized-when-running-maven
        // Above link has no answer for 2 years and still counting!

        String connectionString = "mongodb+srv://jeet:jeet@betterdata.qtzrq.mongodb.net/javaMongo?retryWrites=true&w=majority";
        System.out.println(connectionString);
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            List<Document> databases = mongoClient.listDatabases().into(new ArrayList<>());
            databases.forEach(db -> System.out.println(db.toJson()));

            // Connecting to a specific collection
            MongoDatabase javaMongoDB = mongoClient.getDatabase("javaMongo");
            MongoCollection<Document> coll1Collection = javaMongoDB.getCollection("coll1");

            // Creating a document for a collection
            Document metaDataDoc = new Document("_id", new ObjectId());
            metaDataDoc.append("song_id", new ObjectId())
                        .append("Album", "Justice")
                       .append("Composer", "Alan Walker")
                       .append("Featuring", Arrays.asList("Khalid", "Chance the Rapper", "the Kid Laroi", "Dominic Fike", "Daniel Caesar", "Giveon", "Beam", "Burna Boy", "Benny Blanco", "Lil Uzi Vert", "Jaden", "Quavo", "DaBaby", "Tori Kelly"))
                       .append("Genre", Arrays.asList("Pop"))
                       .append("Lyricist", Arrays.asList("Gulzar", "Javed Akhtar", "Sameer Anjaan"))
                       .append("Original Release Date", "March 19, 2021")
                       .append("Language", "English")
                       .append("Number of songs", 16);
            
            
            // System.out.println(metaDataDoc);

            // insert operation
            // coll1Collection.insertOne(metaDataDoc);
            
            coll1Collection.createIndex(Indexes.text());
            Scanner sc = new Scanner(System.in);
            MongoCursor<Document> cursor = null;

            while(true)
            {
                System.out.println("Enter search query:");
                String str = sc.nextLine();
                if(str.equals("exit()")) break;


                coll1Collection.find(Filters.text(str, new TextSearchOptions().language("None")))
                              .projection(Projections.metaTextScore("score"))
                              .sort(Sorts.metaTextScore("score")).forEach((Document doc) -> System.out.println(doc));
            }
            sc.close();
        }

    }
}
