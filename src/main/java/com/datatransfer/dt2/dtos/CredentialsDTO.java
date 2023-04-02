package com.datatransfer.dt2.dtos;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CredentialsDTO {

	private String client_id;

	private String client_secret;

	private String project_id;

	private List<String> redirect_uris = new ArrayList<>();

	private String auth_uri;

	private String token_uri;

	private String auth_provider_x509_cert_url;

	public CredentialsDTO(String client_id, String client_secret, String project_id, String redirect_uris) {
		this.client_id = client_id;
		this.client_secret = client_secret;
		this.project_id = project_id;
		setRedirectUriss(redirect_uris);
		this.auth_uri = "https://accounts.google.com/o/oauth2/auth";
		this.token_uri = "https://oauth2.googleapis.com/token";
		this.auth_provider_x509_cert_url = "https://www.googleapis.com/oauth2/v1/certs";
	}

	public List<String> getRedirectUriss() {
		return redirect_uris;
	}

	public void setRedirectUriss(String redirectUris) {
		this.redirect_uris.add(redirectUris);
	}

}
