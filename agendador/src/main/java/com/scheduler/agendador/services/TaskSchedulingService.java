package com.scheduler.agendador.services;

import com.scheduler.agendador.model.TaskDefinition;
import com.scheduler.agendador.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ScheduledFuture;

@Service
public class TaskSchedulingService {

    @Autowired
    private TaskScheduler taskScheduler;

    @Autowired
    private TaskRepository taskRepository;

    Map<String, ScheduledFuture<?>> jobsMap = new HashMap<>();

    public void scheduleATask(Runnable tasklet, TaskDefinition taskDefinition) {

        ScheduledFuture<?> scheduledTask = taskScheduler.schedule(tasklet, new CronTrigger(taskDefinition.getCronExpression(), TimeZone.getTimeZone(TimeZone.getDefault().getID())));

        TaskDefinition taskDef = new TaskDefinition();

        taskDef.setCronExpression(taskDefinition.getCronExpression());
        taskDef.setActionUrl(taskDefinition.getActionUrl());
        taskDef.setData(taskDefinition.getData());

        var taskSaved = taskRepository.save(taskDefinition);

        System.out.println("Scheduling task with job id: " + taskSaved.getTask_uuid() + " and cron expression: " + taskSaved.getCronExpression());

        jobsMap.put(taskSaved.getTask_uuid().toString(), scheduledTask);
    }

    public void removeScheduledTask(String jobId) {
        ScheduledFuture<?> scheduledTask = jobsMap.get(jobId);
        if(scheduledTask != null) {
            scheduledTask.cancel(true);
            jobsMap.put(jobId, null);
        }
    }

    public Map<String, ScheduledFuture<?>> loadJobsLocal(){
        return jobsMap;
    }

    public void loadJobsDatabase(){
        var tasks = taskRepository.findAll();

        if (tasks.isEmpty()){
            System.out.println("Nao tem job para ser carregado na aplicacao");
        }else{
            for (var task: tasks) {
                TaskDefinitionBean taskDefinitionBean = new TaskDefinitionBean();
                taskDefinitionBean.setTaskDefinition(task);
                ScheduledFuture<?> scheduledTask = taskScheduler.schedule(taskDefinitionBean,
                        new CronTrigger(task.getCronExpression(), TimeZone.getTimeZone(TimeZone.getDefault().getID())));
                jobsMap.put(task.getTask_uuid().toString(), scheduledTask);
            }
        }
    }
}
