package com.example.tkfinalproject.Utility;

//import com.twilio.Twilio;
//import com.twilio.rest.api.v2010.account.Message;
//import com.twilio.type.PhoneNumber;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * CommunicationHelper is a helper class that provides methods to send emails and SMS messages.
 * It also handles SMS permission requests and results.
 */
public class CommunicationHelper {
    private static final int REQUEST_CODE_SEND_EMAIL = 1; // Request code for sending email
    UtilityClass utilityClass; // Utility class for showing alerts and other utilities

    /**
     * Constructor for CommunicationHelper.
     *
     * @param activity The activity context used to initialize the helper.
     */
    public CommunicationHelper(Activity activity) {
        // Context of the application
        Context context = activity.getApplicationContext();
        utilityClass = new UtilityClass(context);
    }

    /**
     * Sends an email using an email client. The email content is constructed based on the provided
     * InfoMeassge object.
     *
     * @param activity    The activity context used to start the email intent.
     * @param to          The array of recipient email addresses.
     * @param infoMeassge The object containing the information to be included in the email body.
     */
    public static void sendEmail(Activity activity, String[] to, InfoMeassge infoMeassge) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "", null));
        emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "פרטי עסקת הטרייד-אין עם RePepHole");
        String messageBody = "תודה " + infoMeassge.getName() + " שביצעת טרייד–אין עם RePepHole\n" +
                "פרטי הטרייד–אין:\n" +
                "מכשיר – " + infoMeassge.getPhone().getCurrentPhone() + "\n" +
                "זיכוי – " + infoMeassge.getPhone().getAmount() + "₪" + "\n" +
                "מצב המכשיר – " + infoMeassge.getPhone().getStauts() + "\n" +
                "דרך התשלום – " + infoMeassge.getMethod() + "\n" +
                "ניפגש בפעמים הבאות!";
        emailIntent.putExtra(Intent.EXTRA_TEXT, messageBody);

        try {
            activity.startActivityForResult(Intent.createChooser(emailIntent, "Send email..."), REQUEST_CODE_SEND_EMAIL);
        } catch (android.content.ActivityNotFoundException ex) {
            if (activity instanceof EmailCallback) {
                ((EmailCallback) activity).onEmailReasult(false);
            }
        }
    }


    /**
     * Sends an SMS message. The message content is constructed based on the provided InfoMeassge object.
     * Uses the TwilioSMS service to send the message.
     *
     * @param phoneNumber The recipient phone number.
     * @param infoMeassge The object containing the information to be included in the SMS body.
     */
    public void sendSms(String phoneNumber, InfoMeassge infoMeassge) {
        String messageBody = "תודה " + infoMeassge.getName() + " שביצעת טרייד–אין עם RePepHole\n" +
                "פרטי הטרייד–אין:\n" +
                "מכשיר – " + infoMeassge.getPhone().getCurrentPhone() + "\n" +
                "זיכוי – " + infoMeassge.getPhone().getAmount() + "₪" + "\n" +
                "מצב המכשיר – " + infoMeassge.getPhone().getStauts() + "\n" +
                "דרך התשלום – " + infoMeassge.getMethod() + "\n" +
                "ניפגש בפעמים הבאות!";
        TwilioSMS.sendSMS(phoneNumber, messageBody);
    }
}

