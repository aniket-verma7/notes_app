package com.project.notesapp.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.project.notesapp.R;
import com.project.notesapp.dao.NotesDao;
import com.project.notesapp.database.NotesDatabase;
import com.project.notesapp.encoder_decoder.PasswordEncoderDecoder;
import com.project.notesapp.entity.User;

import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SignupActivity extends AppCompatActivity {

    private TextInputEditText editTextName;
    private TextInputEditText editTextMobile;
    private TextInputEditText editTextEmail;
    private TextInputEditText editTextPassword;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_activity);

        init();

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void init() {

        editTextName = findViewById(R.id.newName);
        editTextMobile = findViewById(R.id.newMobile);
        editTextEmail = findViewById(R.id.newEmail);
        editTextPassword = findViewById(R.id.newPassword);

        findViewById(R.id.signup).setOnClickListener(onClick -> signupNewUser());

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void signupNewUser() {

        String name = editTextName.getText().toString();
        String mobile = editTextMobile.getText().toString();
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        String regex = "^\\s*(?:\\+?(\\d{1,3}))?[-. (]*(\\d{3})[-. )]*(\\d{3})[-. ]*(\\d{4})(?: *x(\\d+))?\\s*$";
        Pattern pattern = Pattern.compile(regex);

        if (name.isEmpty())
            editTextName.setError("Required");
        else if (mobile.isEmpty())
            editTextMobile.setError("Required");
        else if (!pattern.matcher(mobile).find())
            editTextMobile.setError("Invalid mobile number");
        else if (email.isEmpty() || email.length() < 4)
            editTextEmail.setError("Invalid email address");
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
            editTextEmail.setError("Invalid email address");
        else if (password.isEmpty() || !passwordValidator(name, password))
            editTextPassword.setError("Invalid Password\nRules:\n15 character long\nAvoid first name\nFirst character should be lowercase\nShould contain 2 uppercase\n2 digits\n1 special character");
        else {
            try {
                User user = new User(name, email, mobile, PasswordEncoderDecoder.encrypt(password));
                NotesDao dao = NotesDatabase.getInstance(getApplicationContext()).getUserDao();
                if (dao.getUser(email) == null && dao.getUser(mobile) == null) {
                    dao.insertUser(user);
                    startActivity(new Intent(this, LoginActivity.class));
                    finish();
                    Toast.makeText(this, "User Created...", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "User already exists", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(this, e.getLocalizedMessage()+"", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }


    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private boolean passwordValidator(String name, String password) {
        int cnt = 0, digits = 0;

        String[] nameArray = name.split("\\s+");

        for (char ch : password.toCharArray()) {
            if (Character.isUpperCase(ch))
                cnt += 1;
            else if (Character.isDigit(ch))
                digits += 1;
        }
        System.out.println(cnt + " " + digits);
        if (password.length() < 7)
            return false;
        else if (Arrays.stream(nameArray).filter(e -> password.contains(e)).collect(Collectors.toList()).size() > 0)
            return false;
        else if (cnt != 2 || digits != 1)
            return false;
        else if (!Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]").matcher(password).find())
            return false;

        return true;
    }
}