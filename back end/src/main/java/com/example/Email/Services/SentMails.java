package com.example.Email.Services;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SentMails implements IMail {

    private final String date;
    private final String sender;
    private final int id;
    private final Queue<String> receivers;
    private final int importance;
    private final String subject;
    private final String body;
    private final Queue<String> attachment;

    LocalDateTime currentDateTime = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public SentMails(MailDTO data) {
        this.date = currentDateTime.format(formatter);
        this.sender = data.getSender();
        this.id = data.getId();
        this.receivers = data.getReceivers();
        this.importance = data.getImportance();
        this.subject = data.getSubject();
        this.body = data.getBody();
        this.attachment = data.getAttachment();
    }

    @Override
    public void storeMails() throws JSONException {
        Path folderPath = Paths.get("D:\\" + sender + "\\SentMails");

        try {
            Files.createDirectories(folderPath);
        } catch (Exception e) {
            System.err.println("Failed to create folder: " + e.getMessage());
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", this.id);
        jsonObject.put("sender", this.sender);
        JSONArray receiversArray = new JSONArray(this.receivers);
        jsonObject.put("receivers", receiversArray);
        jsonObject.put("date", this.date);
        jsonObject.put("importance", this.importance);
        jsonObject.put("subject", this.subject);
        jsonObject.put("body", this.body);
        JSONArray attachmentsArray = new JSONArray(this.attachment);
        jsonObject.put("attachments", attachmentsArray);

        String filePath = "D:\\" + sender + "\\SentMails\\sentMails.json";
        try (FileWriter fileWriter = new FileWriter(filePath, true)) {
            fileWriter.write(jsonObject.toString() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean containsKeyValue(JsonNode jsonObject, String key, String value) {
        JsonNode jsonValue = jsonObject.get(key);
        return jsonValue != null && jsonValue.isTextual() && jsonValue.asText().contains(value);
    }

    @Override
    public List<JsonNode> search(String by, String value) {
        String sender = this.sender;
        String filePath = "D:\\" + sender + "\\SentMails\\sentMails.json";

        List<JsonNode> filteredObjects = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            ObjectMapper objectMapper = new ObjectMapper();

            String line;
            while ((line = br.readLine()) != null) {
                JsonNode jsonObject = objectMapper.readTree(line);

                if (containsKeyValue(jsonObject, by, value)) {
                    filteredObjects.add(jsonObject);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return filteredObjects;
    }

    @Override
    public List<JsonNode> showMails() throws IOException {
        String sender = this.sender;

        String filePath = "D:\\" + sender + "\\SentMails\\sentMails.json";

        List<JsonNode> jsonObjects = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            ObjectMapper objectMapper = new ObjectMapper();

            String line;
            while ((line = br.readLine()) != null) {
                JsonNode jsonObject = objectMapper.readTree(line);
                jsonObjects.add(jsonObject);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonObjects;
    }

    @Override
    public void filterAndWriteToFile(String by, String value) {
        String sender = this.sender;
        String inputFilePath = "D:\\" + sender + "\\SentMails\\sentMails.json";
        String outputFolderPath = "D:\\" + sender + "\\FilteredMails\\";

        List<JsonNode> filteredObjects = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(inputFilePath))) {
            ObjectMapper objectMapper = new ObjectMapper();

            String line;
            while ((line = br.readLine()) != null) {
                JsonNode jsonObject = objectMapper.readTree(line);

                if (containsKeyValue(jsonObject, by, value)) {
                    filteredObjects.add(jsonObject);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            File outputFolder = new File(outputFolderPath);
            if (!outputFolder.exists()) {
                outputFolder.mkdirs();
            }

            String outputFilePath = outputFolderPath + "filteredMails.json";
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath, true))) {
                ObjectMapper objectMapper = new ObjectMapper();
                for (JsonNode filteredObject : filteredObjects) {
                    String jsonString = objectMapper.writeValueAsString(filteredObject);
                    writer.write(jsonString);
                    writer.newLine();
                }
            }

            System.out.println("Filtered objects written to: " + outputFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
