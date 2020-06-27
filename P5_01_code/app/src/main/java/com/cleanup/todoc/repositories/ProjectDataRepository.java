package com.cleanup.todoc.repositories;

import android.arch.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.model.Project;

import java.util.List;

//récupération par l'intermédiaire du constructeur du DAO project pour le
// réutiliser dans les méthodes publiques

public class ProjectDataRepository {

    private final ProjectDao projectDao;


    public ProjectDataRepository(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    // --- Get All Projects ---
    public LiveData<List<Project>> getAllProjects() { return projectDao.getAllProjects();}

    // CREATION PROJET
    // --- CREATE ---
    public void createProject(Project project) {
        projectDao.createProject(project);
    }
}
