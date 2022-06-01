package com.floki.controller;

import com.floki.entity.Category;
import com.floki.service.CategoryService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CategoryController {

    //private String  baseUrl = "http://10.0.2.2:9090";
    private String  baseUrl = "https://flokipos.herokuapp.com/";
    private Response<List<Category>> response;


    public ArrayList<Category> getAllByBusinessId(){
        ArrayList<Category> listCategories = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CategoryService categoryService = retrofit.create(CategoryService.class);
        Call<List<Category>> call = categoryService.getAll();
        try {
            response = call.execute();
            if (response.isSuccessful()) {
                for(Category category : response.body()) {
                    listCategories.add(category);
                }
            }
            System.out.println("is not successful");
        } catch (IOException e) {
            e.printStackTrace();
        }


        return listCategories;
    }

    public void saveConsumtios(){

    }


}
