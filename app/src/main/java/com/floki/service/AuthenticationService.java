package com.floki.service;

import com.floki.entity.AuthenticationRequest;
import com.floki.entity.AuthenticationResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HTTP;

public interface AuthenticationService {
    @HTTP(method = "POST", path = "/floki/auth/authentication", hasBody = true)
    Call<AuthenticationResponse> getToken (@Body AuthenticationRequest authenticationRequest);

    @HTTP(method = "POST", path = "/floki/auth/isTokenExpired", hasBody = true)
    Call<Boolean> isTokenExpired(@Body AuthenticationResponse authenticationResponse);
}
