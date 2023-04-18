package com.datatransfer.dt2.configs;

import java.util.Timer;
import java.util.TimerTask;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

public class ExemploAgendamento implements ApplicationRunner {

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// Cria uma instância do Timer
		Timer timer = new Timer();

		// Define a tarefa que será executada
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				// Coloque o código que você deseja executar automaticamente aqui
				System.out.println("A tarefa foi executada!");
			}
		};

		// Agenda a tarefa para ser executada em um intervalo de tempo específico (em
		// milissegundos)
		// Neste exemplo, a tarefa será executada a cada 5 segundos (5000 ms)
		timer.schedule(task, 0, 5000);
	}

}
