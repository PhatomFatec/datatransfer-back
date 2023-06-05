package com.datatransfer.dt2.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@EqualsAndHashCode
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CredentialsAWS {

	@Id
	private Long id;

	private String accessKey;

	private String secretKey;

	private String regionName;

	private String bucketName;

}
