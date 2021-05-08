package com.example.loginapp;

public class formation {
    private String formationName, desc,image ,date ,nbPlace;


    public formation(String formationName, String desc, String image, String date, String nbPlace) {
        this.formationName = formationName;
        this.desc = desc;
        this.image = image;
        this.date = date;
        this.nbPlace = nbPlace;
    }

    public formation() {

    }

    public String getNbPlace() { return nbPlace; }

    public String getDate() { return date; }

    public String getFormationName() { return formationName; }

    public String getDesc() {
        return desc;
    }

    public String getImage() { return image; }

    public void setNbPlace(String nbPlace) { this.nbPlace = nbPlace; }

    public void setDate(String date) { this.date = date; }

    public void setFormationName(String formationName) { this.formationName = formationName; }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setImage(String image) { this.image = image; }


}
