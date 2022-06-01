package com.floki.controller;

import com.floki.entity.Sale;
import com.floki.service.SaleService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SaleController {

    private String  baseUrl = "https://flokipos.herokuapp.com/";

    public void saveSaleByBusinessId(Sale sale) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        SaleService saleService = retrofit.create(SaleService.class);
        Call<Sale> call = saleService.saveSale(sale);

        call.enqueue(new Callback<Sale>() {
            @Override
            public void onResponse(Call<Sale> call, Response<Sale> response) {
            }

            @Override
            public void onFailure(Call<Sale> call, Throwable t) {

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
