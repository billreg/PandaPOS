package com.floki.controller;

import com.floki.entity.Consumption;
import com.floki.service.ConsumptionService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConsumptionController {

    private String  baseUrl = "https://flokipos.herokuapp.com/";

    private Response<List<Consumption>> response;

    public void saveConsumptionByBusinessId(List<Consumption> consumptionList) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ConsumptionService consumptionService = retrofit.create(ConsumptionService.class);
        Call<List<Consumption>> call = consumptionService.saveConsumptionsByBusinessId(consumptionList);


        call.enqueue(new Callback<List<Consumption>>() {
            @Override
            public void onResponse(Call<List<Consumption>> call, Response<List<Consumption>> response) {
            }

            @Override
            public void onFailure(Call<List<Consumption>> call, Throwable t) {

            }
        });

    }

/*
    public ArrayList<Consumption> getConsumptionByBusinessId() {
        ArrayList<Consumption> listProducts = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ConsumptionService productService = retrofit.create(ConsumptionService.class);
        Call<List<Product>> call = productService.getByCategoryId(8);
        try {
            response = call.execute();
            System.out.println("is not successful");
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (response.isSuccessful()) {
            for (Product product : response.body()) {
                listProducts.add(product);
            }
        } else {

        }
        return listProducts;
    }

*/

}
