package com.example.tkfinalproject.UI.LastPage;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.tkfinalproject.R;
import com.example.tkfinalproject.Utility.AlarmReceiver;
import com.example.tkfinalproject.Utility.AutoResizeEditText;
import com.example.tkfinalproject.Utility.BaseActivity;
import com.example.tkfinalproject.Utility.CommunicationHelper;
import com.example.tkfinalproject.Utility.InfoMeassge;
import com.example.tkfinalproject.Utility.Phone;
import com.example.tkfinalproject.Utility.UtilityClass;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import org.apache.commons.validator.routines.EmailValidator;

public class lastPgae extends BaseActivity implements View.OnClickListener {
    AutoResizeEditText email;
    UtilityClass utilityClass;
    EditText phone;
    CommunicationHelper communicationHelper;
    Button button;
    InfoMeassge infoMeassge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_pgae);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Channel Name";
            String description = "Channel Description";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("channel_id", name, importance);
            channel.setDescription(description);

            // Register the channel with the system; you can't change the importance or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phonenumber);
        button = findViewById(R.id.finishtradein);
        utilityClass = new UtilityClass(this);
        button.setOnClickListener(this);
        communicationHelper = new CommunicationHelper(this);
    }
    @Override
    protected int getRootLayoutId() {
        return R.id.lastlayout;
    }


    @Override
    public void onClick(View view){
        String phoneNumber = changePhoneNumber(phone.getText().toString().trim());
        if (isValidEmail(email.getText().toString().trim()) && isValidPhoneNumber(phoneNumber,"IL")) {
            infoMeassge = new InfoMeassge((Phone) getIntent().getSerializableExtra("phone"), getIntent().getStringExtra("method"), this);
            setAlarm(view,infoMeassge);
            communicationHelper.sendSms(phoneNumber, infoMeassge);
            communicationHelper.sendEmail(new String[]{email.getText().toString().trim()}, infoMeassge);
        }
        //CommunicationHelper(cardForm.getMobileNumber(),new InfoMeassge(phone,"אשראי",getActivity()));
    }
    @SuppressLint("ScheduleExactAlarm")
    public void setAlarm(View view, InfoMeassge infoMeassge) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        String messageBody = "תודה " + infoMeassge.getName() + " שביצעת טרייד–אין עם RePepHole\n" +
                "פרטי הטרייד–אין:\n" +
                "מכשיר – " + infoMeassge.getPhone().getCurrentPhone() + "\n" +
                "זיכוי – " + infoMeassge.getPhone().getAmount() + "₪" + "\n" +
                "מצב המכשיר – " + infoMeassge.getPhone().getStauts() + "\n" +
                "דרך התשלום – " + infoMeassge.getMethod() + "\n" +
                "ניפגש בפעמים הבאות!";
        intent.putExtra("info",messageBody);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Set the alarm to trigger in 5 seconds
        long triggerTime = System.currentTimeMillis() + 5000;
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);
    }
    public boolean isValidEmail(String email) {
        EmailValidator validator = EmailValidator.getInstance();
        if (validator.isValid(email))
        {
            return true;
        }
        else {
            utilityClass.showAlertEmail();
            return false;
        }
    }
    public boolean isValidPhoneNumber(String phoneNumber, String regionCode) {
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        try {
            Phonenumber.PhoneNumber number = phoneNumberUtil.parse(phoneNumber, regionCode);
            if (phoneNumber.length() == 13 && phoneNumberUtil.isValidNumber(number))
            {
                return true;
            }
            else {
                utilityClass.showAlertPhoneNumber();
                return false;
            }
        } catch (NumberParseException e) {
            utilityClass.showAlertPhoneNumber();
            return false;
        }
    }
    private static String changePhoneNumber(String toPhoneNumber) {
        String newPhone = "+972";
        if (!toPhoneNumber.isEmpty() && toPhoneNumber.charAt(0) == '0'){
            for (int i = 1; i < toPhoneNumber.length(); i++) {
                newPhone += toPhoneNumber.charAt(i);
            }
        }
        return newPhone;
    }
}