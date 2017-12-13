package com.example.mukul.daphinslab;

/**
 * Created by mukul on 12/13/17.
 */

public class Bucket {

    private String directory;
    private String imagepath;
    private String foldername;

    public String getFoldername() {
        return foldername;
    }

    public void setFoldername(String foldername) {
        this.foldername = foldername;
    }

    public Bucket(String directory, String imagepath,String foldername) {
        this.directory = directory;
        this.imagepath = imagepath;
        this.foldername = foldername;
    }

    public String getDirectory() {
        return directory;
    }

    public String getImagepath() {
        return imagepath;
    }
}