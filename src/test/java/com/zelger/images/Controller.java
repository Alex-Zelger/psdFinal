package com.zelger.images;

import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {

    //private final S3Client client;

    //This should be a multipart request that contains the image you want to upload as well as some of the metadata about the image.
    @PostMapping("/images")
    public Image postImage(){
        Image img = new Image();
        //img.createImage
        return img;
    }

    //This request will fetch the metadata for an image that you previously stored with the above POST request.
    @GetMapping("/read")
    public String read(){
        return "";
    }

    //This request will respond with the image data (similar to what you did in the QR Code part of the previous assignment)
    @GetMapping("/images/{imageId}/file")
    public String imfile(@PathVariable String imageId){
        return "";
    }

    //Deletes a previously uploaded image
    @DeleteMapping("/images/{imageId}")
    public void del(@PathVariable String imageId){

    }

    //Responds with a JSON array of image IDs
    @GetMapping("/images")
    public String list(){
        return "";
    }


}
