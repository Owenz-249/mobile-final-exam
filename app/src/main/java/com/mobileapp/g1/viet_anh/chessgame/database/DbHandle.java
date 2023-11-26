package com.mobileapp.g1.viet_anh.chessgame.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.mobileapp.g1.viet_anh.chessgame.model.User;

public class DbHandle {

    private DbHelper dbHelper;

    public DbHandle(Context context) {
        dbHelper = new DbHelper(context);
    }

    public boolean checkLogin(User u) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String username = u.getUsername();
        String password = u.getPassword();

        String[] projection = {
                DbHelper.COLUMN_ID,
                DbHelper.COLUMN_NAME,
                DbHelper.COLUMN_USERNAME,
                DbHelper.COLUMN_PASSWORD,
                DbHelper.COLUMN_DOB,
                DbHelper.COLUMN_GENDER,
                DbHelper.COLUMN_AVATAR_PATH
        };
        String selection = DbHelper.COLUMN_USERNAME+ " = ? AND " + DbHelper.COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {username, password};

        Cursor cursor = null;
        try {
            cursor = db.query(
                    dbHelper.TABLE_NAME,projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null
            );
            if(cursor.moveToFirst()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DbHelper.COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.COLUMN_NAME));
                String dob = cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.COLUMN_DOB));
                String gender = cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.COLUMN_GENDER));
                String avatarPath = cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.COLUMN_AVATAR_PATH));
                u.setId(id);
                u.setDob(dob);
                u.setName(name);
                u.setGender(gender);
                u.setAvatarPath(avatarPath);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(cursor != null) {
                cursor.close();
            }
            //db.close();
        }
        return false;
    }

    public boolean addUser(User user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DbHelper.COLUMN_NAME, user.getName());
        values.put(DbHelper.COLUMN_USERNAME, user.getUsername());
        values.put(DbHelper.COLUMN_PASSWORD, user.getPassword());
        values.put(DbHelper.COLUMN_DOB, user.getDob());
        values.put(DbHelper.COLUMN_GENDER, user.getGender());
        values.put(DbHelper.COLUMN_AVATAR_PATH, user.getAvatarPath());

        try {
            long newRowId = db.insert(DbHelper.TABLE_NAME, null, values);
            // newRowId will be -1 if the insertion failed
            if (newRowId != -1) {
                Log.d("DbHandle", "User added successfully with ID: " + newRowId);
                return true;
            } else {
                Log.e("DbHandle", "Failed to add user");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            //db.close();
        }
    }

    public boolean isUserExist(User user) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                DbHelper.COLUMN_ID
        };
        String selection = DbHelper.COLUMN_USERNAME + " = ? AND " +
                DbHelper.COLUMN_DOB + " = ? AND " +
                DbHelper.COLUMN_NAME + " = ? AND " +
                DbHelper.COLUMN_GENDER + " = ?";
        String[] selectionArgs = {
                user.getUsername(),
                user.getDob(),
                user.getName(),
                user.getGender()
        };
        Cursor cursor = null;
        try {
            cursor = db.query(
                    dbHelper.TABLE_NAME, projection,
                    selection, selectionArgs,
                    null, null, null
            );

            // Nếu có ít nhất một dòng trong kết quả trả về, tức là người dùng đã tồn tại
            return cursor.moveToFirst();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
           // db.close();
        }

        return false;
    }

    public boolean updateUser(User user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DbHelper.COLUMN_NAME, user.getName());
        values.put(DbHelper.COLUMN_USERNAME, user.getUsername());
        values.put(DbHelper.COLUMN_PASSWORD, user.getPassword());
        values.put(DbHelper.COLUMN_DOB, user.getDob());

        String selection = DbHelper.COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(user.getId())};

        try {
            int rowsAffected = db.update(DbHelper.TABLE_NAME, values, selection, selectionArgs);
            // rowsAffected will be the number of rows updated
            if (rowsAffected > 0) {
                Log.d("DbHandle", "User updated successfully");
                return true;
            } else {
                Log.e("DbHandle", "Failed to update user");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
           //  db.close();
        }
    }


}
