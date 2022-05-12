package com.project.notesapp.repository;

import android.app.Application;

import com.project.notesapp.dao.NotesDao;
import com.project.notesapp.database.NotesDatabase;
import com.project.notesapp.entity.Note;
import com.project.notesapp.entity.User;

import java.util.List;

public class NoteRepository {

    private NotesDao dao;
    private User user;

    public NoteRepository(Application application, User user) {
        this.user = user;
        dao = NotesDatabase.getInstance(application).getUserDao();
    }

    public void insertNote(Note note) {
        dao.insertNote(note);
    }

    public List<Note> getNotes() {
        return dao.getNotes(user.getMobile());
    }
}
