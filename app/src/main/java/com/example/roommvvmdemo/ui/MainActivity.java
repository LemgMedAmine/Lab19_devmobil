package com.example.roommvvmdemo.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roommvvmdemo.R;
import com.example.roommvvmdemo.data.local.Note;
import com.example.roommvvmdemo.viewmodel.NoteViewModel;

// Auteur: Lemghili Mohammed Amine - Activity principale, elle joue le role de View dans MVVM.
public class MainActivity extends AppCompatActivity {

    private NoteViewModel noteViewModel;
    private EditText etTitle;
    private EditText etDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);
        Button btnAdd = findViewById(R.id.btnAdd);
        Button btnDeleteAll = findViewById(R.id.btnDeleteAll);
        View emptyState = findViewById(R.id.emptyState);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        NoteAdapter adapter = new NoteAdapter();
        recyclerView.setAdapter(adapter);

        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        // Auteur: Lemghili Mohammed Amine - L'Activity observe LiveData et laisse l'UI reagir.
        noteViewModel.getAllNotes().observe(this, notes -> {
            adapter.setNotes(notes);
            boolean hasNotes = notes != null && !notes.isEmpty();
            recyclerView.setVisibility(hasNotes ? View.VISIBLE : View.GONE);
            emptyState.setVisibility(hasNotes ? View.GONE : View.VISIBLE);
        });

        btnAdd.setOnClickListener(v -> saveNote());

        btnDeleteAll.setOnClickListener(v -> {
            noteViewModel.deleteAllNotes();
            Toast.makeText(this, "Toutes les notes ont ete supprimees", Toast.LENGTH_SHORT).show();
        });

        adapter.setOnItemLongClickListener(note -> {
            noteViewModel.delete(note);
            Toast.makeText(this, "Note supprimee", Toast.LENGTH_SHORT).show();
        });

        adapter.setOnItemClickListener(note ->
                Toast.makeText(this, "Titre : " + note.getTitle(), Toast.LENGTH_SHORT).show()
        );
    }

    private void saveNote() {
        // Auteur: Lemghili Mohammed Amine - Validation simple avant l'envoi au ViewModel.
        String title = etTitle.getText().toString().trim();
        String description = etDescription.getText().toString().trim();

        if (title.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Remplir le titre et la description", Toast.LENGTH_SHORT).show();
            return;
        }

        Note note = new Note(title, description);
        noteViewModel.insert(note);

        etTitle.setText("");
        etDescription.setText("");

        Toast.makeText(this, "Note ajoutee", Toast.LENGTH_SHORT).show();
    }
}
