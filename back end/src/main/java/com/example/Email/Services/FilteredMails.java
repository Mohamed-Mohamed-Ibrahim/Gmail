package com.example.Email.Services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class FilteredMails implements IMail {
    private final String date;
    private final int id;
    private final String sender;
    private final Queue<String> receivers;
    private final int importance;
    private final String subject;
    private final String body;

    public FilteredMails(MailDTO data) {
        this.date = data.getDate();
        this.sender = data.getSender();
        this.id = data.getId();
        this.receivers = data.getReceivers();
        this.importance = data.getImportance();
        this.subject = data.getSubject();
        this.body = data.getBody();
    }

    @Override
    public void storeMails() throws JSONException {
        // Implementation for storing filtered mails, if needed.
    }

    private static boolean containsKeyValue(JsonNode jsonObject, String key, String value) {
        JsonNode jsonValue = jsonObject.get(key);
        return jsonValue != null && jsonValue.isTextual() && jsonValue.asText().contains(value);
    }

    @Override
    public List<JsonNode> search(String by, String value) {
        String sender = this.sender;
        String filePath = "D:\\" + sender + "\\FilteredMails\\filteredMails.json";

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

        String filePath = "D:\\" + sender + "\\FilteredMails\\filteredMails.json";

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
        // Implementation for filtering and writing to a file, if needed.
    }
}
