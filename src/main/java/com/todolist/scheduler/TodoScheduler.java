package com.todolist.scheduler;

import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.todolist.repositories.TodoRepository;

@Component
public class TodoScheduler {

    private final TodoRepository todoRepository;

    public TodoScheduler(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void hardDeleteExpiredTodos() {
        LocalDateTime expiredDate = LocalDateTime.now().minusDays(7);
        System.out.println("Scheduler running... expiredDate: " + expiredDate);
        todoRepository.deleteByIsDeletedTrueAndDeletedAtBefore(expiredDate);
    }
}
