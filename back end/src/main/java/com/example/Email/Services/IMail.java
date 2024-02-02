package com.example.Email.Services;

import com.fasterxml.jackson.databind.JsonNode;
import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public interface IMail {

    public void storeMails() throws JSONException ;

    public List<JsonNode> search(String by, String value) ;

    public List<JsonNode> showMails() throws IOException ;

    public void filterAndWriteToFile(String by, String value) ;


}

