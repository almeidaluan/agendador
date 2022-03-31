package com.scheduler.agendador.services;

import com.scheduler.agendador.model.TaskDefinition;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Log4j2
public class TaskDefinitionBean implements Runnable{

    private TaskDefinition taskDefinition;


    @Override
    public void run() {
        System.out.println(taskDefinition.getData());
        System.out.println(taskDefinition.getActionUrl());

        ResponseEntity<?> entity =  new RestTemplate().getForEntity(taskDefinition.getActionUrl(),String.class);
        log.info(entity.toString());
    }

    public TaskDefinition getTaskDefinition() {
        return taskDefinition;
    }

    public void setTaskDefinition(TaskDefinition taskDefinition) {
        this.taskDefinition = taskDefinition;
    }
}
