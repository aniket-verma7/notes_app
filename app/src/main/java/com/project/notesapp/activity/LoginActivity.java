package com.project.notesapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.project.notesapp.HomeActivity;
import com.project.notesapp.R;
import com.project.notesapp.dao.NotesDao;
import com.project.notesapp.database.NotesDatabase;
import com.project.notesapp.encoder_decoder.PasswordEncoderDecoder;
import com.project.notesapp.entity.User;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText editTextEmailMobile;
    private TextInputEditText editTextPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        init();
    }

    private void init() {
        editTextEmailMobile = findViewById(R.id.emailMobile);
        editTextPassword = findViewById(R.id.loginPassword);
    }

    public void newUser(View view) {
        startActivity(new Intent(this, SignupActivity.class));
        finish();
    }

    public void loginUser(View view) {
        String emailMobile = editTextEmailMobile.getText().toString();
        String password = editTextPassword.getText().toString();

        if (emailMobile.isEmpty())
            editTextEmailMobile.setError("Required");
        else if (password.isEmpty())
            editTextPassword.setError("Required");
        else {
            try {
                NotesDao dao = NotesDatabase.getInstance(getApplicationContext()).getUserDao();
                User user = dao.getUser(emailMobile);
                if(user == null)
                    Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
                else if(!PasswordEncoderDecoder.decrypt(user.getPassword()).equals(password))
                    Toast.makeText(this, "Incorrect Credentials", Toast.LENGTH_SHORT).show();
                else{
                    Intent intent = new Intent(this, HomeActivity.class);
                    intent.putExtra("user_data",user);
                    startActivity(intent);
                    finish();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}