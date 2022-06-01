package com.floki.service;

import com.floki.entity.Subcategory;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.HTTP;
import retrofit2.http.Path;

public interface SubcategoryService {

    @HTTP(method = "GET", path = "/floki/subcategories/all")
    Call<List<Subcategory>> getAll();

    @HTTP(method = "GET", path = "/floki/subcategories/getByBusinessId/{id}")
    Call<List<Subcategory>> getByBusinessId(@Path("id") int businessId);

}
