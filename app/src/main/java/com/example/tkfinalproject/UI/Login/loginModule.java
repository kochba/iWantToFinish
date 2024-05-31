package com.example.tkfinalproject.UI.Login;

import android.content.Context;

import com.example.tkfinalproject.DB.MyFireBaseHelper;
import com.example.tkfinalproject.RePostry.Repostry;
import com.example.tkfinalproject.RePostry.User;

public class loginModule {
    Repostry repostry;
    public loginModule(Context context){
        repostry = new Repostry(context);
    }

    public void UserExsist(User user, MyFireBaseHelper.checkUser checkUser){
        repostry.IsExisit(user.getUsername(),user.getPass(),checkUser);
    }
    public void setUser(User user){
        repostry.setCurrentUser(user);
    }

    public boolean addUser(User user){
        return repostry.addDbUser(user);
    }

}
