package com.example.Email.Controller;


import com.example.Email.Services.MailDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@CrossOrigin
@RestController
public class SignInController {

    private static boolean containsKeyValue(JsonNode jsonObject, String key, String value) {
        JsonNode jsonValue = jsonObject.get(key);
        return jsonValue != null && jsonValue.isTextual() && jsonValue.asText().equals(value);
    }

    @PostMapping("/signin")
    public String signIn(@RequestBody MailDTO data) {
        String email = data.getEmail();
        String password = data.getPassword();

        String filePath = "D:\\Users\\users.json";

        boolean check_email = false;
        boolean check_password = false;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            ObjectMapper objectMapper = new ObjectMapper();

            String line;
            while ((line = br.readLine()) != null) {
                JsonNode jsonObject = objectMapper.readTree(line);
                check_email = containsKeyValue(jsonObject, "email", email);
                check_password = containsKeyValue(jsonObject, "password", password);

                if (check_email) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (check_email && check_password) {
            return email;
        } else if (check_email && !check_password) {
            return "0";
        } else if (!check_email) {
            return "-1";

        }

        return null ;

    }

}