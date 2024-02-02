package com.example.Email.Controller;

import org.json.JSONException  ;

import com.example.Email.Services.IMail;
import com.example.Email.Services.MailDTO;
import com.example.Email.Services.MailFactory;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired ;
import org.springframework.web.bind.annotation.CrossOrigin    ;
import org.springframework.web.bind.annotation.PostMapping    ;
import org.springframework.web.bind.annotation.RequestBody    ;
import org.springframework.web.bind.annotation.RestController ;

@RestController
@CrossOrigin
public class SendMailsController {
    @Autowired
    MailFactory mailFactory ;

    @PostMapping("/send")
    public void sendEmail(@RequestBody MailDTO data) throws JSONException {

        IMail mail1 = mailFactory.getMail("sent", data) ;
        IMail mail2 = mailFactory.getMail("default", data) ;

        mail1.storeMails() ;
        mail2.storeMails() ;

    }

}
