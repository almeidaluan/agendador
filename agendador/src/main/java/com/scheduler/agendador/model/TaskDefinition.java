package com.scheduler.agendador.model;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
public class TaskDefinition {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID task_uuid;

    @Column()
    private String cronExpression;

    @Column()
    private String actionUrl;

    @Column()
    private String data;
}
