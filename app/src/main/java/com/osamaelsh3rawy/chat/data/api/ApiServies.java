package com.osamaelsh3rawy.chat.data.api;

import com.osamaelsh3rawy.chat.notification.MyRessponse;
import com.osamaelsh3rawy.chat.notification.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiServies {
    @Headers(
            {
        "Content-Type:application/json",
        "Authorization:Key=AAAA25NhU-g:APA91bHk87E5B3F24kZJGqviyhrC_AsS9MdCYPLPOWShRdaeViqNUt7l-ZrNjaGfX50hZMOsMPrjMBFRX9s_ON-nvs0vT8RphIwTw1nXcQDqL7EJC0YM_5_OBe0FMHAKI0ti6eUG7QQz"
            }
            )

    @POST("fcm/send")
    Call<MyRessponse> sendNotifications(@Body Sender body);
}
