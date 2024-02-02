package com.example.Email.Controller;

import com.example.Email.Services.IMail;
import com.example.Email.Services.MailDTO;
import com.example.Email.Services.MailFactory;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class DraftController {
    @Autowired
    MailFactory mailFactory ;

    @PostMapping("/draft")
    public void draftMails(@RequestBody MailDTO data) throws JSONException {

        IMail mail = mailFactory.getMail("draft", data) ;

        mail.storeMails() ;

    }

}
