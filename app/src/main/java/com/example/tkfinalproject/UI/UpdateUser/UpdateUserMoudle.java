package com.example.tkfinalproject.UI.UpdateUser;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tkfinalproject.R;
import com.example.tkfinalproject.RePostry.Repostry;
import com.example.tkfinalproject.RePostry.User;
import com.example.tkfinalproject.Utility.IonComplete;

public class UpdateUserMoudle {
    int x;
    private Repostry repostry;
    private Context myContext;
    Button btn;
    User user;
    UpdateUser updateUser;
    public UpdateUserMoudle(Context context)
    {
        repostry = new Repostry(context);
        myContext = context;
    }

    public void showdata(EditText editTextname,EditText editTextPass,Button btn){
//        User user1 = repostry.getCurrentUser();
//        editTextname.setText(user1.getUsername());
//        editTextPass.setText(user1.getPass());
        repostry.setCurrentData(new IonComplete() {
            @Override
            public void onCompleteBool(boolean flag) {
                User user1 = repostry.getCurrentUser();
                editTextname.setText(user1.getUsername());
                editTextPass.setText(user1.getPass());
                btn.setEnabled(true);
            }
        });
    }
    public void setUser(EditText editTextName, EditText editTextPass){
        user = new User(editTextName.getText().toString().trim(),editTextPass.getText().toString().trim());
        repostry.setCurrentUser(user);
    }

    public void updateUser(EditText editTextName, EditText editTextPass, IonComplete.IonCompleteInt ionCompleteInt){
        user = new User(editTextName.getText().toString().trim(),editTextPass.getText().toString().trim());
        repostry.updatedata(user,ionCompleteInt);
//        x = repostry.Updateuser(user);
//        if (x == 0){
//            repostry.setCurrentUser(user);
//            return 0;
//        }
//        return x;
    }
}
