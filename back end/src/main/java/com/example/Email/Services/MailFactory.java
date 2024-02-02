package com.example.Email.Services;

import org.springframework.stereotype.Service;

@Service
public class MailFactory implements IMailFactory{
    @Override
    public IMail getMail(String type, MailDTO data) {

        return switch (type) {
            case "default"  -> new Default(data)       ;
            case "sent"     -> new SentMails(data)     ;
            case "trash"    -> new TrashMails(data)    ;
            case "draft"    -> new Draft(data)         ;
            case "filter"   -> new FilteredMails(data) ;
            default         -> null                    ;
        };

    }
}
