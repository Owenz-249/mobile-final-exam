package com.mobileapp.g1.viet_anh.chessgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mobileapp.g1.viet_anh.chessgame.model.User;

public class ProfileActivity extends AppCompatActivity {

    private Button buttonModify;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        Intent intent = getIntent();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            User user = intent.getSerializableExtra("user", User.class);

            ImageView imageViewAvatar = findViewById(R.id.imageViewAvatar);
            TextView textViewUsername = findViewById(R.id.textViewUsername);
            TextView textViewName = findViewById(R.id.textViewName);
            TextView textViewDOB = findViewById(R.id.textViewDOB);
            TextView textViewGender = findViewById(R.id.textViewGender);

            if(user.getGender().equalsIgnoreCase("Female")){
                imageViewAvatar.setImageResource(R.drawable.female);
            }
            else imageViewAvatar.setImageResource(R.drawable.male);

            textViewUsername.setText("Username: " + user.getUsername());
            textViewName.setText("Name: " + user.getName());
            textViewDOB.setText("Date of birth: " + user.getDob());
            textViewGender.setText("Gender: " + user.getGender());
        }

        buttonModify = findViewById(R.id.buttonModify);
        buttonModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent modificationIntent = new Intent(ProfileActivity.this, ModificationActivity.class);
                User user2 = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                    user2 = intent.getSerializableExtra("user", User.class);
                    modificationIntent.putExtra("user", user2);
                    startActivity(modificationIntent);
                    //finish();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);




        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
            // Nhận dữ liệu người dùng đã được cập nhật từ ModificationActivity
            User updatedUser = (User) data.getSerializableExtra("updatedUser");
            Log.d("ProfileActivity", "onActivityResult: requestCode = " + requestCode + ", resultCode = " + resultCode);
            // Cập nhật giao diện người dùng trên luồng chính
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ImageView imageViewAvatar = findViewById(R.id.imageViewAvatar);
                    TextView textViewUsername = findViewById(R.id.textViewUsername);
                    TextView textViewName = findViewById(R.id.textViewName);
                    TextView textViewDOB = findViewById(R.id.textViewDOB);
                    TextView textViewGender = findViewById(R.id.textViewGender);

                    if (updatedUser.getGender().equalsIgnoreCase("Female")) {
                        imageViewAvatar.setImageResource(R.drawable.female);
                    } else {
                        imageViewAvatar.setImageResource(R.drawable.male);
                    }

                    textViewUsername.setText("Username: " + updatedUser.getUsername());
                    textViewName.setText("Name: " + updatedUser.getName());
                    textViewDOB.setText("Date of birth: " + updatedUser.getDob());
                    textViewGender.setText("Gender: " + updatedUser.getGender());
                }
            });
        }
    }
}
