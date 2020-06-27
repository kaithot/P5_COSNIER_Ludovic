package com.cleanup.todoc.ui;

import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;
import android.content.res.ColorStateList;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cleanup.todoc.R;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Adapter which handles the list of tasks to display in the dedicated RecyclerView.</p>
 *
 * @author Gaëtan HERFRAY
 */
public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TaskViewHolder> {
    /**
     * The list of tasks the adapter deals with
     */

    @NonNull
    private List<Task> tasks;
    private List<Project> projects; // ajout
    private Project taskProject;

    /**
     * The listener for when a task needs to be deleted
     */
    @NonNull
    private final DeleteTaskListener deleteTaskListener;

    /**
     * Instantiates a new TasksAdapter.
     *
     * @param tasks the list of tasks the adapter deals with to set
     */
    // ajout dans le constructeur du paramètre @NonNull final List<Project> projects
    TasksAdapter(@NonNull final List<Task> tasks, @NonNull final DeleteTaskListener deleteTaskListener, @NonNull final List<Project> projects) {
        this.tasks = tasks;
        this.deleteTaskListener = deleteTaskListener;
        this.projects = projects;
    }

    /**
     * Updates the list of tasks the adapter deals with.
     *
     * @param tasks the list of tasks the adapter deals with to set
     */
    void updateTasks(@NonNull final List<Task> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    //AJOUT updateProjects
    /**
     * Updates the list of projects the adapter deals with.
     *
     * @param projects the list of projects the adapter deals with to set
     */
    void updateProjects(@NonNull final List<Project> projects) {
        this.projects = projects;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_task, viewGroup, false);
        return new TaskViewHolder(view, deleteTaskListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder taskViewHolder, int position) {
        taskViewHolder.bind(tasks.get(position));
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    /**
     * Listener for deleting tasks
     */
    public interface DeleteTaskListener {
        /**
         * Called when a task needs to be deleted.
         *
         * @param taskId the task that needs to be deleted
         */
        void onDeleteTask(Task taskId);
    }

    /**
     * <p>ViewHolder for task items in the tasks list</p>
     *
     * @author Gaëtan HERFRAY
     */
    class TaskViewHolder extends RecyclerView.ViewHolder {
        /**
         * The circle icon showing the color of the project
         */
        private final AppCompatImageView imgProject;

        /**
         * The TextView displaying the name of the task
         */
        private final TextView lblTaskName;

        /**
         * The TextView displaying the name of the project
         */
        private final TextView lblProjectName;

        /**
         * The delete icon
         */
        private final AppCompatImageView imgDelete;

        /**
         * The listener for when a task needs to be deleted
         */
        private final DeleteTaskListener deleteTaskListener;

        /**
         * Instantiates a new TaskViewHolder.
         *
         * @param itemView           the view of the task item
         * @param deleteTaskListener the listener for when a task needs to be deleted to set
         */
        TaskViewHolder(@NonNull View itemView, @NonNull DeleteTaskListener deleteTaskListener) {
            super(itemView);

            this.deleteTaskListener = deleteTaskListener;

            imgProject = itemView.findViewById(R.id.img_project);
            lblTaskName = itemView.findViewById(R.id.lbl_task_name);
            lblProjectName = itemView.findViewById(R.id.lbl_project_name);
            imgDelete = itemView.findViewById(R.id.img_delete);

            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Object tag = view.getTag();
                    if (tag instanceof Task) {
                        TaskViewHolder.this.deleteTaskListener.onDeleteTask((Task) tag);
                    }
                }
            });
        }

        /**
         * Binds a task to the item view.
         *
         * @param task the task to bind in the item view
         */
        void bind(Task task) {
            lblTaskName.setText(task.getName());
            imgDelete.setTag(task);

            //boucle pour récupérer l'ID dans project
            for (Project item : projects) {

                if (item.getId() == task.getProjectId()) {
                    taskProject = item;
                }

                // -----
            }
                    if (taskProject != null) {
                        imgProject.setSupportImageTintList(ColorStateList.valueOf(taskProject.getColor()));
                        lblProjectName.setText(taskProject.getName());
                    } else {
                        imgProject.setVisibility(View.INVISIBLE);
                        lblProjectName.setText("");
                    }
            }
        }
    }