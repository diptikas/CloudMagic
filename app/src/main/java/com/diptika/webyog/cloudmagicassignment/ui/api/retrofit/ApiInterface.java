package com.diptika.webyog.cloudmagicassignment.ui.api.retrofit;

/**
 * Created diptika.s on 27/08/16.
 */
import com.diptika.webyog.cloudmagicassignment.ui.api.model.Message;
import com.diptika.webyog.cloudmagicassignment.ui.api.model.MessageDetail;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Path;



public interface ApiInterface {
    @GET("message/")
    Call<List<Message>> getMessageList();

    @GET("message/{id}")
    Call<MessageDetail> getMessageDetails(@Path("id") int id);

    @DELETE("message/{id}")
    Call<Void> deleteMessage(@Path("id") int id);
}