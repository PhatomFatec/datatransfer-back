package com.datatransfer.dt2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.datatransfer.dt2.models.History;

public interface HistoryRepository extends JpaRepository<History, Long> {

}
