package com.example.Email.Services;

import org.springframework.stereotype.Service;

@Service
public class ContactsSinglton {

    private static ContactsFacade contactService = null;

    private ContactsSinglton(){}

    public static ContactsFacade getInstance() {

        if ( contactService == null ) {

            contactService = new ContactsFacade();

        }

        return contactService;

    }

}
