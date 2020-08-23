package com.osamaelsh3rawy.chat.notification;

import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFireBaseMessage extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String sented=remoteMessage.getData().get("sented");
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

        if(firebaseUser != null && sented.equals(firebaseUser.getUid())){
            sendNotifications(remoteMessage);

        }

    }

    private void sendNotifications(RemoteMessage remoteMessage) {

        String user=remoteMessage.getData().get("user");
        String icon=remoteMessage.getData().get("icon");
        String titel=remoteMessage.getData().get("titel");
        String body=remoteMessage.getData().get("body");

        RemoteMessage.Notification notification=remoteMessage.getNotification();
        int j= Integer.parseInt(user.replaceAll("[\\D]",""));

       // Intent intent=new Intent()


    }
}
