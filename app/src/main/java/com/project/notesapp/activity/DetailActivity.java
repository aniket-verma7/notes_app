package com.project.notesapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.project.notesapp.R;
import com.project.notesapp.adapter.DetailsImageAdapter;
import com.project.notesapp.adapter.ImageAdapter;
import com.project.notesapp.entity.Note;

import java.util.ArrayList;
import java.util.Scanner;


public class DetailActivity extends AppCompatActivity {

    private TextView detailTitle,detailDescription;
    private Note note;
    private DetailsImageAdapter adapter;
    private RecyclerView imageRecycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        note = (Note) getIntent().getSerializableExtra("note_data");
        detailTitle = findViewById(R.id.detailTitle);
        detailDescription = findViewById(R.id.detailDescription);

        detailTitle.setText(note.getTitle());
        detailDescription.setText(note.getDescription());

        imageRecycleView = findViewById(R.id.imageSlider);
        imageRecycleView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        adapter = new DetailsImageAdapter(getImageList(note));
        imageRecycleView.setAdapter(adapter);


    }

    private ArrayList<String> getImageList(Note note) {
        ArrayList<String> tmp = new ArrayList<>();
        Scanner scanner = new Scanner(note.getImageList());
        scanner.useDelimiter(", ");
        while(scanner.hasNext())
            tmp.add(scanner.next());
        return tmp;
    }
}