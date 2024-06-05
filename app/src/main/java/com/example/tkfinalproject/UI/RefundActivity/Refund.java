package com.example.tkfinalproject.UI.RefundActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.tkfinalproject.R;
import com.example.tkfinalproject.UI.FirstPage.FirstPage;
import com.example.tkfinalproject.UI.Fragments.cancelFragment;
import com.example.tkfinalproject.UI.Fragments.cashfragment;
import com.example.tkfinalproject.UI.Fragments.creditCard;
import com.example.tkfinalproject.UI.Fragments.firstFragment;
import com.example.tkfinalproject.Utility.BaseActivity;
import com.example.tkfinalproject.Utility.Phone;

/**
 * The Refund class extends BaseActivity to handle refund operations.
 * It allows switching between different fragments and updates the displayed price.
 */
public class Refund extends BaseActivity {

    /** EditText to display the final price. */
    EditText editText;

    /** Phone object to hold price information. */
    Phone phone;

    /** Boolean to indicate if the activity should be reset. */
    boolean reset;

    /** Integer to track the current fragment. */
    int currentFragment;

    /** Buttons to switch between fragments. */
    private Button button1, button2, button3, button4;

    /** Fragments for different refund operations. */
    private Fragment fragment1, fragment2, fragment3, fragment4;

    /**
     * Called when the activity is first created. This is where you should do all of your normal static set up:
     * create views, bind data to lists, etc. This method also provides you with a Bundle containing the activity's
     * previously frozen state, if there was one.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this
     *                           Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     *                           <b>Note: Otherwise it is null.</b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refund);

        // Initialize fragments
        fragment1 = new creditCard();
        fragment2 = new cancelFragment();
        fragment3 = new firstFragment();
        fragment4 = new cashfragment();

        // Initialize buttons
        button1 = findViewById(R.id.creditbutton);
        button2 = findViewById(R.id.cancel);
        button3 = findViewById(R.id.info);
        button4 = findViewById(R.id.cash);

        // Set initial visibility
        button1.setVisibility(View.VISIBLE);
        button2.setVisibility(View.VISIBLE);
        button3.setVisibility(View.GONE);
        button4.setVisibility(View.VISIBLE);

        // Initialize EditText and set price
        editText = findViewById(R.id.finalprice);
        phone = (Phone) getIntent().getSerializableExtra("price");
        try {
            assert phone != null;
            editText.setText(phone.getAmount() + "₪");
        } catch (Exception e) {
            Intent intent = new Intent(Refund.this, FirstPage.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

        // Set button click listeners
        button1.setOnClickListener(view -> showFragment(1));
        button2.setOnClickListener(view -> showFragment(2));
        button3.setOnClickListener(view -> showFragment(3));
        button4.setOnClickListener(view -> showFragment(4));

        reset = true;
        if (savedInstanceState != null) {
            showFragment(savedInstanceState.getInt("code"));
            if (savedInstanceState.getBoolean("reset")) {
                savedInstanceState.putBoolean("reset", false);
                reset = false;
                this.recreate();
            }
        } else {
            showFragment(3);
        }
    }

    /**
     * Saves the state of the activity. This method is called before the activity may be killed
     * so that when it comes back some time in the future it can restore its state.
     *
     * @param outState Bundle in which to place your saved state.
     */
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("code", currentFragment);
        outState.putBoolean("reset", reset);
    }

    /**
     * Retrieves the root layout ID for the activity. This is used to set the content view.
     *
     * @return The root layout ID.
     */
    @Override
    protected int getRootLayoutId() {
        return R.id.refundlayout;
    }

    /**
     * Displays the fragment based on the provided code.
     * Updates the visibility of buttons based on the current fragment.
     *
     * @param code The code representing the fragment to be displayed.
     */
    private void showFragment(int code) {
        currentFragment = code;
        switch (code) {
            case 1:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, fragment1)
                        .commit();
                break;
            case 2:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, fragment2)
                        .commit();
                break;
            case 3:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, fragment3)
                        .commit();
                break;
            case 4:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, fragment4)
                        .commit();
        }

        // Update button visibility based on the current fragment
        if (code == 1) {
            button1.setVisibility(View.GONE);
            button2.setVisibility(View.VISIBLE);
            button3.setVisibility(View.VISIBLE);
            button4.setVisibility(View.VISIBLE);
        } else if (code == 2) {
            button1.setVisibility(View.VISIBLE);
            button2.setVisibility(View.GONE);
            button3.setVisibility(View.VISIBLE);
            button4.setVisibility(View.VISIBLE);
        } else if (code == 3) {
            button1.setVisibility(View.VISIBLE);
            button2.setVisibility(View.VISIBLE);
            button3.setVisibility(View.GONE);
            button4.setVisibility(View.VISIBLE);
        } else if (code == 4) {
            button1.setVisibility(View.VISIBLE);
            button2.setVisibility(View.VISIBLE);
            button3.setVisibility(View.VISIBLE);
            button4.setVisibility(View.GONE);
        }
    }
}
