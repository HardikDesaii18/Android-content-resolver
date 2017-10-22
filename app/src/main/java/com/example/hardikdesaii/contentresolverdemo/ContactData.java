package com.example.hardikdesaii.contentresolverdemo;


public class ContactData
{   public String ContactImage;
    public String ContactName;
    public String ContactNumber;
    public String ContactEmail;

    ContactData(String ContactImage,String ContactName,String ContactNumber,String ContactEmail)
    {
        this.ContactEmail=ContactEmail;
        this.ContactImage=ContactImage;
        this.ContactName=ContactName;
        this.ContactNumber=ContactNumber;

    }
    public String getContactEmail() {
        return ContactEmail;
    }

    public void setContactEmail(String contactEmail) {
        ContactEmail = contactEmail;
    }

    public String getContactImage() {
        return ContactImage;
    }

    public void setContactImage(String contactImage) {
        this.ContactImage = ContactImage;
    }

    public String getContactName() {
        return ContactName;
    }

    public void setContactName(String contactName) {
        ContactName = contactName;
    }

    public String getContactNumber() {
        return ContactNumber;
    }

    public void setContactNumber(String contactNumber) {
        ContactNumber = contactNumber;
    }


}
