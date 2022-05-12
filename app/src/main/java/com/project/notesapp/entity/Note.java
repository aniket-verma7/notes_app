package com.project.notesapp.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.ArrayList;

@Entity(tableName = "notes_table")
public class Note implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String mobile;//act as foreign key
    private String title;
    private String description;
    private String imageList;


    public Note() {}

    public Note(String mobile, String title, String description) {
        this.mobile = mobile;
        this.title = title;
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getMobile() {
        return mobile;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageList() {
        return imageList;
    }

    public void setImageList(String imageList) {
        this.imageList = imageList;
    }

    @Override
    public String toString() {
        return "Notes{" +
                "id=" + id +
                ", mobile='" + mobile + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
