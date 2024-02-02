package com.example.Email.Controller;

import com.example.Email.Services.IMail;
import com.example.Email.Services.MailDTO;
import com.example.Email.Services.MailFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.*;

@RestController
@CrossOrigin
public class TrashSentMailsController {

    @Autowired
    MailFactory mailFactory;

    @PostMapping("/deleteSent")
    public void deleteDefaultPriority(@RequestBody MailDTO data) throws JSONException, IOException {
        String sender = data.getSender();
        int id = data.getId();

        IMail mail = mailFactory.getMail("trash", data);
        mail.storeMails();

        String sentFile = "D:\\" + sender + "\\SentMails\\sentMails.json";

        removeJsonObjectById(sentFile, id);
    }

    private void removeJsonObjectById(String filePath, int id) throws IOException {
        StringBuilder updatedJson = new StringBuilder();
        boolean objectRemoved = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                JSONObject obj = new JSONObject(line);

                if (obj.getInt("id") == id) {
                    objectRemoved = true;
                    continue;
                }

                updatedJson.append(line).append(",");
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        if (!objectRemoved) {
            updatedJson.deleteCharAt(updatedJson.length() - 1);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(updatedJson.toString());
        }

        if (!objectRemoved) {
            System.out.println("No object with id " + id + " found in the file.");
        } else {
            System.out.println("Object with id " + id + " successfully removed from the file.");
        }
    }
}