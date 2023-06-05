package com.datatransfer.dt2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.datatransfer.dt2.configs.FixedDelayUpdatedEvent;
import com.datatransfer.dt2.models.Schedule;
import com.datatransfer.dt2.repositories.ScheduleRepository;

@Service
public class ScheduleConfigService {

	@Autowired
	private ScheduleRepository scheduleConfigRepository;

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	public long getFixedRateFromDatabase() {
		Schedule scheduleConfig = scheduleConfigRepository.findById(1L)
				.orElseThrow(() -> new RuntimeException("Configuração não encontrada"));
		return scheduleConfig.getTime();
	}

	public void updateFixedDelayProperty() {
		long fixedDelay = getFixedRateFromDatabase();

		// Dispara o evento informando a atualização do valor do atraso
		FixedDelayUpdatedEvent event = new FixedDelayUpdatedEvent(this, fixedDelay);
		eventPublisher.publishEvent(event);
	}
}
