package com.cleanup.todoc.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.cleanup.todoc.model.Project;

import java.util.List;

//Mise en place de l'interface DAO pour la table Project.

@Dao
public interface ProjectDao {

    //ajout d'un nouveau projet et
    //mise en place de @Insert(onConflict = OnConflictStrategy.REPLACE) pour
    //permettre d'écraser un utilisateur déjà existant possédant le même id que celui que l'on souhaite créer.

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createProject(Project project);

    @Query("SELECT * FROM Project;")
    LiveData<List<Project>> getAllProjects();

    //CREATION DE CETTE REQUETE POUR UTILISATION DU MOCK
    @Query("SELECT * FROM Project WHERE id= :id;")
    Project getProjectById(long id);

}
