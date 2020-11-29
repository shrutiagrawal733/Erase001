package com.example.erase;


public class DataModel {

    String date, dropbox, gadget;
    int points;

    public DataModel(String date, String dropbox, String gadget, int points)
    {
        this.date = date;
        this.dropbox = dropbox;
        this.gadget = gadget;
        this.points = points;
    }

    public String getDate() {
        return date;
    }

    public String getDropbox() {
        return dropbox;
    }
    public String getGadget() {
        return gadget;
    }
    public int getPoints() {
        return points;
    }
}
