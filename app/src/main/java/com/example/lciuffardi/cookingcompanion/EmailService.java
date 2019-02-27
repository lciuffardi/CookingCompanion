package com.example.lciuffardi.cookingcompanion;

/**
 * Created by lciuffardi on 10/16/2017.
 */

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import android.os.Handler;

/**
 * Created by Luigi Ciuffardi on 10/2/2017.
 * Last updated by Luigi Ciuffardi on 2/26/2019.
 */
public class EmailService extends IntentService {
    private static final String TAG = EmailService.class.getName();
    private static final String EMAIL_SOURCE = "cookingcompanionuser@gmail.com";
    private static final String PASSWORD = "nlkdxjocfriqyevj";
    private static final String EMAIL_DESTINATION = "cookingcompaniondevteam@gmail.com";

    private Session session;
    private String email;
    private String message;
    private Handler mHandler;
    private boolean sent = false;

    public EmailService(){
        super("EmailService");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        mHandler = new Handler();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        email = intent.getStringExtra("email");
        message = intent.getStringExtra("message");
        doInBackground();

        mHandler.post(new Runnable(){
            @Override
            public void run(){
                if(sent) {
                    Toast.makeText(getApplicationContext(), "Message has been sent", Toast.LENGTH_LONG).show();
                    ContactUsFragment.sent = false;
                } else {
                    Toast.makeText(getApplicationContext(), "ERROR: Message was unable to be sent"
                            , Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    protected void doInBackground() {

        Properties props = new Properties();

        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        //Creating a new session
        session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    //Authenticating the password
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(EMAIL_SOURCE, PASSWORD);
                    }
                });

        try {

            //Creating MimeMessage object
            MimeMessage devTeamMessage = new MimeMessage(session);
            MimeMessage copyMessage = new MimeMessage(session);

            //Setting sender address
            devTeamMessage.setFrom(new InternetAddress(EMAIL_SOURCE));
            copyMessage.setFrom(new InternetAddress(EMAIL_SOURCE));
            //Adding receiver
            devTeamMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(EMAIL_DESTINATION));
            copyMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            //Adding subject
            devTeamMessage.setSubject("User Feedback from " + email);
            copyMessage.setSubject("DO NOT REPLY: Cooking Companion Message Copy");
            //Adding message
            devTeamMessage.setText(email + " has sent the following message:\n\n" + message);
            copyMessage.setText("You have sent the following feedback to the Cooking Companion " +
                    "Dev Team:\n\n" + "\t\"" + message + "\"" + "\n\n\nThank you for your feedback and we" +
                    " appreciate that you have decided to use Cooking Companion for your cooking" +
                    " needs! :)");

            //Sending email
            Transport.send(devTeamMessage);
            Transport.send(copyMessage);
            sent = true;
            Log.d(TAG, "User's Message has successfully been sent to CookingCompanion DEV Team...");

        } catch (MessagingException e) {
            Log.e(TAG, "Error Sending User's Email to CookingCompanion DEV Team...");
            e.printStackTrace();
        }
    }

}
