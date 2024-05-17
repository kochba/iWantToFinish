package com.example.tkfinalproject.RePostry;

import android.content.Context;
import android.icu.lang.UScript;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;

import com.example.tkfinalproject.Utility.ConnectivityListener;
import com.example.tkfinalproject.Utility.IonComplete;
import com.example.tkfinalproject.Utility.UtilityClass;
import com.google.android.gms.dynamic.IFragmentWrapper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyFireBaseHelper {
    MyFireBaseHelper myFireBaseHelper;
    FirebaseDatabase database;
    DatabaseReference reference;
    Context myContext;
    DataSnapshot  dataSnapshot;
    UtilityClass utilityClass;
    ExecutorService executorService;
    ConnectivityListener connectivityListener;

    public MyFireBaseHelper(Context context) {
        utilityClass = new UtilityClass(context);
        try {
            executorService = Executors.newSingleThreadExecutor();
            connectivityListener = new ConnectivityListener(context);
            myFireBaseHelper = new MyFireBaseHelper(context);
            database = FirebaseDatabase.getInstance();
            reference = database.getReference("Users");
            myContext = context;
        } catch (Exception e){
            utilityClass.showAlertExp();
        }
    }
    public void addUser(User user, IonComplete ionComplete){
        executorService.execute(() -> {
            try {
                reference.child(user.getUsername()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        ionComplete.onCompleteBool(task.isSuccessful());
                    }
                });
            } catch (Exception e) {
                utilityClass.showAlertExp();
                ionComplete.onCompleteBool(false);
            }
        });
    }
    public void update(User user,IonComplete ionComplete){
        executorService.execute(() -> {
            try {
                Map<String, Object> updates = new HashMap<>();
                updates.put("username", user.getUsername());
                updates.put("pass", user.getPass());
                reference.child(user.getUsername()).updateChildren(updates).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        ionComplete.onCompleteBool(task.isSuccessful());
                    }
                });
            } catch (Exception e) {
                utilityClass.showAlertExp();
                ionComplete.onCompleteBool(false);
            }
        });
    }

    public interface checkUser
    {
        void onCheckedUser(boolean flag);
    }
    public void userNameExsIts(String username , checkUser checkUser){
        executorService.execute(() -> {
            try {
                reference.child(username).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        DataSnapshot dataSnapshot = task.getResult();
                        checkUser.onCheckedUser(task.isSuccessful() && String.valueOf(dataSnapshot.child("username").getValue()).equals(username));
                    }
                });
            } catch (Exception e) {
                utilityClass.showAlertExp();
                checkUser.onCheckedUser(false);
            }
        });
    }
    public void userExsits(User user,checkUser checkUser){
        executorService.execute(() -> {
            try {
                reference.child(user.getUsername()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        DataSnapshot dataSnapshot = task.getResult();
                        if (task.isSuccessful() && String.valueOf(dataSnapshot.child("username").getValue()).equals(user.getUsername())) {
                            checkUser.onCheckedUser(String.valueOf(dataSnapshot.child("pass").getValue()).equals(user.getPass()));
                        } else {
                            checkUser.onCheckedUser(false);
                        }
                    }
                });
            } catch (Exception e) {
                utilityClass.showAlertExp();
                checkUser.onCheckedUser(false);
            }
        });
    }
    public void getUserByName(String username, IonComplete.IonCompleteUser user) {
        executorService.execute(() -> {
            try {
                reference.child(username).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            DataSnapshot dataSnapshot = task.getResult();
                            String retrievedUsername = String.valueOf(dataSnapshot.child("username").getValue());
                            if (username.equals(retrievedUsername)) {
                                String pass = String.valueOf(dataSnapshot.child("pass").getValue());
                                user.onCompleteUser(new User(retrievedUsername, pass));
                            } else {
                                user.onCompleteUser(null);
                            }
                        } else {
                            user.onCompleteUser(null);
                        }
                    }
                });
            } catch (Exception e) {
                utilityClass.showAlertExp();
                user.onCompleteUser(null);
            }
        });
    }



//    public boolean userNameExist(String userName){
//        User user = getUserByName(userName)
//        return user != null && user.getUsername().equals(userName);
//    }
//    public boolean checkUserExistence(User user , checkUser) {
//            @Override
//            public void onCheckedUser(User user) {
//
//            }
//        });
//        boolean b = user1 != null;
//        boolean x = user1.getUsername().equals(user.getUsername());
//        boolean u =  user1.getPass().equals(user.getPass());
//        return user1 != null && user1.getUsername().equals(user.getUsername()) && user1.getPass().equals(user.getPass());
//    }
//    private User getUserByName(String username, checkUser callback){
//        reference.child(username).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                if (!task.isSuccessful()) {
//                    callback.onCheckedUser(null);
//                }
//                else {
//                    callback.onCheckedUser(task.getResult().getValue(User.class));
//                }
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//
//            }
//        });
//        return null;
//    }
//    private ArrayList<User> getUsers(){
//        ArrayList<User> list = new ArrayList<>();
//        reference.child("users").child(userId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                if (!task.isSuccessful()) {
//                    Log.e("firebase", "Error getting data", task.getException());
//                }
//                else {
//                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
//                }
//            }
//        });
//        return  list;
//    }

//    public boolean isExsist(User user){
//        reference.child("Users").orderByChild("username").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        })
//    }

}
