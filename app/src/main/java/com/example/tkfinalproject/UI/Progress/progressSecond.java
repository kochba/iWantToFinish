package com.example.tkfinalproject.UI.Progress;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.tkfinalproject.R;
import com.example.tkfinalproject.UI.FirstPage.FirstPage;
import com.example.tkfinalproject.UI.RefundActivity.Refund;
import com.example.tkfinalproject.Utility.BaseActivity;

/**
 * The progressSecond class extends BaseActivity and implements View.OnClickListener to handle progress tracking with a countdown timer.
 * It updates a progress bar and navigates to another activity when the timer finishes or when the button is clicked.
 */
public class progressSecond extends BaseActivity implements View.OnClickListener {

    /** Progress bar to display the countdown progress. */
    ProgressBar progressBar;

    /** Total time for the countdown timer in milliseconds. */
    private static final long TOTAL_TIME_MILLIS = 1000;

    /** Countdown timer to track the progress. */
    private CountDownTimer countDownTimer;

    /** Intent to start another activity. */
    Intent intent;

    /** Button to cancel the countdown and navigate to another activity. */
    Button btn;

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
        setContentView(R.layout.activity_progress_second);
        progressBar = findViewById(R.id.progressBarSecond);
        btn = findViewById(R.id.progrssSecondBtn);
        btn.setOnClickListener(this);

        // Initialize the CountDownTimer
        countDownTimer = new CountDownTimer(TOTAL_TIME_MILLIS, 100) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Calculate progress percentage
                int progress = (int) (((TOTAL_TIME_MILLIS - millisUntilFinished) * 100) / TOTAL_TIME_MILLIS);
                progressBar.setProgress(progress);
            }

            @Override
            public void onFinish() {
                intent = new Intent(progressSecond.this, Refund.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("price", getIntent().getSerializableExtra("price"));
                startActivity(intent);
            }
        };

        // Start the timer
        countDownTimer.start();
    }

    /**
     * Retrieves the root layout ID for the activity. This is used to set the content view.
     *
     * @return The root layout ID.
     */
    @Override
    protected int getRootLayoutId() {
        return R.id.progresssecondlayout;
    }

    /**
     * Called when the activity is destroyed. This method cancels the countdown timer to avoid memory leaks.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Cancel the timer to avoid memory leaks
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    /**
     * Called when a view has been clicked. This method is invoked when the user clicks the button to cancel the countdown.
     * It cancels the countdown timer and navigates to the FirstPage activity.
     *
     * @param view The view that was clicked.
     */
    @Override
    public void onClick(View view) {
        countDownTimer.cancel();
        intent = new Intent(progressSecond.this, FirstPage.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
