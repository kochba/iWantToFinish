package com.example.tkfinalproject.Utility;

public class TwilioSMS {

    private static final String ACCOUNT_SID = "ACc843eee2825429287d84e7ae65054db2";
    private static final String AUTH_TOKEN = "0ea50a0762e6a25ce74298358333c9b0";
    private static final String TWILIO_PHONE_NUMBER = "+15705368240";

    public static void sendSMS(String toPhoneNumber, String message) {
//        String url = "https://api.twilio.com/2010-04-01/Accounts/" + ACCOUNT_SID + "/Messages";
//        String postData = "To=" + toPhoneNumber + "&From=" + TWILIO_PHONE_NUMBER + "&Body=" + message;
//
//        new AsyncTask<String, Void, String>() {
//            @Override
//            protected String doInBackground(String... params) {
//                try {
//                    URL url = new URL(params[0]);
//                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                    conn.setDoOutput(true);
//                    conn.setRequestMethod("POST");
//                    conn.setRequestProperty("Authorization", "Basic " + android.util.Base64.encodeToString((ACCOUNT_SID + ":" + AUTH_TOKEN).getBytes(), android.util.Base64.DEFAULT).replace("\n", ""));
//                    conn.getOutputStream().write(postData.getBytes("UTF-8"));
//
//                    Scanner scanner = new Scanner(conn.getInputStream(), "UTF-8");
//                    String response = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
//                    scanner.close();
//                    return response;
//                } catch (Exception e) {
//                    Log.e("Twilio", "Error: " + e.getMessage());
//                    return null;
//                }
//            }
//
//            @Override
//            protected void onPostExecute(String result) {
//                if (result != null) {
//                    Log.d("Twilio", "Response: " + result);
//                    // Handle success or failure as per your app's requirement
//                } else {
//                    Log.e("Twilio", "Failed to send SMS");
//                    // Handle failure
//                }
//            }
//        }.execute(url);
    }

//    private static String changePhoneNumber(String toPhoneNumber) {
//        String newPhone = "+972";
//        for (int i = 1; i < 10; i++) {
//            newPhone += toPhoneNumber.charAt(i);
//        }
//        return newPhone;
//    }
}