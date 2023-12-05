package com.edgrantJBusRD.jbus_android.request;
public class UtilsApi {
    public static final String BASE_URL_API = "http://192.168.11.195:5000/"; //hotspot
//    public static final String BASE_URL_API = "http://192.168.56.1:5000/"; //sisesa
//    public static final String BASE_URL_API = "http://10.10.50.73:5000/"; //UI

    public static BaseApiService getApiService() {
        return
                RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }
}