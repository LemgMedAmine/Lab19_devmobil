package com.example.roommvvmdemo.data.local;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

// Auteur: Lemghili Mohammed Amine - Entity Room representant une note stockee en SQLite.
@Entity(tableName = "notes_table")
public class Note {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private final String title;
    private final String description;

    public Note(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
