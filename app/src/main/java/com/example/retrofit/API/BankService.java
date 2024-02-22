package com.example.retrofit.API;

import com.example.retrofit.Entity.BankResponse;
import com.example.retrofit.Entity.Banks;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface BankService {
    @GET("v2/banks")
    Call<BankResponse> getBanks();
}
