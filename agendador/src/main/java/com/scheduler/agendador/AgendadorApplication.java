package com.scheduler.agendador;

import com.scheduler.agendador.services.TaskSchedulingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AgendadorApplication {

	@Autowired
	private TaskSchedulingService taskSchedulingService;

	public static void main(String[] args) {
		SpringApplication.run(AgendadorApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() {
		System.out.println("Startando Configuracao de Jobs que estao cadastrados na base");
		taskSchedulingService.loadJobsDatabase();
	}

}
