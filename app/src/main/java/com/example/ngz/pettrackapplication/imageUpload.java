package com.example.ngz.pettrackapplication;

public class imageUpload {

    public String pet_name;
    public String pet_email;
    public String pet_phone;
    public String pet_img;
    public String pet_Category;
    public String pet_Uid;

    public String getPet_name() {
        return pet_name;
    }

    public String getPet_email() {
        return pet_email;
    }

    public String getPet_phone() {
        return pet_phone;
    }

    public String getPet_img() {
        return pet_img;
    }

    public String getPet_Category() {
        return pet_Category;
    }

    public void setPet_name(String pet_name) {
        this.pet_name = pet_name;
    }

    public void setPet_email(String pet_email) {
        this.pet_email = pet_email;
    }

    public void setPet_phone(String pet_phone) {
        this.pet_phone = pet_phone;
    }

    public void setPet_img(String pet_img) {
        this.pet_img = pet_img;
    }

    public void setPet_Category(String pet_Category) {
        this.pet_Category = pet_Category;
    }

    public String getPet_Uid() {
        return pet_Uid;
    }

    public void setPet_Uid(String pet_Uid) {
        this.pet_Uid = pet_Uid;
    }

    public imageUpload(String pet_name, String pet_email, String pet_phone, String pet_Category, String pet_img, String pet_Uid) {
        this.pet_name = pet_name;
        this.pet_email = pet_email;
        this.pet_phone = pet_phone;
        this.pet_img = pet_img;
        this.pet_Category = pet_Category;
        this.pet_Uid = pet_Uid;
    }

    public imageUpload(){}
}
