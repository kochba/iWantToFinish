package com.example.tkfinalproject.UI.mainactivity;

import android.content.Context;

import com.example.tkfinalproject.DB.MyFireBaseHelper;
import com.example.tkfinalproject.RePostry.Repostry;
import com.example.tkfinalproject.RePostry.User;

public class MainActivityModule {
    Repostry repostry;
    public MainActivityModule(Context context){
        repostry = new Repostry(context);
    }


    public void StartLogin(User user, MyFireBaseHelper.checkUser checkUser){
        repostry.IsExisit(user.getUsername(),user.getPass(),checkUser);
    }
    public void setUser(User user){
        repostry.setCurrentUser(user);
    }

}
