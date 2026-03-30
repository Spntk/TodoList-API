package com.todolist.dtos;

import java.util.List;

public class DashboardDto {
    private long totalTasks;
    private long completedTasks;
    private long inProgressTasks;
    private long pendingTasks;
    private long overdueTasks;
    private List<TodoDto> recentTasks;
    private List<TodoDto> overdueTodoList;

    public DashboardDto(long totalTasks, long completedTasks, long inProgressTasks, long pendingTasks,
            long overdueTasks, List<TodoDto> recentTasks, List<TodoDto> overdueTodoList) {
        this.totalTasks = totalTasks;
        this.completedTasks = completedTasks;
        this.inProgressTasks = inProgressTasks;
        this.pendingTasks = pendingTasks;
        this.overdueTasks = overdueTasks;
        this.recentTasks = recentTasks;
        this.overdueTodoList = overdueTodoList;
    }

    public long getCompletedTasks() {
        return completedTasks;
    }

    public long getInProgressTasks() {
        return inProgressTasks;
    }

    public long getOverdueTasks() {
        return overdueTasks;
    }

    public List<TodoDto> getOverdueTodoList() {
        return overdueTodoList;
    }

    public long getPendingTasks() {
        return pendingTasks;
    }

    public List<TodoDto> getRecentTasks() {
        return recentTasks;
    }

    public long getTotalTasks() {
        return totalTasks;
    }
}
