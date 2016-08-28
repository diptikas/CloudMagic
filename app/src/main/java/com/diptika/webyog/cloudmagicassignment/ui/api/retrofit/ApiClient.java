package com.diptika.webyog.cloudmagicassignment.ui.api.retrofit;

/**
 * Created by diptika.s on 27/08/16.
 */

    import retrofit2.Retrofit;
    import retrofit2.converter.gson.GsonConverterFactory;


    public class ApiClient {

        public static final String BASE_URL = "http://192.168.1.9:8088/api/";
        private static Retrofit retrofit = null;

        public static Retrofit getClient() {
            if (retrofit==null) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
            return retrofit;
        }
    }

