package com.example.tkfinalproject.UI.LogOut;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.tkfinalproject.RePostry.Repostry;
import com.example.tkfinalproject.RePostry.User;
import com.example.tkfinalproject.UI.mainactivity.MainActivity;
import com.example.tkfinalproject.Utility.UtilityClass;

public class logoutModule {
    private Repostry repostry;
    Intent intent;
    Context myContex;
    UtilityClass utilityClass;
    SharedPreferences.Editor editor;
    SharedPreferences sp;
    public logoutModule(Context context)
    {
        myContex = context;
        sp = myContex.getSharedPreferences("MyUserPerfs" , Context.MODE_PRIVATE);
        editor = sp.edit();
        repostry = new Repostry(context);
        utilityClass = new UtilityClass(context);
    }
    public void makeLogout(){
        editor.putString("UserName","");
        editor.putString("UserPass","");
        editor.commit();
        repostry.setCurrentUser(new User("",""));
        intent = new Intent(myContex, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        myContex.startActivity(intent);
    }
}
