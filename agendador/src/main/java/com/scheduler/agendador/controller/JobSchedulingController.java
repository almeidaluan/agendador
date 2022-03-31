package com.scheduler.agendador.controller;

import com.scheduler.agendador.model.TaskDefinition;
import com.scheduler.agendador.services.TaskDefinitionBean;
import com.scheduler.agendador.services.TaskSchedulingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/schedule")
public class JobSchedulingController {

    @Autowired
    private TaskSchedulingService taskSchedulingService;

    @Autowired
    private TaskDefinitionBean taskDefinitionBean;

    @PostMapping(path="/taskdef", consumes = "application/json", produces="application/json")
    public void scheduleATask(@RequestBody TaskDefinition taskDefinition) {
        taskDefinitionBean.setTaskDefinition(taskDefinition);
        taskSchedulingService.scheduleATask(taskDefinitionBean, taskDefinition);
    }

    @GetMapping(path="/remove/{jobid}")
    public void removeJob(@PathVariable String jobid) {
        taskSchedulingService.removeScheduledTask(jobid);
    }


    @GetMapping("/teste")
    public String teste01(@RequestParam String requestId){
        return "oi" + requestId;
    }

    @GetMapping("/load-jobs")
    public ResponseEntity<?> returnJobs() {
        return ResponseEntity.ok(taskSchedulingService.loadJobsLocal());
    }
}
