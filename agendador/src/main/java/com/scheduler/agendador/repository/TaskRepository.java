package com.scheduler.agendador.repository;

import com.scheduler.agendador.model.TaskDefinition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TaskRepository  extends JpaRepository<TaskDefinition, UUID> {
}
