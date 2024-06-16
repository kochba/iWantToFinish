package com.example.tkfinalproject.UI.UpdateUser;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;

import com.example.tkfinalproject.RePostry.Repostry;
import com.example.tkfinalproject.RePostry.User;
import com.example.tkfinalproject.UI.mainactivity.MainActivity;
import com.example.tkfinalproject.Utility.IonComplete;

/**
 * This class handles user data operations such as showing user data, updating user data,
 * and deleting user data. It interacts with a repository to perform these operations.
 */
public class UpdateUserMoudle {
    private final Repostry repostry;           // Repository for handling user data operations
    private final Context myContext;           // Context for various operations
    private final AlertDialog.Builder adb;     // Alert dialog builder for displaying messages
    private User user;                         // User object to hold current user data
    private final SharedPreferences.Editor editor; // SharedPreferences editor for storing user data
    private Intent intent;                     // Intent for navigation

    /**
     * Constructor to initialize the UpdateUserMoudle with a context.
     *
     * @param context The context to use for various operations.
     */
    public UpdateUserMoudle(Context context) {
        adb = new AlertDialog.Builder(context);
        repostry = new Repostry(context);
        myContext = context;
        SharedPreferences sp = context.getSharedPreferences("MyUserPerfs", Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    /**
     * Shows the current user data in the provided EditText fields and enables the given buttons.
     *
     * @param editTextname The EditText to display the username.
     * @param editTextPass The EditText to display the password.
     * @param btn The button to be enabled.
     * @param btn2 The second button to be enabled.
     */
    public void showdata(EditText editTextname, EditText editTextPass, Button btn, Button btn2) {
        repostry.setCurrentData(flag -> {
            User user1 = repostry.getCurrentUser();
            editTextname.setText(user1.getUsername());
            editTextPass.setText(user1.getPass());
            btn.setEnabled(true);
            btn2.setEnabled(true);
            if (myContext instanceof UpdateUser){
                ((UpdateUser) myContext).hideLoadingOverlay();
            }
        });
    }

    /**
     * Sets the current user in the repository with the given EditText values.
     *
     * @param editTextName The EditText containing the username.
     * @param editTextPass The EditText containing the password.
     */
    public void setUser(EditText editTextName, EditText editTextPass) {
        user = new User(editTextName.getText().toString().trim(), editTextPass.getText().toString().trim());
        repostry.setCurrentUser(user);
    }

    /**
     * Updates the user data in the repository with the given EditText values.
     *
     * @param editTextName The EditText containing the username.
     * @param editTextPass The EditText containing the password.
     * @param ionCompleteInt The callback interface to notify the result of the update operation.
     */
    public void updateUser(EditText editTextName, EditText editTextPass, IonComplete.IonCompleteInt ionCompleteInt) {
        user = new User(editTextName.getText().toString().trim(), editTextPass.getText().toString().trim());
        repostry.updatedata(user, ionCompleteInt);
    }

    /**
     * Deletes the user data from the repository with the given EditText values.
     * If the deletion is successful, clears the SharedPreferences and starts the MainActivity.
     *
     * @param editTextName The EditText containing the username.
     * @param editTextPass The EditText containing the password.
     */
    public void deleteUser(EditText editTextName, EditText editTextPass) {
        user = new User(editTextName.getText().toString().trim(), editTextPass.getText().toString().trim());
        repostry.removeUser(user, flag -> {
            if (flag) {
                editor.putString("UserName", "");
                editor.putString("UserPass", "");
                editor.commit();
                repostry.setCurrentUser(new User("", ""));
                intent = new Intent(myContext, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                myContext.startActivity(intent);
            } else {
                adb.setTitle("נסה שוב");
                adb.setMessage("מחיקת משתמש נכשלה");
                adb.setCancelable(false);
                adb.setPositiveButton("הבנתי", (dialog, which) -> {});
                adb.create().show();
            }
        });
    }
}


