package com.floki.controller;

import com.floki.entity.Subcategory;
import com.floki.service.SubcategoryService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SubcategoryController {
    //private String  baseUrl = "http://10.0.2.2:9090";
    private String  baseUrl = "https://flokipos.herokuapp.com/";
    private Response<List<Subcategory>> response;

    public ArrayList<Subcategory> getAll(){
        ArrayList<Subcategory> listSubcategories = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        SubcategoryService subcategoryService = retrofit.create(SubcategoryService.class);
        Call<List<Subcategory>> call = subcategoryService.getAll();
        try {
            response = call.execute();
            if (response.isSuccessful()) {
                for(Subcategory subcategory : response.body()) {
                    listSubcategories.add(subcategory);
                }
            }
            System.out.println("is not successful");
        } catch (IOException e) {
            e.printStackTrace();
        }


        return listSubcategories;
    }

    public ArrayList<Subcategory> getAllByBusinessId(){
        ArrayList<Subcategory> listSubcategories = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        SubcategoryService subcategoryService = retrofit.create(SubcategoryService.class);
        Call<List<Subcategory>> call = subcategoryService.getByBusinessId(1);
        try {
            response = call.execute();
            System.out.println("is not successful");
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (response.isSuccessful()) {
            for(Subcategory subcategory : response.body()) {
                listSubcategories.add(subcategory);
            }
        }else{

        }
        return listSubcategories;
    }



}
