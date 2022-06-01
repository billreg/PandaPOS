package com.floki.service;

import com.floki.entity.Business;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HTTP;
import retrofit2.http.Path;

public interface BusinessService {
    @HTTP(method = "POST", path = "/floki/business/save", hasBody = true)
    Call<Business> save(@Body Business business);

    @HTTP(method = "GET", path = "/floki/business/{id}")
    Call<Business> getByBusinessId(@Path("id") int businessId);
}
