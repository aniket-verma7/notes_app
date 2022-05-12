package com.project.notesapp.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.project.notesapp.entity.Note;
import com.project.notesapp.entity.User;

import java.util.List;

@Dao
public interface NotesDao {

    @Query("select * from user_table where mobile = :queryText or email = :queryText")
    User getUser(String queryText);

    @Insert(onConflict = OnConflictStrategy.FAIL)
    void insertUser(User user);

    @Query("select * from notes_table where mobile= :mobile")
    List<Note> getNotes(String mobile);

    @Insert(onConflict = OnConflictStrategy.FAIL)
    void insertNote(Note note);
}
