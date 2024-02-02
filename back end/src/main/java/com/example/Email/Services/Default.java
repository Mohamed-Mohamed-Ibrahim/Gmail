package com.example.Email.Services;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Default implements IMail {
    private final int id;
    private final String date;
    private final String sender;
    private final Queue<String> receivers;
    private final int importance;
    private final String subject;
    private final String body;
    private final Queue<String> attachment;

    LocalDateTime currentDateTime = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Default(MailDTO data) {
        this.date = currentDateTime.format(formatter);
        this.sender = data.getSender();
        this.id = data.getId();
        this.receivers = data.getReceivers();
        this.importance = data.getImportance();
        this.subject = data.getSubject();
        this.body = data.getBody();
        this.attachment = data.getAttachment();
    }

    public void storeMails() throws JSONException {
        while (!receivers.isEmpty()) {
            String receiver = receivers.poll();
            Path path = Paths.get("D:\\" + receiver);

            if (Files.exists(path)) {
                Path folderPath = Paths.get("D:\\" + receiver + "\\DefaultMails");

                try {
                    Files.createDirectories(folderPath);
                } catch (Exception e) {
                    System.err.println("Failed to create folder: " + e.getMessage());
                }

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", this.id);
                jsonObject.put("sender", this.sender);
                jsonObject.put("date", this.date);
                JSONArray receiversArray = new JSONArray(this.receivers);
                jsonObject.put("receivers", receiversArray);
                jsonObject.put("importance", this.importance);
                jsonObject.put("subject", this.subject);
                jsonObject.put("body", this.body);
                JSONArray attachementsArray = new JSONArray(this.attachment);
                jsonObject.put("attachments", attachementsArray);

                String filePath = "D:\\" + receiver + "\\DefaultMails\\defaultMails.json";
                try (FileWriter fileWriter = new FileWriter(filePath, true)) {
                    fileWriter.write(jsonObject + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static boolean containsKeyValue(JsonNode jsonObject, String key, String value) {
        JsonNode jsonValue = jsonObject.get(key);
        return jsonValue != null && jsonValue.isTextual() && jsonValue.asText().contains(value);
    }

    @Override
    public List<JsonNode> search(String by, String value) {
        String sender = this.sender;
        String filePath = "D:\\" + sender + "\\DefaultMails\\defaultMails.json";

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
        String filePath = "D:\\" + sender + "\\DefaultMails\\defaultMails.json";

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
        String inputFilePath = "D:\\" + sender + "\\DefaultMails\\defaultMails.json";
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
