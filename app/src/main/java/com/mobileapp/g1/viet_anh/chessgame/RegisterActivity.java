package com.mobileapp.g1.viet_anh.chessgame;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mobileapp.g1.viet_anh.chessgame.database.DbHandle;
import com.mobileapp.g1.viet_anh.chessgame.model.User;

import java.util.Calendar;


public class RegisterActivity extends AppCompatActivity {
    private Button datePickerButton, registerButton;
    private TextView dobTextView, titleTextView;
    private RadioGroup genderRadioGroup;
    private RadioButton radioButtonFemale, radioButtonMale;
    private EditText usernameEditText, passwordEditText, nameEditText;

    private static final String female_img_path = "res/drawable/female.png";
    private static final String male_img_path = "res/drawable/male.png";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        User user = new User();

        datePickerButton = findViewById(R.id.buttonDatePicker);
        titleTextView = findViewById(R.id.textViewTitle);
        dobTextView = findViewById(R.id.textViewDOB);
        usernameEditText = findViewById(R.id.editTextUsername);
        passwordEditText = findViewById(R.id.editTextPassword);
        nameEditText = findViewById(R.id.editTextName);
        radioButtonFemale = findViewById(R.id.radioButtonFemale);
        radioButtonMale = findViewById(R.id.radioButtonMale);
        genderRadioGroup = findViewById(R.id.radioGroupGender);
        registerButton = findViewById(R.id.buttonRegister);


        genderRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(radioButtonFemale.isChecked()) {
                    user.setAvatarPath(female_img_path);
                    user.setGender("Female");
                }
                else if(radioButtonMale.isChecked()) {
                    user.setAvatarPath(male_img_path);
                    user.setGender("Male");
                }
            }
        });


        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(user);
            }
        });


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser(user);
            }
        });

    }
    private void showDatePickerDialog(User user) {

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(
                RegisterActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {

                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                        Calendar selectedDate = Calendar.getInstance();
                        selectedDate.set(selectedYear, selectedMonth, selectedDay);
                        String formattedDate = sdf.format(selectedDate.getTime());
                        user.setDob(formattedDate);
                        dobTextView.setText("Date of Birth: " + formattedDate);
                    }
                },
                year, month, day);


        datePickerDialog.show();
    }


    private void registerUser(User user) {

        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String name = nameEditText.getText().toString().trim();
        String dob = dobTextView.getText().toString().trim();


        if (username.isEmpty() || password.isEmpty() || name.isEmpty() || dob.equals("Date of Birth: Not selected") || user.getGender().isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        }
        else {
            DbHandle dbHandle = new DbHandle(this);

            user.setName(name);
            user.setUsername(username);
            user.setPassword(password);

            if(dbHandle.isUserExist(user)) {
                Toast.makeText(RegisterActivity.this,
                        "User with the same username, name, date of birth, and gender already exists", Toast.LENGTH_SHORT).show();
            }
            else {
                boolean check = dbHandle.addUser(user);
                if(!check) {
                    Toast.makeText(RegisterActivity.this, "Failed to register user", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    Toast.makeText(RegisterActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    finish();
                }
            }
        }
    }
}
