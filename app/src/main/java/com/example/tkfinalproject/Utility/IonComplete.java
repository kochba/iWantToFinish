package com.example.tkfinalproject.Utility;

import com.example.tkfinalproject.RePostry.User;

public interface IonComplete {

    void onCompleteBool(boolean flag);

    public interface IonCompleteInt
    {
        void onCompleteInt(int flag);
    }
    public interface IonCompleteUser{
        void onCompleteUser(User user);
    }
}

