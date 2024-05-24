package com.example.tkfinalproject.UI.LastPage;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tkfinalproject.R;
import com.example.tkfinalproject.Utility.AutoResizeEditText;
import com.example.tkfinalproject.Utility.BaseActivity;

public class lastPgae extends AppCompatActivity implements View.OnClickListener {
    AutoResizeEditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_pgae);
        email = findViewById(R.id.email);
    }


//    @Override
//    protected int getRootLayoutId() {
//        return 0;
//    }

    @Override
    public void onClick(View view) {

    }
}