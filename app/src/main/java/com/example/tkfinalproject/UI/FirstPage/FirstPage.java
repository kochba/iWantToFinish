package com.example.tkfinalproject.UI.FirstPage;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.example.tkfinalproject.R;
import com.example.tkfinalproject.UI.LogOut.LogOut1;
import com.example.tkfinalproject.UI.SelectPhone.SelectNewPhone;
import com.example.tkfinalproject.UI.UpdateUser.UpdateUser;
import com.example.tkfinalproject.Utility.BaseActivity;

public class FirstPage extends BaseActivity implements View.OnClickListener {
    ImageView updateicon,logouticon;
    Intent intent;
    Button btn;
    OnBackPressedCallback callback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
        updateicon = findViewById(R.id.updatepass);
        logouticon = findViewById(R.id.logouticon);
        btn = findViewById(R.id.newphone);
        btn.setOnClickListener(this);
        updateicon.setOnClickListener(this);
        logouticon.setOnClickListener(this);
        btn.setBackgroundResource(R.drawable.button);
    }
    @Override
    protected int getRootLayoutId() {
        return R.id.Firstpagelayout;
    }

    @Override
    public void onClick(View v) {
        //getSupportFragmentManager().beginTransaction().add(R.id.logoutlayout,log,null).commit();
        if (updateicon == v) {
            intent = new Intent(FirstPage.this, UpdateUser.class);
            startActivity(intent);
        } else if (logouticon == v) {
            intent = new Intent(FirstPage.this, LogOut1.class);
            startActivity(intent);
        }
        else {
            intent = new Intent(FirstPage.this, SelectNewPhone.class);
            startActivity(intent);
        }
    }

//    private void showMenu(View v){
//        PopupMenu menu = new PopupMenu(FirstPage.this,v);
//        menu.getMenuInflater().inflate(R.menu.popup_menu_firstpage,menu.getMenu());
//        menu.show();
//        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem menuItem) {
//                if (menuItem.getItemId() == R.id.updatebutton){
//                    intent = new Intent(FirstPage.this, UpdateUser.class);
//                    startActivity(intent);
//                }
//                else {
//                    intent = new Intent(FirstPage.this, LogOut1.class);
//                    startActivity(intent);
//                }
//                return false;
//            }
//        });
//    }
}