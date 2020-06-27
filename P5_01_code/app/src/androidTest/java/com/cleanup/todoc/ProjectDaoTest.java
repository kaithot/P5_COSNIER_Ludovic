package com.cleanup.todoc;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.cleanup.todoc.database.Save;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Ludovic Cosnier 11/06/2020
 */

@RunWith(AndroidJUnit4.class)
public class ProjectDaoTest {

    // FOR DATA
    private Save database;
    private static Project DEMO_1 = new Project(1,"Projet tartampion", 0xFFEADAD1);
    private static Project DEMO_2 = new Project(2,"Projet Lucidia", 0xFFB4CDBA);
    private static Project DEMO_3 = new Project(3,"Projet Circus", 0xFFA3CED2);

    private static Task task1 = new Task( 1, "aaa", 123);

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb() throws Exception {
        this.database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                Save.class)
                .allowMainThreadQueries()
                .build();
    }

    @After
    public void closeDb() throws Exception {
        database.close();
    }

    @Test
    public void insertAndGetProjects() throws InterruptedException {
        this.database.projectDao().createProject(DEMO_1);
        this.database.projectDao().createProject(DEMO_2);
        this.database.projectDao().createProject(DEMO_3);

        // TEST
        List<Project> projects = LiveDataTestUtil.getValue(this.database.projectDao().getAllProjects());
        assertTrue(projects.size() == 3);
    }

    @Test
    public void insertAndGetTask() throws InterruptedException {
        this.database.projectDao().createProject(DEMO_1); // mise en place d'un projet
        this.database.taskDao().insertTask(task1); // ajout d'une tache

        // TEST
        List<Task> task = LiveDataTestUtil.getValue(this.database.taskDao().getAllTasks());
        assertTrue(task.size() == 1);

    }

    @Test
    public void deleteTask() throws InterruptedException {
        this.database.projectDao().createProject(DEMO_1); // mise en place d'un projet
        this.database.taskDao().insertTask(task1); // ajout d'une tache

        Task task = LiveDataTestUtil.getValue(this.database.taskDao().getAllTasks()).get(0);

        this.database.taskDao().deleteTask(task); // supp d'une tache

        // TEST
        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getAllTasks());
        assertTrue(tasks.size() == 0);
    }

}
