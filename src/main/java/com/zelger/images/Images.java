package com.zelger.images;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

public class Images {
    public String id;
    public String fileName;
    public String uploader;
    public String date;

    public void createImage(String FILENAME, String UPLOADER){
        UUID uuid = UUID.randomUUID();
        id = uuid.toString();
        fileName = FILENAME;
        uploader = UPLOADER;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        date = dtf.format(now);
    }

}
