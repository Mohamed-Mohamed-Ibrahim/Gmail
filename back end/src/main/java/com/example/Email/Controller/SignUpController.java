package com.example.Email.Controller;

import com.example.Email.Services.MailDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@CrossOrigin
public class SignUpController {


    private static boolean containsKeyValue(JsonNode jsonObject, String key, String value) {
        JsonNode jsonValue = jsonObject.get(key);
        return jsonValue != null && jsonValue.isTextual() && jsonValue.asText().contains(value);
    }

    @PostMapping("/signup")
    public int signup(@RequestBody MailDTO data) throws JSONException, IOException {

        String username = data.getUsername();
        String email = data.getEmail();
        String password = data.getPassword();

        Path folderPath = Paths.get("D:\\Users");
        Path userfolder = Paths.get("D:\\" + email) ;
        String filePath = "D:\\Users\\users.json";

        try {
            Files.createDirectories(folderPath);
        }
        catch (Exception e) {
            System.err.println("Failed to create Folder: " + e.getMessage());
        }

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            ObjectMapper objectMapper = new ObjectMapper();

            String line;
            while ((line = br.readLine()) != null) {
                JsonNode jsonObject = objectMapper.readTree(line);

                if (containsKeyValue(jsonObject, "email", email)) {
                    return 0 ;
                }
            }

        }
        catch (IOException e) {
            e.printStackTrace();
        }



        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", username);
        jsonObject.put("email", email);
        jsonObject.put("password", password);

        Files.createDirectories(userfolder);

        try (FileWriter fileWriter = new FileWriter(filePath, true)) {
            fileWriter.write(jsonObject + "\n");
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return 1 ;

    }

}
