package com.example.Email.Services;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@Service
public class ContactsFacade {

    private ArrayList<Contact> contacts;

    public ContactsFacade() {
        this.contacts = new ArrayList<>();
    }

    public ContactsFacade(ArrayList<Contact> contacts) {
        this.contacts = contacts;
    }

    public void addContact(Contact contact){
        contacts.add(contact);
        this.storeContacts();
    }
    public void editContact(Contact newContact,String oldName){
        if( oldName.isEmpty() ){
            this.addContact( newContact );
            return;
        }

        for(Contact contact : contacts){
            if(contact.getName().equals(oldName)){
                contact.setName(newContact.getName());
                contact.setEmail(newContact.getEmail());
                this.storeContacts();
                break;
            }
        }
    }
    public void deleteContact(String nameOfContactTobeDeleted){
        for(Contact contact : contacts){
            if(contact.getName().equals(nameOfContactTobeDeleted)){
                contacts.remove(contact);
                this.storeContacts();
                break;
            }
        }
    }
    public ArrayList<Contact> searchContacts(String searchTerm) {
        return contacts.stream()
                .filter(contact -> contact.getName().contains(searchTerm))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Contact> sortContactsByName() {
        return contacts.stream()
                .sorted(Comparator.comparing(Contact::getName))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Contact> getContacts() {
        return contacts;
    }

    public void listContacts() {
        for (Contact contact : contacts) {
            System.out.println(contact);
        }
    }

    public void storeContacts(){

        String filePath = "D:/" + contacts.get(0).getUser() + "/Contacts/contacts.json";

        Path fileToDelete = Paths.get(filePath);

        if (Files.exists(fileToDelete)) {
            try {
                Files.delete(fileToDelete);
                System.out.println("File deleted successfully.");
            } catch (IOException e) {
                System.err.println("Failed to delete the file: " + e.getMessage());
            }
        } else {
            System.out.println("File does not exist at path: " + filePath);
        }

        try {
            int size = contacts.size();

            for ( int i=0; i<size; i++) {

                Contact contact = contacts.get(i);

                String receiver = contact.getUser();

                Path path = Paths.get("D:/" + receiver);

                if (Files.exists(path)) {
                    Path folderPath = Paths.get("D:/" + receiver + "/Contacts");

                    try {
                        Files.createDirectories(folderPath);
                    } catch (Exception e) {
                        System.out.println("Failed to create folder: " + e.getMessage());
                    }

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("name", contact.getName());
                    JSONArray emailsArrayJson = new JSONArray(contact.getEmail());
                    jsonObject.put("emails", emailsArrayJson);
                    jsonObject.put("user", contact.getUser());

                    filePath = "D:/" + receiver + "/Contacts/contacts.json";

                    try ( FileWriter fileWriter = new FileWriter(filePath, true); ) {
                        fileWriter.write(jsonObject + "\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public ArrayList<Contact> showContacts(String sender) {

        String filePath = "D:\\" + sender + "\\Contacts\\contacts.json";

        ArrayList<Contact> jsonObjects = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            ObjectMapper objectMapper = new ObjectMapper();

            String line;
            while ((line = br.readLine()) != null) {
                JsonNode jsonObject = objectMapper.readTree(line);

                String name = String.valueOf(jsonObject.get("name"));
                name = name.replaceAll("\"", "");
                String user = String.valueOf(jsonObject.get("user"));
                user = user.replaceAll("\"", "");
                JsonNode emailNode = jsonObject.get("emails");

                ArrayList<String> emailList = new ArrayList<>();

                if (emailNode != null && emailNode.isArray()) {

                    for (JsonNode jsonObjectEmail : emailNode) {

                        String email = String.valueOf(jsonObjectEmail);
                        email = email.replaceAll("\"", "");
                        emailList.add(String.valueOf(email));

                    }

                }
                Contact contact = new Contact(name, emailList, user);
                jsonObjects.add(contact);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(jsonObjects);
        this.contacts = jsonObjects;

        return contacts;
    }
}

