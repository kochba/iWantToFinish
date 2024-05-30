package com.example.tkfinalproject.Utility;

//import com.twilio.Twilio;
//import com.twilio.rest.api.v2010.account.Message;
//import com.twilio.type.PhoneNumber;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class CommunicationHelper {
    private static final int SMS_PERMISSION_REQUEST_CODE = 101;
    private Context context;
    private Activity activity;
    UtilityClass utilityClass;

    public CommunicationHelper(Activity activity) {
        this.context = activity.getApplicationContext();
        this.activity = activity;
        utilityClass = new UtilityClass(context);
    }

    // Method to send an email
    public void sendEmail(String[] to, InfoMeassge infoMeassge) {
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
            activity.startActivity(Intent.createChooser(emailIntent, "Send email..."));
        } catch (android.content.ActivityNotFoundException ex) {
            utilityClass.showAlertExp();
        }
    }
//    private boolean hasSmsPermission() {
//        return ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED;
//    }
//
//    // Method to request SMS permission
//    private void requestSmsPermission() {
//        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.SEND_SMS}, SMS_PERMISSION_REQUEST_CODE);
//    }
//
//    // Method to handle the result of permission request
//    public void onRequestPermissionsResult(int requestCode, int[] grantResults) {
//        if (requestCode == SMS_PERMISSION_REQUEST_CODE) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(context, "SMS permission granted", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(context, "SMS permission denied", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

    // Method to send an SMS
    public void sendSms(String phoneNumber, InfoMeassge infoMeassge) {
//        if (hasSmsPermission()){
//            try {
//                SmsManager smsManager = SmsManager.getDefault();
//                String messageBody = "תודה " + infoMeassge.getName() + " שביצעת טרייד-אין עם RePepHole\n" +
//                        "פרטי הטרייד אין:\n" +
//                        "מכשיר - " + infoMeassge.getPhone().getCurrentPhone() + "\n" +
//                        "זיכוי – " + infoMeassge.getPhone().getAmount() + "\n" +
//                        "מצב המכשיר - " + infoMeassge.getPhone().getStauts() + "\n" +
//                        "דרך התשלום – " + infoMeassge.getMethod() + "\n" +
//                        "ניפגש בפעמים הבאות!";
//                smsManager.sendTextMessage(phoneNumber, null, messageBody, null, null);
//                Toast.makeText(context, "SMS sent.", Toast.LENGTH_SHORT).show();
//            } catch (Exception e) {
//                utilityClass.showAlertExp();
//                e.printStackTrace();
//            }
//        }
//        else {
//            requestSmsPermission();
//        }
        String messageBody = "תודה " + infoMeassge.getName() + " שביצעת טרייד–אין עם RePepHole\n" +
                "פרטי הטרייד–אין:\n" +
                "מכשיר – " + infoMeassge.getPhone().getCurrentPhone() + "\n" +
                "זיכוי – " + infoMeassge.getPhone().getAmount() + "₪" + "\n" +
                "מצב המכשיר – " + infoMeassge.getPhone().getStauts() + "\n" +
                "דרך התשלום – " + infoMeassge.getMethod() + "\n" +
                "ניפגש בפעמים הבאות!";
        //TwilioSMS.sendSMS(phoneNumber,messageBody);
    }
}
