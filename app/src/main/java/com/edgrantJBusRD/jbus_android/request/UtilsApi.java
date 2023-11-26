package com.edgrantJBusRD.jbus_android.request;
public class UtilsApi {
//    public static final String BASE_URL_API = "http://192.168.22.195:5000/"; //!jangan lupa diganti kalau wifinya beda
    public static final String BASE_URL_API = "http://192.168.110.223:5000/"; //!jangan lupa diganti kalau wifinya beda
    public static BaseApiService getApiService() {
        return
                RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }
}