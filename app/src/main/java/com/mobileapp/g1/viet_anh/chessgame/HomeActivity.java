package com.mobileapp.g1.viet_anh.chessgame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mobileapp.g1.viet_anh.chessgame.model.User;

public class HomeActivity extends AppCompatActivity {

    private Button buttonProfile, buttonAddFriend;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);


        Intent intent = getIntent();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            User user = intent.getSerializableExtra("user", User.class);
            TextView name = findViewById(R.id.textViewName);
            name.setText("Welcome " + user.getName() + " to Chess");
        }

        buttonProfile = findViewById(R.id.buttonProfile);
        buttonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentProfile = new Intent(HomeActivity.this, ProfileActivity.class);
                User user = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                    user = intent.getSerializableExtra("user", User.class);
                    intentProfile.putExtra("user", user);
                    startActivity(intentProfile);
                    finish();
                }
            }
        });


    }
}
