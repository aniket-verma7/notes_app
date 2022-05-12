package com.project.notesapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.project.notesapp.dao.NotesDao;
import com.project.notesapp.entity.Note;
import com.project.notesapp.entity.User;

@Database(entities = {User.class, Note.class}, version = 1)
public abstract class NotesDatabase extends RoomDatabase {

    public abstract NotesDao getUserDao();

    private static NotesDatabase instance;

    public static NotesDatabase getInstance(Context context)
    {
        if(instance == null)
        {
            synchronized (NotesDatabase.class)
            {
                if(instance == null)
                    instance = Room.databaseBuilder(context, NotesDatabase.class,"user_database").allowMainThreadQueries().build();
            }
        }
        return instance;
    }
}
