package com.zelger.images;

public class Image {
    public String id;
    public String fileName;
    public String uploader;
    public String s3Url;

    public void createImage(String ID, String FILENAME, String UPLOADER, String URL){
        id = ID;
        fileName = FILENAME;
        uploader = UPLOADER;
        s3Url = URL;
    }

}
