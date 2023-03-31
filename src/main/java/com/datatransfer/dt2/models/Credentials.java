package com.datatransfer.dt2.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Credentials {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String client_id;

	private String client_secret;

	private String project_id;

	private String redirect_uris;

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
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getClient_id() {
		return client_id;
	}

	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}

	public String getClient_secret() {
		return client_secret;
	}

	public void setClient_secret(String client_secret) {
		this.client_secret = client_secret;
	}

	public String getProject_id() {
		return project_id;
	}

	public void setProject_id(String project_id) {
		this.project_id = project_id;
	}

	public String getRedirect_uris() {
		return redirect_uris;
	}

	public void setRedirect_uris(String redirect_uris) {
		this.redirect_uris = redirect_uris;
	}

}
