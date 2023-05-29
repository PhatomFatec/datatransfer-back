package com.datatransfer.dt2.controller;

import java.io.FileWriter;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datatransfer.dt2.models.Schedule;
import com.datatransfer.dt2.repositories.ScheduleRepository;
import com.datatransfer.dt2.services.ScheduleConfigService;

@RestController
@RequestMapping("/alterar")
@CrossOrigin
public class fixedRateController {

	@Autowired
	private ScheduleRepository repo;

	@Autowired
	private ScheduleConfigService service;


	@PostMapping
	public void save(@RequestBody Schedule obj) throws GeneralSecurityException, InterruptedException, IOException {
		repo.save(obj);
		service.updateFixedDelayProperty();

		LocalDateTime now = LocalDateTime.now();
        // Formatar a data e hora atual como uma string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String formattedDateTime = now.format(formatter);
		String fileName = "arquivo_" + formattedDateTime + ".java";

        String nomeArquivo = "./src/main/resources/temp_files/"+fileName;
        String conteudoClasse = "public class MinhaClasse {\n" +
                                "    public static void main(String[] args) {\n" +
                                "        System.out.println(\"Olá, mundo!\");\n" +
                                "    }\n" +
                                "}";
        
        try {
            FileWriter fileWriter = new FileWriter(nomeArquivo);
            fileWriter.write(conteudoClasse);
            fileWriter.close();
            System.out.println("Arquivo criado com sucesso.");
        } catch (IOException e) {
            System.out.println("Ocorreu um erro ao criar o arquivo: " + e.getMessage());
        }

    }

	
}
