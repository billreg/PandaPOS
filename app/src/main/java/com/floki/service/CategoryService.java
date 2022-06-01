package com.floki.service;

import com.floki.entity.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.HTTP;
import retrofit2.http.Path;

public interface CategoryService {

    @HTTP(method = "GET", path = "/floki/categories/all")
    Call<List<Category>> getAll();

}
