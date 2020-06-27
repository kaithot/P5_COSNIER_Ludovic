package com.cleanup.todoc.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.content.ClipData;

import com.cleanup.todoc.model.Task;

import java.util.List;



//Mise en place d'une interface DAO pour Task
//afin de regrouper les accès aux données.

@Dao
public interface TaskDao {

    //récupére les taches à faire
    @Query("SELECT * FROM Task ;")
    LiveData<List<Task>> getAllTasks();

    //ajout d'une tache à faire
    @Insert
    void insertTask(Task task);

    //supp. d'une tache
    @Delete
    void deleteTask(Task task);
}
