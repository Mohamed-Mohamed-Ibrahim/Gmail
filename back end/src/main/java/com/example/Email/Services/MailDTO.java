package com.example.Email.Services;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.LinkedList;
import java.util.Queue;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_DEFAULT)
public class MailDTO {
    private final int id;
    private final String by;
    private final String value;
    private final String type;
    private final String sender;
    private final Queue<String> receivers;
    private final String date;
    private final int importance;
    private final String subject;
    private final String body;
    private final Queue<String> attachment;

    private final String username ;
    private final String email;
    private final String password ;

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public MailDTO(int id, String date, String by, String value, String type, String sender, Queue<String> receivers, String date1, int importance, String subject, String body, Queue<String> attachment, String username , String email, String password) {
        this.id = id;
        this.date = date;
        this.by = by;
        this.value = value;
        this.type = type;
        this.sender = sender;
        this.receivers = new LinkedList<>(receivers);
        this.importance = importance;
        this.subject = subject;
        this.body = body;
        this.attachment = new LinkedList<>(attachment);
        this.username = username ;
        this.email = email;
        this.password = password ;
    }

    public String getDate() {
        return date;
    }

    public Queue<String> getAttachment() {
        return new LinkedList<>(attachment);
    }

    public int getId() {
        return id;
    }

    public String getBy() {
        return by;
    }

    public String getValue() {
        return value;
    }

    public String getType() {
        return type;
    }

    public String getSender() {
        return sender;
    }

    public Queue<String> getReceivers() {
        return new LinkedList<>(receivers);
    }

    public int getImportance() {
        return importance;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }
}