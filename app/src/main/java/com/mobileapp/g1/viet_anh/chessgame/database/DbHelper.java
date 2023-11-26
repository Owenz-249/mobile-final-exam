package com.mobileapp.g1.viet_anh.chessgame.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Users.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "users";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_DOB = "dob";
    public static final String COLUMN_GENDER = "gender";
    public static final String COLUMN_AVATAR_PATH = "avatarPath";

    String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_NAME + " TEXT, "
            + COLUMN_USERNAME + " TEXT, "
            + COLUMN_PASSWORD + " TEXT, "
            + COLUMN_DOB + " TEXT, "
            + COLUMN_GENDER + " TEXT, "
            + COLUMN_AVATAR_PATH + " TEXT);";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void insertData(SQLiteDatabase db) {
        String[] insertStatements = {
                "INSERT INTO " + TABLE_NAME + " (" + COLUMN_NAME + ", " + COLUMN_USERNAME + ", " + COLUMN_PASSWORD + ", " + COLUMN_DOB + ", " + COLUMN_GENDER + ", " + COLUMN_AVATAR_PATH + ") VALUES ('Admin', 'admin', 'admin', '24/09/2002', 'female', 'res/drawable/female.png');",
                "INSERT INTO " + TABLE_NAME + " (" + COLUMN_NAME + ", " + COLUMN_USERNAME + ", " + COLUMN_PASSWORD + ", " + COLUMN_DOB + ", " + COLUMN_GENDER + ", " + COLUMN_AVATAR_PATH + ") VALUES ('Viet Anh', 'owen', 'test123', '12/02/2002', 'male', 'res/drawable/male.png');"
        };

        for (String insertStatement : insertStatements) {
            db.execSQL(insertStatement);
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        insertData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
