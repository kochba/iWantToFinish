package com.example.tkfinalproject.UI.UpdateUser;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;

import com.example.tkfinalproject.R;
import com.example.tkfinalproject.RePostry.User;
import com.example.tkfinalproject.UI.FirstPage.FirstPage;
import com.example.tkfinalproject.Utility.BaseActivity;
import com.example.tkfinalproject.Utility.UtilityClass;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class handles the user update and delete operations in the application.
 * It provides a UI to input the username and password, and buttons to update or delete the user.
 * It also checks for network connectivity before performing these operations.
 */
public class UpdateUser extends BaseActivity implements View.OnClickListener {

    private EditText editTextName, editTextPass;
    private Intent intent;
    private Button Update, Delete;
    private UpdateUserMoudle moudle;
    private UtilityClass utilityClass;
    private SharedPreferences sp;
    private AlertDialog.Builder adb;
    private User user;

    /**
     * Called when the activity is first created. Initializes the UI components and sets up the network check.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);
        utilityClass = new UtilityClass(this);
        adb = new AlertDialog.Builder(this);

        editTextName = findViewById(R.id.userup);
        editTextPass = findViewById(R.id.passup);
        Update = findViewById(R.id.uptade);
        Delete = findViewById(R.id.delete);
        Delete.setOnClickListener(this);
        Update.setOnClickListener(this);

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if (isConnected) {
            moudle = new UpdateUserMoudle(this);
            moudle.showdata(editTextName, editTextPass, Update, Delete);
        } else {
            showalert("יש בעיה חבר!", "אין אינטרנט חבר אי אפשר לעדכן סיסמה", new Intent(this, FirstPage.class));
        }

        intent = new Intent(UpdateUser.this, FirstPage.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        sp = getSharedPreferences("MyUserPerfs", Context.MODE_PRIVATE);
    }

    /**
     * Returns the root layout ID of the activity.
     *
     * @return The root layout ID.
     */
    @Override
    protected int getRootLayoutId() {
        return R.id.updatelayout;
    }

    /**
     * Shows an alert dialog with a specified title and message.
     *
     * @param head The title of the alert dialog.
     * @param body The message of the alert dialog.
     */
    private void showalert(String head, String body) {
        adb.setTitle(head);
        adb.setMessage(body);
        adb.setCancelable(false);
        adb.setPositiveButton("הבנתי", (dialog, which) -> {});
        adb.create().show();
    }

    /**
     * Shows an alert dialog with a specified title, message, and intent to start when the dialog is confirmed.
     *
     * @param head The title of the alert dialog.
     * @param body The message of the alert dialog.
     * @param intent The intent to start when the dialog is confirmed.
     */
    private void showalert(String head, String body, Intent intent) {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle(head);
        adb.setMessage(body);
        adb.setCancelable(false);
        adb.setPositiveButton("הבנתי", (dialog, which) -> startActivity(intent));
        adb.create().show();
    }

    /**
     * Handles the click events for the update and delete buttons.
     *
     * @param view The view that was clicked.
     */
    @Override
    public void onClick(View view) {
        if (view == Update) {
            handleUpdate();
        } else if (view == Delete) {
            handleDelete();
        }
    }

    /**
     * Handles the update button click event.
     * It validates the input and performs the update operation if the input is valid.
     */
    private void handleUpdate() {
        Update.setEnabled(false);
        Delete.setEnabled(false);
        super.showLoadingOverlay();

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        SharedPreferences.Editor editor = sp.edit();

        if (editTextName.getText().length() < 8) {
            showalert("יש בעיה חבר!", "שם משתמש קצר משמונה");
            resetButtons();
        } else if (!containsLetter(editTextName.getText().toString())) {
            showalert("יש בעיה חבר!", "בשם משתמש חייב לפחות אות אחת");
            resetButtons();
        } else if (editTextPass.getText().length() < 6) {
            showalert("יש בעיה חבר!", "סיסמה קצרה מ6");
            resetButtons();
        } else if (isConnected) {
            user = new User(sp.getString("UserName", ""), sp.getString("UserPass", ""));
            moudle.updateUser(editTextName, editTextPass, flag -> {
                switch (flag) {
                    case 0:
                        if (!Objects.equals(user.getUsername(), "")) {
                            editor.putString("UserName", editTextName.getText().toString().trim());
                            editor.putString("UserPass", editTextPass.getText().toString().trim());
                            editor.commit();
                        }
                        moudle.setUser(editTextName, editTextPass);
                        showalert("עדכון סיסמה הצליח!", "אתה יכול להמשיך להשתמש באפלייקצייה", intent);
                        resetButtons();
                        break;
                    case 1:
                        showalert("העדכון נכשל!", "נסה שוב");
                        resetButtons();
                        break;
                    case 2:
                        showalert("יש בעיה חבר!", "השם משתמש החדש כבר קיים במערכת");
                        resetButtons();
                        break;
                }
            });
        } else {
            showalert("יש בעיה חבר!", "אין אינטרנט חבר אי אפשר לעדכן סיסמה");
            resetButtons();
        }
    }

    /**
     * Handles the delete button click event.
     * It confirms the delete operation and performs it if confirmed.
     */
    private void handleDelete() {
        Update.setEnabled(false);
        Delete.setEnabled(false);

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if (isConnected) {
            adb.setTitle("מחיקת משתמש");
            adb.setMessage("האם את בטוח שאתה רוצה למחוק את המשתמש");
            adb.setCancelable(false);
            adb.setPositiveButton("כן", (dialog, which) -> {
                UpdateUser.super.showLoadingOverlay();
                moudle.deleteUser(editTextName, editTextPass);
            });
            adb.setNegativeButton("לא", (dialog, which) -> {
                dialog.cancel();
                resetButtons();
            });
            adb.create().show();
        } else {
            showalert("יש בעיה חבר!", "אין אינטרנט חבר אי אפשר למחוק משתמש");
            resetButtons();
        }
    }

    /**
     * Resets the state of the update and delete buttons.
     */
    private void resetButtons() {
        Update.setEnabled(true);
        Delete.setEnabled(true);
        super.hideLoadingOverlay();
        editTextName.setEnabled(false);
    }

    /**
     * Checks if the input string contains at least one letter.
     *
     * @param input The input string to check.
     * @return True if the input contains at least one letter, false otherwise.
     */
    private boolean containsLetter(String input) {
        String pattern = ".*[a-zA-Z].*";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(input);
        return matcher.matches();
    }
}
