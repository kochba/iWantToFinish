package com.example.tkfinalproject.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.tkfinalproject.RePostry.User;
import com.example.tkfinalproject.Utility.UtilityClass;

/**
 * This class provides helper methods to interact with an SQLite database for storing user data.
 * It extends the SQLiteOpenHelper class and manages database creation, version management, and CRUD operations.
 */
public class MyDataBaseHelper extends SQLiteOpenHelper {

    // Utility class instance for showing alerts
    UtilityClass utilityClass;
    // Database name
    private static final String DATABASE_NAME = "usersData.db";
    // Database version
    private static final int DATABASE_VERSION = 1;

    // Table name
    private static final String TABLE_NAME = "myUserData";
    // Column names
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_UserName = "User_Name";
    private static final String COLUMN_PassWord = "User_PassWord";

    // String variable to hold query results
    private String str;

    /**
     * Constructor to initialize the MyDataBaseHelper with a context.
     *
     * @param context The context to use for various operations.
     */
    public MyDataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        utilityClass = new UtilityClass(context);
    }

    /**
     * Called when the database is created for the first time. Creates the user data table.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            String query = "CREATE TABLE " + TABLE_NAME +
                    " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_UserName + " TEXT, " +
                    COLUMN_PassWord + " TEXT);";
            db.execSQL(query);
        } catch (Exception e) {
            utilityClass.showAlertExp();
        }
    }

    /**
     * Called when the database needs to be upgraded. Drops the existing table and creates a new one.
     *
     * @param db The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        } catch (Exception e) {
            utilityClass.showAlertExp();
        }
    }

    /**
     * Adds a new user to the database.
     *
     * @param user The user to add.
     * @return True if the user was added successfully, false otherwise.
     */
    public boolean AddUser(User user) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_UserName, user.getUsername());
            cv.put(COLUMN_PassWord, user.getPass());
            long result = db.insert(TABLE_NAME, null, cv);
            return result != -1;
        } catch (Exception e) {
            utilityClass.showAlertExp();
            return false;
        }
    }

    /**
     * Retrieves the ID of a user by their username.
     *
     * @param name The username to search for.
     * @return The ID of the user, or null if not found.
     */
    private String idByName(String name) {
        String query = "SELECT " + COLUMN_ID + " FROM " + TABLE_NAME + " WHERE " + COLUMN_UserName + " = ?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        str = null;

        try {
            if (db != null) {
                cursor = db.rawQuery(query, new String[]{name});
                if (cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndex(COLUMN_ID);
                    str = cursor.getString(columnIndex);
                }
            }
        } catch (Exception e) {
            utilityClass.showAlertExp();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return str;
    }

    /**
     * Updates the password of a user.
     *
     * @param user The user with the new password.
     * @return True if the password was updated successfully, false otherwise.
     */
    public boolean uptadePass(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_PassWord, user.getPass());
        str = idByName(user.getUsername());

        try {
            if (str != null) {
                long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{str});
                return result != -1;
            }
        } catch (Exception e) {
            utilityClass.showAlertExp();
        }

        return false;
    }

    /**
     * Removes a user from the database.
     *
     * @param user The user to remove.
     * @return True if the user was removed successfully, false otherwise.
     */
    public boolean removeUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        String id = idByName(user.getUsername());

        try {
            if (id != null) {
                int rowsAffected = db.delete(TABLE_NAME, "_id=?", new String[]{id});
                return (rowsAffected > 0);
            } else {
                return false;
            }
        } catch (Exception e) {
            utilityClass.showAlertExp();
            return false;
        }
    }

    /**
     * Checks if a username exists in the database.
     *
     * @param userName The username to check.
     * @return True if the username exists, false otherwise.
     */
    public boolean DoesUserNameExisit(String userName) {
        String query = "SELECT " + COLUMN_UserName + " FROM " + TABLE_NAME + " WHERE " + COLUMN_UserName + " = ?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            if (db != null) {
                cursor = db.rawQuery(query, new String[]{userName});
                return cursor.getCount() > 0;
            }
        } catch (Exception e) {
            utilityClass.showAlertExp();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return false;
    }
}
