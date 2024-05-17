package com.example.tkfinalproject.Utility;
import android.icu.text.IDNA;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SmsSender {
    // Your Twilio Account SID and Auth Token
    public static final String ACCOUNT_SID = "ACc843eee2825429287d84e7ae65054db2";
    public static final String AUTH_TOKEN = "2753390a807168732e1424c9f626eebd";

    public static void sendSms(String toPhoneNumber, InfoMeassge infoMeassge) {
        // Initialize the Twilio client
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        String messageBody = "תודה " + infoMeassge.getName() + " שביצעת טרייד-אין עם RePepHole\n" +
                "פרטי הטרייד אין:\n" +
                "מכשיר - " + infoMeassge.getPhone().getCurrentPhone() + "\n" +
                "זיכוי – " + infoMeassge.getPhone().getAmount() + "\n" +
                "מצב המכשיר - " + infoMeassge.getPhone().getStauts() + "\n" +
                "דרך התשלום – " + infoMeassge.getMethod() + "\n" +
                "ניפגש בפעמים הבאות!";


        // Send the SMS
        Message message = Message.creator(
                        new PhoneNumber(toPhoneNumber), // to
                        new PhoneNumber("+15705368240"), // from
                        messageBody)
                .create();

        System.out.println("Message sent successfully: " + message.getSid());
    }
}

