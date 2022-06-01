package com.floki.service;

import com.floki.entity.Consumption;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HTTP;

public interface ConsumptionService {

    @HTTP(method = "POST", path = "/floki/consumption/saveConsumptionsByBusinessId", hasBody = true)
    Call<List<Consumption>> saveConsumptionsByBusinessId(@Body List<Consumption> consumptionList);

/*    @HTTP(method = "GET", path = "/jarvis/products/getByBusinessId/{id}")
    Call<List<Product>> getByBusinessId(@Path("id") int categoryId);*/

}
