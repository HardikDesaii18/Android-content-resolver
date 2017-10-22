package com.example.hardikdesaii.contentresolverdemo;


public class SmsData
{
    int id;
    public String sender;
    public String message;
    public String date;

    SmsData(int id,String sender,String message,String date)
    {
        this.id=id;
        this.sender=sender;
        this.message=message;
        this.date=date;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getMessage() {
        return message;
    }

    public String getSender() {
        return sender;
    }
}
