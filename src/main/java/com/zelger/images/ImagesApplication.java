package com.zelger.images;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.IOException;

import org.springframework.context.annotation.Bean;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.model.CreateBucketConfiguration;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.DeleteBucketRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.S3Client;

@SpringBootApplication
public class ImagesApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(ImagesApplication.class, args);
}

	@Bean
	public S3Client createS3Client(){
		return S3Client.builder()
				.region(Region.US_EAST_2)
				.build();
	}
}
