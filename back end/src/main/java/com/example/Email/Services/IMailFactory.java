package com.example.Email.Services;
public interface IMailFactory{
    IMail getMail(String type, MailDTO data) ;
}