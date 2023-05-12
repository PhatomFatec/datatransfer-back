package com.datatransfer.dt2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.datatransfer.dt2.models.Credentials;

@Repository
public interface CredentialsRepository extends JpaRepository<Credentials, Long> {

}
