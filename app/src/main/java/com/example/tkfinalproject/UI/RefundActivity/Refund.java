package com.example.tkfinalproject.UI.RefundActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.tkfinalproject.R;
import com.example.tkfinalproject.UI.FirstPage.FirstPage;
import com.example.tkfinalproject.UI.Fragments.creditCard;
import com.example.tkfinalproject.UI.Fragments.firstFragment;
import com.example.tkfinalproject.UI.Progress.progerssFirst;
import com.example.tkfinalproject.UI.Progress.progressSecond;
import com.example.tkfinalproject.Utility.LocaleHelper;
import com.example.tkfinalproject.Utility.Phone;

public class Refund extends AppCompatActivity implements View.OnClickListener {
    FragmentManager fragmentManager;
    EditText editText;
    Phone phone;
    private Intent intent;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refund);
        LocaleHelper.setLocale(this, "iw");
        btn = findViewById(R.id.creditbutton);
        btn.setOnClickListener(this);
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, firstFragment.class,null)
                .commit();
        editText = findViewById(R.id.finalprice);
        phone = (Phone)getIntent().getSerializableExtra("price");
        try {
            editText.setText(phone.getAmount()+ "₪");
        } catch (Exception e){
            intent = new Intent(Refund.this, FirstPage.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

    }

    @Override
    public void onClick(View view) {
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, creditCard.class,null)
                .commit();
    }
}