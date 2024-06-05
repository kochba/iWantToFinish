package com.example.tkfinalproject.UI.LastPage;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.annotation.NonNull;

import com.example.tkfinalproject.R;
import com.example.tkfinalproject.UI.FirstPage.FirstPage;
import com.example.tkfinalproject.Utility.AlarmReceiver;
import com.example.tkfinalproject.Utility.AutoResizeEditText;
import com.example.tkfinalproject.Utility.BaseActivity;
import com.example.tkfinalproject.Utility.CommunicationHelper;
import com.example.tkfinalproject.Utility.EmailCallback;
import com.example.tkfinalproject.Utility.InfoMeassge;
import com.example.tkfinalproject.Utility.Phone;
import com.example.tkfinalproject.Utility.UtilityClass;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import org.apache.commons.validator.routines.EmailValidator;

import java.util.Objects;

/**
 * The lastPgae class represents the final page of the application where users
 * complete their trade-in process. It extends BaseActivity and implements
 * View.OnClickListener and EmailCallback interfaces.
 */
public class lastPgae extends BaseActivity implements View.OnClickListener, EmailCallback {

    // UI components
    AutoResizeEditText email;
    UtilityClass utilityClass;
    TextView textView;
    boolean canGoBack;
    AlertDialog.Builder adb;
    LinearLayout linearLayout;
    EditText phone;
    CommunicationHelper communicationHelper;
    Button button;
    InfoMeassge infoMeassge;
    com.airbnb.lottie.LottieAnimationView animationView;
    OnBackPressedDispatcher onBackPressedDispatcher;

    /**
     * Called when the activity is starting. This is where most initialization
     * should go: calling setContentView(int) to inflate the activity's UI,
     * and using findViewById(int) to programmatically interact with widgets
     * in the UI.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     * previously being shut down then this Bundle contains the data it most
     * recently supplied in onSaveInstanceState(Bundle).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_pgae);

        // Setup notification channel
        CharSequence name = "Channel Name";
        String description = "Channel Description";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel channel = new NotificationChannel("channel_id", name, importance);
        channel.setDescription(description);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);

        // Initialize variables
        onBackPressedDispatcher = getOnBackPressedDispatcher();
        canGoBack = true;
        textView = findViewById(R.id.tv);
        linearLayout = findViewById(R.id.hideLast);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phonenumber);
        button = findViewById(R.id.finishtradein);
        animationView = findViewById(R.id.animationLast);
        utilityClass = new UtilityClass(this);
        button.setOnClickListener(this);
        communicationHelper = new CommunicationHelper(this);

        // Handle back press callback
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (canGoBack) {
                    setEnabled(false);
                    onBackPressedDispatcher.onBackPressed();
                }
            }
        };
        onBackPressedDispatcher.addCallback(this, callback);
    }

    private static final int REQUEST_CODE_SEND_EMAIL = 1;

    /**
     * Called when an activity you launched exits, giving you the requestCode
     * you started it with, the resultCode it returned, and any additional
     * data from it.
     *
     * @param requestCode The integer request code originally supplied to
     * startActivityForResult(), allowing you to identify who this result came from.
     * @param resultCode The integer result code returned by the child activity
     * through its setResult().
     * @param data An Intent, which can return result data to the caller
     * (various data can be attached to Intent "extras").
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SEND_EMAIL) {
            onEmailReasult(true);
        }
    }

    /**
     * Called when the email result is received.
     *
     * @param succsees A boolean indicating whether the email was successfully sent.
     */
    @Override
    public void onEmailReasult(boolean succsees) {
        if (succsees) {
            textView.setText("תודה שביצעת טרייד-אין איתנו!");
            canGoBack = false;
            Intent intent = new Intent(this, FirstPage.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            for (int i = 0; i < linearLayout.getChildCount(); i++) {
                linearLayout.getChildAt(i).setVisibility(View.GONE);
            }
            animationView.setVisibility(View.VISIBLE);
            animationView.setRepeatCount(0);
            animationView.playAnimation();
            animationView.addAnimatorListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(@NonNull Animator animator) {
                    // Called when the animation starts
                }

                @Override
                public void onAnimationEnd(@NonNull Animator animator) {
                    // Called when the animation ends
                    runOnUiThread(() -> startActivity(intent));
                }

                @Override
                public void onAnimationCancel(@NonNull Animator animator) {
                    // Called when the animation is canceled
                    startActivity(intent);
                }

                @Override
                public void onAnimationRepeat(@NonNull Animator animator) {
                    // Called when the animation repeats
                }
            });
        } else {
            adb = new AlertDialog.Builder(this);
            adb.setTitle("יש בעיה חבר!");
            adb.setMessage("שלח לעצמך את המייל לסיום תהליך הטרייד-אין");
            adb.setCancelable(false);
            adb.setPositiveButton("הבנתי", (dialog, which) -> {
                // Close the dialog
            });
            adb.create().show();
        }
    }

    /**
     * Get the root layout ID of the activity.
     *
     * @return The root layout ID.
     */
    @Override
    protected int getRootLayoutId() {
        return R.id.lastlayout;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param view The view that was clicked.
     */
    @Override
    public void onClick(View view) {
        String phoneNumber = changePhoneNumber(phone.getText().toString().trim());
        if (isValidEmail(Objects.requireNonNull(email.getText()).toString().trim()) && isValidPhoneNumber(phoneNumber, "IL")) {
            infoMeassge = new InfoMeassge((Phone) getIntent().getSerializableExtra("phone"), getIntent().getStringExtra("method"), this);
            setAlarm(infoMeassge);
            communicationHelper.sendSms(phoneNumber, infoMeassge);
            CommunicationHelper.sendEmail(this, new String[]{email.getText().toString().trim()}, infoMeassge);
        }
    }

    /**
     * Set an alarm to notify the user.
     *
     * @param infoMeassge The message to be included in the alarm notification.
     */
    @SuppressLint("ScheduleExactAlarm")
    public void setAlarm(InfoMeassge infoMeassge) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        String messageBody = "תודה " + infoMeassge.getName() + " שביצעת טרייד–אין עם RePepHole\n" +
                "פרטי הטרייד–אין:\n" +
                "מכשיר – " + infoMeassge.getPhone().getCurrentPhone() + "\n" +
                "זיכוי – " + infoMeassge.getPhone().getAmount() + "₪" + "\n" +
                "מצב המכשיר – " + infoMeassge.getPhone().getStauts() + "\n" +
                "דרך התשלום – " + infoMeassge.getMethod() + "\n" +
                "ניפגש בפעמים הבאות!";
        intent.putExtra("info", messageBody);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        long triggerTime = System.currentTimeMillis() + 3000;
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);
    }

    /**
     * Check if the given email is valid.
     *
     * @param email The email address to be validated.
     * @return true if the email is valid, false otherwise.
     */
    public boolean isValidEmail(String email) {
        EmailValidator validator = EmailValidator.getInstance();
        if (validator.isValid(email)) {
            return true;
        } else {
            utilityClass.showAlertEmail();
            return false;
        }
    }

    /**
     * Check if the given phone number is valid.
     *
     * @param phoneNumber The phone number to be validated.
     * @param regionCode The region code for the phone number.
     * @return true if the phone number is valid, false otherwise.
     */
    public boolean isValidPhoneNumber(String phoneNumber, String regionCode) {
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        try {
            Phonenumber.PhoneNumber number = phoneNumberUtil.parse(phoneNumber, regionCode);
            if (phoneNumber.length() == 13 && phoneNumberUtil.isValidNumber(number)) {
                return true;
            } else {
                utilityClass.showAlertPhoneNumber();
                return false;
            }
        } catch (NumberParseException e) {
            utilityClass.showAlertPhoneNumber();
            return false;
        }
    }

    /**
     * Change the phone number to the desired format.
     *
     * @param toPhoneNumber The phone number to be changed.
     * @return The formatted phone number.
     */
    private static String changePhoneNumber(String toPhoneNumber) {
        StringBuilder newPhone = new StringBuilder("+972");
        if (!toPhoneNumber.isEmpty() && toPhoneNumber.charAt(0) == '0') {
            for (int i = 1; i < toPhoneNumber.length(); i++) {
                newPhone.append(toPhoneNumber.charAt(i));
            }
        }
        return newPhone.toString();
    }
}