package com.datatransfer.dt2.models;

import java.time.Instant;
import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id")
    private Long history_id;

    private String file_id;
    private String nome_arquivo;
    private Long tamanho;
    private Long tempo;
    private LocalDate data_envio;
    private String status;

    public History() {
    }

    public History(Long history_id, String file_id, String nome_arquivo, Long tamanho, Long tempo,
            LocalDate data_envio, String status) {
    }
}
