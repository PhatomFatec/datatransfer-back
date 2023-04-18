package com.datatransfer.dt2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datatransfer.dt2.models.History;
import com.datatransfer.dt2.repositories.HistoryRepository;

@Service
public class HistoryService {

    @Autowired
    private HistoryRepository repository;

    public History save(History obj) {
        obj.setStatus("Enviado");

        return repository.save(obj);
    }

    public History History(History obj) {
        return new History(obj.getHistory_id(), obj.getFile_id(), obj.getNome_arquivo(), obj.getTamanho(),
                obj.getTempo(), obj.getData_envio(), obj.getStatus());
    }

}
