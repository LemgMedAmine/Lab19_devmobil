package com.example.roommvvmdemo.data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

// Auteur: Lemghili Mohammed Amine - Base Room exposee sous forme de singleton.
@Database(entities = {Note.class}, version = 1, exportSchema = false)
public abstract class NoteDatabase extends RoomDatabase {

    private static volatile NoteDatabase instance;

    public abstract NoteDao noteDao();

    // Auteur: Lemghili Mohammed Amine - Point d'entree unique vers la base locale.
    public static NoteDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (NoteDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    NoteDatabase.class,
                                    "notes_database"
                            )
                            .fallbackToDestructiveMigration(true)
                            .build();
                }
            }
        }
        return instance;
    }
}
