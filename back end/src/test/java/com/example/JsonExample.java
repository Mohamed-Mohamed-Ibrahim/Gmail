package com.example;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JsonExample {
    public static void main(String[] args) throws JSONException {

        Path folderPath = Paths.get("D:\\mohassan5286@gmail.com\\SentMails") ;

        try {

            Files.createDirectories(folderPath) ;
            System.out.println("Directory created successfully.") ;
        } catch (Exception e) {

            System.err.println("Failed to create directory: " + e.getMessage()) ;
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "John");
        jsonObject.put("age", 25);
        jsonObject.put("city", "New York");

        String filePath = "D:\\mohassan5286@gmail.com\\SentMails\\sentMails.json";

        try (FileWriter fileWriter = new FileWriter(filePath, true)) {
            fileWriter.write(jsonObject.toString()) ;
        }
        catch (IOException e) {
            e.printStackTrace() ;
        }

    }
}
