package com.mobileapp.g1.viet_anh.chessgame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mobileapp.g1.viet_anh.chessgame.database.DbHandle;
import com.mobileapp.g1.viet_anh.chessgame.model.User;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUsername, editTextPassword;
    private Button login;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        DbHandle dbHandle = new DbHandle(this);

        editTextUsername = findViewById(R.id.inpUsername);
        editTextPassword = findViewById(R.id.inpPassword);
        login = findViewById(R.id.buttonLogin);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();
                if(username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Username or password must be not null !", Toast.LENGTH_SHORT).show();
                }
                else {
                    User u = new User(username, password);
                    boolean checkLogin = dbHandle.checkLogin(u);
                    if(checkLogin) {
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        intent.putExtra("user", u);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Toast.makeText(LoginActivity.this, "Login failed. Please check your credentials!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }
}
