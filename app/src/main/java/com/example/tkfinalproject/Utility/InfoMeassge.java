package com.example.tkfinalproject.Utility;

import android.content.Context;

import com.example.tkfinalproject.RePostry.Repostry;

public class InfoMeassge {
    String name;
    Phone phone;
    String Method;
    Repostry repostry;
    public InfoMeassge(Phone phone, String method, Context context) {
        repostry = new Repostry(context);
        this.name = repostry.getCurrentUser().getUsername();
        this.phone = phone;
        this.Method = method;
    }
    public String getName() {
        return name;
    }

    public String getMethod() {
        return Method;
    }
    public Phone getPhone() {
        return phone;
    }
}
