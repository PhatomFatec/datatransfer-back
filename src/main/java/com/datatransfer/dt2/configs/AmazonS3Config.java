package com.datatransfer.dt2.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

@Configuration
public class AmazonS3Config {

    @Value("${aws.accessKey}")
    private String accessKey;

    @Value("${aws.secretKey}")
    private String secretKey;

    @Value("${aws.region}")
    private String regionName;

    @Value("${aws.s3.bucketName}")
    private String bucketName;

    @Bean
    public AmazonS3 amazonS3Client() {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        AmazonS3 amazonS3 = new AmazonS3Client(awsCredentials);
        Region region = Region.getRegion(Regions.fromName(regionName));

        amazonS3.setRegion(region);
        return amazonS3;
    }

    // ...
}

