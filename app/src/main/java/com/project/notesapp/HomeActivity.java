package com.project.notesapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.notesapp.activity.AddNotesActivity;
import com.project.notesapp.activity.DetailActivity;
import com.project.notesapp.adapter.NotesAdapter;
import com.project.notesapp.entity.Note;
import com.project.notesapp.entity.User;
import com.project.notesapp.repository.NoteRepository;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements NotesAdapter.INoteDetail {

    private User user;
    private RecyclerView rcvNotes;
    private TextView placeHolder;
    private NotesAdapter adapter;
    private NoteRepository repository;
    private List<Note> noteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        user = (User) getIntent().getSerializableExtra("user_data");
        repository = new NoteRepository(getApplication(),user);
        noteList = repository.getNotes();
        adapter = new NotesAdapter(noteList,this);

        placeHolder = findViewById(R.id.placeHolder);
        rcvNotes = findViewById(R.id.rcvNotes);
        rcvNotes.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        rcvNotes.setAdapter(adapter);
        findViewById(R.id.addNote).setOnClickListener(onClick -> openAddNotes());

        if (noteList.size() > 0) {
            placeHolder.setVisibility(View.GONE);
            rcvNotes.setVisibility(View.VISIBLE);
        }
    }

    private void openAddNotes() {
        Intent intent = new Intent(this, AddNotesActivity.class);
        intent.putExtra("user_data",user);
        startActivityForResult(intent,200);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == 201){
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
            noteList = repository.getNotes();
            adapter.listUpdated(noteList);
            if (noteList.size() > 0) {
                placeHolder.setVisibility(View.GONE);
                rcvNotes.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void showNote(Note note) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("note_data",note);
        startActivity(intent);
    }
}
