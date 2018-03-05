package com.example.paulwinjeba.kshop;

/**
 * Created by PAULWIN JEBA on 03-03-2018.
 */

public class MyProfile {
    private String Name;
    private String Phone_Number;
    private String Email_ID;
    private String Address;

    public MyProfile(){

    }
    public MyProfile(String name , String phone_number, String email_id, String address){
        Name = name;
        Phone_Number = phone_number;
        Email_ID = email_id;
        Address = address;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone_Number() {
        return Phone_Number;
    }

    public void setPhone_Number(String phone_Number) {
        Phone_Number = phone_Number;
    }

    public String getEmail_ID() {
        return Email_ID;
    }

    public void setEmail_ID(String email_ID) {
        Email_ID = email_ID;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
