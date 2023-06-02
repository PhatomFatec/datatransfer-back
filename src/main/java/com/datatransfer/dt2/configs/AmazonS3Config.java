package com.datatransfer.dt2.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.datatransfer.dt2.repositories.CredentialsAWSRepository;
import com.datatransfer.dt2.services.DatabaseCredentialsProvider;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@EqualsAndHashCode
@Setter
@Configuration
public class AmazonS3Config {

	private String regionName;
    private final AWSCredentialsProvider credentialsProvider;
    
    @Autowired
    private CredentialsAWSRepository credentialRepository;
    
    

    public AmazonS3Config(AWSCredentialsProvider credentialsProvider) {
        this.credentialsProvider = credentialsProvider;
    }

    @Bean
    public AmazonS3 amazonS3() {
        AWSCredentialsProvider credentialsProvider = new DatabaseCredentialsProvider(credentialRepository);	
        AmazonS3 amazonS3 = AmazonS3ClientBuilder.standard()
                .withCredentials(credentialsProvider)
                .withRegion(Regions.SA_EAST_1)
                .build();
        
        return amazonS3;

	}
    

}
