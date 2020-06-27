package com.cleanup.todoc.repositories;

import android.arch.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Task;

import java.util.List;

//récupération par l'intermédiaire du constructeur du DAO task pour le
//réutiliser dans les méthodes publiques
public class TaskDataRepository {

    private final TaskDao taskDao;

    public TaskDataRepository(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    // --- GET ---
    public LiveData<List<Task>> getAllTasks() {
        return this.taskDao.getAllTasks();
    }

    // --- CREATE ---
    public void createTask(Task task) {
        taskDao.insertTask(task);
    }

    // --- DELETE ---
    public void deleteTask(Task task) {
        taskDao.deleteTask(task);
    }

}

