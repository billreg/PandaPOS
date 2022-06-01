package com.floki.controller;

import com.floki.entity.AuthenticationRequest;
import com.floki.entity.AuthenticationResponse;
import com.floki.service.AuthenticationService;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AuthenticationController {

    private String  baseUrl = "https://flokipos.herokuapp.com/";

    public AuthenticationResponse getToken(AuthenticationRequest authenticationRequest) throws IOException {
        AuthenticationResponse authenticationResponse = null;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        AuthenticationService authenticationService = retrofit.create(AuthenticationService.class);
        Call<AuthenticationResponse> call = authenticationService.getToken(authenticationRequest);

        Response<AuthenticationResponse> response = call.execute();

        if (response.isSuccessful()){
            authenticationResponse = response.body();
            return authenticationResponse;
        }
        return null;
    }

    public Boolean isTokenExpired(AuthenticationResponse authenticationResponse) throws IOException {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        AuthenticationService authenticationService = retrofit.create(AuthenticationService.class);
        Call<Boolean> call = authenticationService.isTokenExpired(authenticationResponse);

        Response<Boolean> response = call.execute();

        if (response.isSuccessful()){
            return response.body();
        }
        return null;
    }
}
