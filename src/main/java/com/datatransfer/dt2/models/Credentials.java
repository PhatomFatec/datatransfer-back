package com.datatransfer.dt2.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@EqualsAndHashCode
@Setter
@Entity
public class Credentials {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String client_id;

	private String client_secret;

	private String project_id;

	private String redirect_uris;
	
	private String auth_uri;
	
	private String token_uri;
	
	private String auth_provider_x509_cert_url;

	public Credentials() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Credentials(Long id, String client_id, String client_secret, String project_id, String redirect_uris) {
		super();
		this.id = id;
		this.client_id = client_id;
		this.client_secret = client_secret;
		this.project_id = project_id;
		this.redirect_uris = redirect_uris;
		this.auth_uri = "https://accounts.google.com/o/oauth2/auth";
		this.token_uri ="https://oauth2.googleapis.com/token";
		this.auth_provider_x509_cert_url = "https://www.googleapis.com/oauth2/v1/certs";
	}


}
