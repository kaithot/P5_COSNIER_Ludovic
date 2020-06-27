package com.cleanup.todoc;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit tests for tasks
 *
 * @author Gaëtan HERFRAY
 */
public class TaskUnitTest {

    // creation d'un mock projectDao et ajout des 3 projects
    // puis modification des asserEquals et assertNull afin de prendre en compte le projectDao

    @Test
    public void test_projects() {

        ProjectDao projectDao=mock(ProjectDao.class);
        when(projectDao.getProjectById(1)).thenReturn(new Project(1,"Projet Tartampion",0xFFEADAD1));
        when(projectDao.getProjectById(2)).thenReturn(new Project(2,"Projet Lucidia", 0xFFB4CDBA));
        when(projectDao.getProjectById(3)).thenReturn(new Project( 3,"Projet Circus", 0xFFA3CED2));
        when(projectDao.getProjectById(4)).thenReturn(null);

        final Task task1 = new Task( 1, "task 1", new Date().getTime());
        final Task task2 = new Task( 2, "task 2", new Date().getTime());
        final Task task3 = new Task(3, "task 3", new Date().getTime());
        final Task task4 = new Task( 4, "task 4", new Date().getTime());

        assertEquals("Projet Tartampion", (projectDao.getProjectById(task1.getProjectId())).getName());
        assertEquals("Projet Lucidia", (projectDao.getProjectById(task2.getProjectId())).getName());
        assertEquals("Projet Circus", (projectDao.getProjectById(task3.getProjectId())).getName());
        assertNull(projectDao.getProjectById(task4.getProjectId()));
    }

    @Test
    public void test_az_comparator() {
        final Task task1 = new Task( 1, "aaa", 123);
        final Task task2 = new Task(2, "zzz", 124);
        final Task task3 = new Task( 3, "hhh", 125);

        final ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        Collections.sort(tasks, new Task.TaskAZComparator());

        assertSame(tasks.get(0), task1);
        assertSame(tasks.get(1), task3);
        assertSame(tasks.get(2), task2);
    }

    @Test
    public void test_za_comparator() {
        final Task task1 = new Task( 1, "aaa", 123);
        final Task task2 = new Task(2, "zzz", 124);
        final Task task3 = new Task( 3, "hhh", 125);

        final ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        Collections.sort(tasks, new Task.TaskZAComparator());

        assertSame(tasks.get(0), task2);
        assertSame(tasks.get(1), task3);
        assertSame(tasks.get(2), task1);
    }

    @Test
    public void test_recent_comparator() {
        final Task task1 = new Task(1, "aaa", 123);
        final Task task2 = new Task( 2, "zzz", 124);
        final Task task3 = new Task( 3, "hhh", 125);

        final ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        Collections.sort(tasks, new Task.TaskRecentComparator());

        assertSame(tasks.get(0), task3);
        assertSame(tasks.get(1), task2);
        assertSame(tasks.get(2), task1);
    }

    @Test
    public void test_old_comparator() {
        final Task task1 = new Task( 1, "aaa", 123);
        final Task task2 = new Task( 2, "zzz", 124);
        final Task task3 = new Task( 3, "hhh", 125);

        final ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        Collections.sort(tasks, new Task.TaskOldComparator());

        assertSame(tasks.get(0), task1);
        assertSame(tasks.get(1), task2);
        assertSame(tasks.get(2), task3);
    }
}