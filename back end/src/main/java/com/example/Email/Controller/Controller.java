package com.example.Email.Controller;

import com.example.Email.Services.Contact;
import com.example.Email.Services.ContactsFacade;
import com.example.Email.Services.ContactsSinglton;
import com.example.Email.Services.twoContacts;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


@RestController
@CrossOrigin()
public class Controller {

    ContactsFacade contactsFacade;

    Controller() {
        contactsFacade = ContactsSinglton.getInstance();
    }

    @PostMapping(value = "/contact/edit")
    public void editContact(@RequestBody twoContacts contacts) {

        contactsFacade.editContact(contacts.getCurContact(),contacts.getNewContact().getName());

    }

    @DeleteMapping(value = "/contact/delete")
    public void deleteContact(@RequestBody Contact contact)  {

        contactsFacade.deleteContact(contact.getName());

    }

    @PostMapping(value = "/contact/search")
    public void searchContact(@RequestBody Contact contact) {

        contactsFacade.searchContacts(contact.getName());

    }

    @GetMapping(value = "/contacts/{sender}")
    public ArrayList<Contact>  sendContacts(@PathVariable String sender) {

        return contactsFacade.showContacts(sender);

    }


}