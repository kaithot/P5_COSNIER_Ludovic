package com.cleanup.todoc.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.NonNull;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

/**
 * Created by Ludovic Cosnier 18/05/2020
 */

//classe abstraite, héritant de RoomDatabase

@Database(entities = {Task.class, Project.class},version = 1, exportSchema = false)
public abstract class Save extends RoomDatabase{

    // --- SINGLETON --- <-- design paternn qui permet de créer une seule instance de notre BdD
    private static volatile Save INSTANCE;

    // --- DAO --- <-- déclaration de nos 2 DAO
    public abstract TaskDao taskDao();
    public abstract ProjectDao projectDao();

    // --- INSTANCE ---
    public static Save getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (Save.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            Save.class, "MyDatabase.db")
                            .addCallback(prepopulateDatabase())//permet d'ajouter des éléments (voir ci-dessous)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static Callback prepopulateDatabase(){ // ajout des differents projets
        return new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);

                ContentValues contentValuesTartampion = new ContentValues();
                contentValuesTartampion.put("name","Projet Tartampion");
                contentValuesTartampion.put("color",0xFFEADAD1);
                db.insert("Project", OnConflictStrategy.IGNORE,contentValuesTartampion);

                ContentValues contentValuesLucidia = new ContentValues();
                contentValuesLucidia.put("name","Projet Lucidia");
                contentValuesLucidia.put("color",0xFFB4CDBA);
                db.insert("Project", OnConflictStrategy.IGNORE,contentValuesLucidia);

                ContentValues contentValuesCircus = new ContentValues();
                contentValuesCircus.put("name","Projet Circus");
                contentValuesCircus.put("color",0xFFA3CED2);
                db.insert("Project", OnConflictStrategy.IGNORE,contentValuesCircus);

            }
        };
    }
}