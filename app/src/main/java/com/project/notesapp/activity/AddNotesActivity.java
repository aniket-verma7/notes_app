package com.project.notesapp.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.project.notesapp.R;
import com.project.notesapp.adapter.ImageAdapter;
import com.project.notesapp.entity.Note;
import com.project.notesapp.entity.User;
import com.project.notesapp.repository.NoteRepository;
import com.project.notesapp.util.FileHandler;

import java.io.IOException;
import java.util.ArrayList;

public class AddNotesActivity extends AppCompatActivity implements ImageAdapter.ItemChangeListener {

    private User user;
    private TextInputEditText editTextNewTitle;
    private TextInputEditText editTextNewDescription;
    private NoteRepository repository;
    private RecyclerView imageRecycleView;
    private ArrayList<Uri> uriArrayList;
    private ImageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_note);

        user = (User) getIntent().getSerializableExtra("user_data");
        init();
    }

    private void init() {
        editTextNewTitle = findViewById(R.id.newTitle);
        editTextNewDescription = findViewById(R.id.newDescription);

        uriArrayList = new ArrayList<>();
        imageRecycleView = findViewById(R.id.rcvImageView);
        imageRecycleView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapter = new ImageAdapter(uriArrayList, this);
        imageRecycleView.setAdapter(adapter);

        repository = new NoteRepository(getApplication(), user);

        findViewById(R.id.saveNote).setOnClickListener(onClick -> saveNote());
        findViewById(R.id.camera).setOnClickListener(onClick -> pickImage());

    }

    private void saveNote() {
        String title = editTextNewTitle.getText().toString();
        String description = editTextNewDescription.getText().toString();

        ProgressDialog dialog = ProgressDialog.show(this, "", "Loading. Please wait...", true);
        dialog.show();

        if (title.isEmpty() || title.length() < 5 || title.length() > 100) {
            editTextNewTitle.setError("Invalid Title Length (5-100 characters is allowed)");
            dialog.dismiss();
        } else if (description.isEmpty() || description.length() < 100 || description.length() > 1000) {
            editTextNewDescription.setError("Invalid description Length (100-1000 characters is allowed)");
            dialog.dismiss();
        } else {
            try {
                Note note = new Note(user.getMobile(), title, description);
                StringBuilder uriBuilder = new StringBuilder();

                for (int i = 0; i < uriArrayList.size(); i++) {
                    String fileName = System.currentTimeMillis() + "_" + user.getMobile() + "_" + i + ".jpeg";
                    uriBuilder.append(fileName);

                    FileHandler.saveImageToExternalStorage(this, fileName, MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), uriArrayList.get(i)));
                    if (i != uriArrayList.size())
                        uriBuilder.append(", ");
                }
                note.setImageList(uriBuilder.toString());
                repository.insertNote(note);

                Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();
                setResult(201);
                finish();
            } catch (Exception exception) {
                Toast.makeText(this, exception.getLocalizedMessage() + "", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        }


    }

    private Bitmap getBitmap(Uri uri) throws IOException {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            return ImageDecoder.decodeBitmap(ImageDecoder.createSource(getApplicationContext().getContentResolver(), uri));
        } else {
            return MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), uri);
        }
    }

    private void pickImage() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
            return;
        }
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.putExtra(Intent.EXTRA_AUTO_LAUNCH_SINGLE_CHOICE, true);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            try {
                if (uriArrayList.size() < 10) {
                    uriArrayList.add(imageUri);
                    adapter.update(uriArrayList);
                } else {
                    Toast.makeText(this, "max 10 image is allowed", Toast.LENGTH_SHORT).show();
                    return;
                }

            } catch (Exception e) {
                Toast.makeText(this, e.getLocalizedMessage() + "", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void updateBitmaps(ArrayList<Uri> uriArrayList) {
        this.uriArrayList = uriArrayList;
    }
}
