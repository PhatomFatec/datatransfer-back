package com.datatransfer.dt2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.datatransfer.dt2.models.Credentials;

public interface CredentialsRepository extends JpaRepository<Credentials, Long> {

}
