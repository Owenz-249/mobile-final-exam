package com.mobileapp.g1.viet_anh.chessgame;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mobileapp.g1.viet_anh.chessgame.database.DbHandle;
import com.mobileapp.g1.viet_anh.chessgame.model.User;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ModificationActivity extends AppCompatActivity {

    private EditText editTextUpdateName, editTextUpdateUsername, editTextUpdatePassword, editTextUpdateConfirmPassword;
    private TextView textViewUpdateDOB;
    private Button buttonUpdateDatePicker, buttonUpdateProfile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modification);

        editTextUpdateName = findViewById(R.id.editTextUpdateName);
        editTextUpdateUsername = findViewById(R.id.editTextUpdateUsername);
        editTextUpdatePassword = findViewById(R.id.editTextUpdatePassword);
        editTextUpdateConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        textViewUpdateDOB = findViewById(R.id.textViewUpdateDOB);
        buttonUpdateDatePicker = findViewById(R.id.buttonUpdateDatePicker);
        buttonUpdateProfile = findViewById(R.id.buttonUpdateProfile);

        Intent intent = getIntent();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            User updatedUser = intent.getSerializableExtra("user", User.class);

            buttonUpdateDatePicker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDatePickerDialog(updatedUser);
                }
            });

            buttonUpdateProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateProfile(updatedUser);
                }
            });
        }

    }

    private void showDatePickerDialog(User updatedUser) {

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(
                ModificationActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {

                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                        Calendar selectedDate = Calendar.getInstance();
                        selectedDate.set(selectedYear, selectedMonth, selectedDay);
                        String formattedDate = sdf.format(selectedDate.getTime());
                        if(updatedUser.getDob().equalsIgnoreCase(formattedDate)){
                            Toast.makeText(ModificationActivity.this, "You chooses the same day stored in database", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            updatedUser.setDob(formattedDate);
                            textViewUpdateDOB.setText("Date of Birth: " + formattedDate);
                        }

                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    private void updateProfile(User updatedUser) {
        String name = editTextUpdateName.getText().toString().trim();
        String username = editTextUpdateUsername.getText().toString().trim();
        String password = editTextUpdatePassword.getText().toString().trim();
        String confirmPassword = editTextUpdateConfirmPassword.getText().toString().trim();

        if(!name.isEmpty()) updatedUser.setName(name);

        if(!username.isEmpty()) updatedUser.setUsername(username);

        if(updatedUser.getPassword().equalsIgnoreCase(password)){
            Toast.makeText(ModificationActivity.this, "You enters the old password, please re-enter", Toast.LENGTH_SHORT).show();
        }
        else if(!password.equalsIgnoreCase(confirmPassword)) {
            Toast.makeText(ModificationActivity.this, "Your password doesn't match together, please check again !", Toast.LENGTH_SHORT).show();
        }
        else {
            if(!password.isEmpty()) updatedUser.setPassword(password);
            DbHandle dbHandle = new DbHandle(this);
            boolean check = dbHandle.updateUser(updatedUser);
            if(check){
                Intent intent = new Intent(ModificationActivity.this, ProfileActivity.class);
                intent.putExtra("updatedUser", updatedUser);
                setResult(Activity.RESULT_OK, intent);
                Toast.makeText(ModificationActivity.this, "Update succeed !", Toast.LENGTH_SHORT).show();
                finish();
            }
            else
                Toast.makeText(ModificationActivity.this, "Update failed !", Toast.LENGTH_SHORT).show();
        }

    }
}
