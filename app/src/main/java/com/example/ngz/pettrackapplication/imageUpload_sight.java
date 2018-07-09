package com.example.ngz.pettrackapplication;

public class imageUpload_sight {

    public String pet_email_sight;
    public String pet_phone_sight;
    public String pet_img_sight;
    public String pet_Category_sight;
    public String pet_Uid_sight;


    public String getPet_email_sight() {
        return pet_email_sight;
    }

    public String getPet_phone_sight() {
        return pet_phone_sight;
    }

    public String getPet_img_sight() {
        return pet_img_sight;
    }

    public String getPet_Category_sight() {
        return pet_Category_sight;
    }

    public void setPet_email_sight(String pet_email_sight) {
        this.pet_email_sight = pet_email_sight;
    }

    public void setPet_phone_sight(String pet_phone_sight) {
        this.pet_phone_sight = pet_phone_sight;
    }

    public void setPet_img_sight(String pet_img_sight) {
        this.pet_img_sight = pet_img_sight;
    }

    public void setPet_Category_sight(String pet_Category_sight) {
        this.pet_Category_sight = pet_Category_sight;
    }

    public String getPet_Uid_sight() {
        return pet_Uid_sight;
    }

    public void setPet_Uid_sight(String pet_Uid_sight) {
        this.pet_Uid_sight = pet_Uid_sight;
    }

    public imageUpload_sight(String pet_email, String pet_phone, String pet_img, String pet_Category_sight , String pet_Uid_sight) {
        this.pet_email_sight = pet_email;
        this.pet_phone_sight = pet_phone;
        this.pet_img_sight = pet_img;
        this.pet_Category_sight = pet_Category_sight;
        this.pet_Uid_sight = pet_Uid_sight;
    }

    public imageUpload_sight(){}
}
