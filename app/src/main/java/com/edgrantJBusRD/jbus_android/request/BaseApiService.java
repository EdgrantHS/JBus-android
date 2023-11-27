package com.edgrantJBusRD.jbus_android.request;

import com.edgrantJBusRD.jbus_android.model.Account;
import com.edgrantJBusRD.jbus_android.model.BaseResponse;
import com.edgrantJBusRD.jbus_android.model.Bus;
import com.edgrantJBusRD.jbus_android.model.Renter;
import com.edgrantJBusRD.jbus_android.model.Station;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BaseApiService {
    @POST("account/register")
    Call<BaseResponse<Account>> register (
            @Query("name") String name,
            @Query("email") String email,
            @Query("password") String password
    );
    @POST("account/login")
    Call<BaseResponse<Account>> login (
            @Query("email") String email,
            @Query("password") String password
    );
    @POST("account/{id}/topUp")
    Call<BaseResponse<Double>> topUp(
            @Path("id") int id,
            @Query("amount") double amount
    );

    @POST("account/{id}/registerRenter")
    Call<BaseResponse<Renter>> registerRenter(
            @Path("id") int id,
            @Query("companyName") String companyName,
            @Query("address") String address,
            @Query("phoneNumber") String phoneNumber
    );
    @GET("account/getMyBus")
    Call<BaseResponse<List<Bus>>> getMyBus(
            @Query("accountId") int accountId
    );
    @GET("station/getAll")
    Call<List<Station>> getAllStation();
}