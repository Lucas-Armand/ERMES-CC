package com.bomroboto.smartcalendar.interfaces;

import com.bomroboto.smartcalendar.models.Contact;
import com.bomroboto.smartcalendar.models.ContactHolder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Andrei Benincasa on 22/02/2017.
 * andrei.benincasa@gmail.com
 */
public interface RandomContactsService
{
    String BASE_URL = "https://randomuser.me/api/";

    @GET(".")
    Call<ContactHolder> getContacts(@Query("results") int results);//@Path("user") String user);
}
