package com.floki.controller;

import com.floki.entity.Business;
import com.floki.service.BusinessService;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BusinessController {
    Business businessreturn;

    private String  baseUrl = "https://flokipos.herokuapp.com/";

    private Response<Business> response;

    public Business saveBusiness(Business business) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        BusinessService businessService = retrofit.create(BusinessService.class);
        Call<Business> call = businessService.save(business);

        try {
            response = call.execute();
            if (response.isSuccessful()){
                return response.body();
            }else {
                return null;
            }
        }catch (Exception e){

        }

        return null;
    }

    public Business getBusinessById(int id){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        BusinessService businessService = retrofit.create(BusinessService.class);
        Call<Business> call = businessService.getByBusinessId(id);

        try {
            response = call.execute();
            if (response.isSuccessful()){
                return response.body();
            }else {
                return null;
            }
        }catch (Exception e){

        }
        return null;
    }


}
