package com.datatransfer.dt2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.datatransfer.dt2.repositories.CredentialsAWSRepository;

@Service
public class DatabaseCredentialsProvider implements AWSCredentialsProvider {

    private final CredentialsAWSRepository credentialsRepository;
    
    public DatabaseCredentialsProvider(@Autowired CredentialsAWSRepository credentialsRepository) {
        this.credentialsRepository = credentialsRepository;
    }

    @Override
    public AWSCredentials getCredentials() {
    	String accesKey = credentialsRepository.findById(1L).get().getAccessKey();
    	String secretKey = credentialsRepository.findById(1L).get().getSecretKey();
        return new BasicAWSCredentials(accesKey, secretKey);
    }


	public void refreshCred(String acessKey, String secretKey) {
		credentialsRepository.findById(1L).get().setAccessKey(acessKey);
		credentialsRepository.findById(1L).get().setSecretKey(secretKey);
	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		
	}
}
