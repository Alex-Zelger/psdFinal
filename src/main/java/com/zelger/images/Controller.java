package com.zelger.images;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.core.sync.RequestBody;

import java.io.File;
import java.nio.ByteBuffer;
import java.io.IOException;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.Scanner;

@RestController
public class Controller {

    private final S3Client client;

    @Autowired
    Controller(S3Client client){
        this.client = client;
    }

    //This should be a multipart request that contains the image you want to upload as well as some of the metadata about the image.
    @PostMapping("/images")
    public Images postImage(@RequestParam String uploader, @RequestParam String filename){
        //Create Metadata
        Images image = new Images();
        image.createImage(filename, uploader);


        //Creates multipart upload and gets upload id
        CreateMultipartUploadRequest createMultipartUploadRequest = CreateMultipartUploadRequest.builder()
                .bucket("practicumstorage")
                .key(image.id)
                .build();

        CreateMultipartUploadResponse response = client.createMultipartUpload(createMultipartUploadRequest);
        String uploadId = response.uploadId();
        System.out.println(uploadId);


        //Upload part 1 (Image)
        UploadPartRequest uploadPartRequest1 = UploadPartRequest.builder()
                .bucket("practicumstorage")
                .key("image")
                .uploadId(uploadId)
                .partNumber(1).build();
        CompletedPart part1 = CompletedPart.builder().partNumber(1).build();


        //Upload part 2 (uploader)
        UploadPartRequest uploadPartRequest2 = UploadPartRequest.builder().bucket("practicumstorage").key("uploader")
                .uploadId(uploadId)
                .partNumber(2).build();
        CompletedPart part2 = CompletedPart.builder().partNumber(2).build();

        //Upload part 3 (filename)
        UploadPartRequest uploadPartRequest3 = UploadPartRequest.builder().bucket("practicumstorage").key("filename")
                .uploadId(uploadId)
                .partNumber(3).build();
        CompletedPart part3 = CompletedPart.builder().partNumber(3).build();


        //Merge all and upload
        CompletedMultipartUpload completedMultipartUpload = CompletedMultipartUpload.builder()
                .parts(part1, part2, part3)
                .build();

        CompleteMultipartUploadRequest completeMultipartUploadRequest =
                CompleteMultipartUploadRequest.builder()
                        .bucket("practicumstorage")
                        .key("image")
                        .uploadId(uploadId)
                        .multipartUpload(completedMultipartUpload)
                        .build();

        client.completeMultipartUpload(completeMultipartUploadRequest);


        //return metadata
        return image;
    }

    //This request will fetch the metadata for an image that you previously stored with the above POST request.
    @GetMapping("/read")
    public String read(@RequestParam String id){
        GetObjectRequest request = GetObjectRequest.builder()
                .bucket("practicumstorage")
                .key(id)
                .build();
        var inputStream = this.client.getObject(request);
        Scanner s = new Scanner(inputStream).useDelimiter("\\A");
        String result = s.hasNext() ? s.next() : "";
        return result;
    }

    //This request will respond with the image data (similar to what you did in the QR Code part of the previous assignment)
    @GetMapping("/images/{imageId}/file")
    public String imfile(@PathVariable String imageId){

        //Stuck with this
        return "Doesn't Work";
    }

    //Deletes a previously uploaded image
    @DeleteMapping("/images/{imageId}")
    public void del(@PathVariable String imageId){
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket("practicumstorage")
                .key(imageId)
                .build();

        client.deleteObject(deleteObjectRequest);

    }

    //Responds with a JSON array of image IDs
    @GetMapping("/images")
    public List<S3Object> list(){

            ListObjectsRequest listObjects = ListObjectsRequest
                    .builder()
                    .bucket("practicumstorage")
                    .build();

            ListObjectsResponse res = client.listObjects(listObjects);
            List<S3Object> objects = res.contents();

        return objects;
    }


}
