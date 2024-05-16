package com.example.tkfinalproject.RePostry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import androidx.annotation.Nullable;

import com.example.tkfinalproject.Utility.UtilityClass;

public class MyDataBaseHelper extends SQLiteOpenHelper {

    private Context context;
    UtilityClass utilityClass;
    private static final String DATABASE_NAME = "usersData.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "myUserData";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_UserName = "User_Name";
    private static final String COLUMN_PassWord = "User_PassWord";

    private String str;

    public MyDataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        utilityClass = new UtilityClass(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            String query = "CREATE TABLE " + TABLE_NAME +
                    " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_UserName + " TEXT, " +
                    COLUMN_PassWord + " TEXT);";
            db.execSQL(query);
        } catch (Exception e){
            utilityClass.showAlertExp();
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        try {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        } catch (Exception e) {
            utilityClass.showAlertExp();
        }
    }

    public boolean AddUser(User user){
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

    public void removeUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        String id = idByName(user.getUsername());

        try {
            if (id != null) {
                db.delete(TABLE_NAME, "_id=?", new String[]{id});
            }
        } catch (Exception e) {
            utilityClass.showAlertExp();
        }
    }

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