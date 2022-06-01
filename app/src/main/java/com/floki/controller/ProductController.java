package com.floki.controller;

import android.content.Context;
import android.widget.Toast;

import com.floki.entity.Product;
import com.floki.service.ProductService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductController {
    //private String  baseUrl = "http://10.0.2.2:9090";
    private String  baseUrl = "https://flokipos.herokuapp.com/";

    private Response<List<Product>> response;

    public ArrayList<Product> getByProductById() {
        ArrayList<Product> listProducts = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ProductService productService = retrofit.create(ProductService.class);
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

    public ArrayList<Product> getByBusinessId(int businessId) {
        ArrayList<Product> listProducts = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ProductService productService = retrofit.create(ProductService.class);
        Call<List<Product>> call = productService.getByBusinessId(businessId);
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

    public Product updateProductByBusinessId(Product product) {
        Response<Product> responseProduct;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ProductService productService = retrofit.create(ProductService.class);
        Call<Product> call = productService.saveByBusinessIdAndProductId(product);
        try {
            responseProduct = call.execute();
            if (responseProduct.isSuccessful()) {
                product = responseProduct.body();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return product;
    }

    public void updateProductByBusinessIdTwo(Product product, Context context) {
        Response<Product> responseProduct;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ProductService productService = retrofit.create(ProductService.class);
        Call<Product> call = productService.saveByBusinessIdAndProductId(product);

        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Agreagdo correctamente en el servidor", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context, "Error al agregar producto", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Toast.makeText(context, "Network Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
