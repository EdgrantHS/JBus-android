package com.edgrantJBusRD.jbus_android.request;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UtilsApi {
    public static String BASE_URL_API = "http://192.168.26.195:5000/"; //tidak jadi final agar dapat diubah
//    public static final String BASE_URL_API = "http://192.168.56.1:5000/"; //sisesa
//    public static final String BASE_URL_API = "http://10.10.50.73:5000/"; //UI

    public static BaseApiService getApiService() {
        System.out.println("util " + BASE_URL_API);
        return
                RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }

}