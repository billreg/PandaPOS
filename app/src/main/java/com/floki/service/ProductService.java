package com.floki.service;

import com.floki.entity.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HTTP;
import retrofit2.http.Path;

public interface ProductService {

    @HTTP(method = "GET", path = "/floki/products/getByCategoryId/{id}")
    Call<List<Product>> getByCategoryId(@Path("id") int categoryId);

    @HTTP(method = "GET", path = "/floki/products/getByBusinessId/{id}")
    Call<List<Product>> getByBusinessId(@Path("id") int businessId);

    @HTTP(method = "POST", path = "/floki/products/saveByBusinessIdAndProductId", hasBody = true)
    Call<Product> saveByBusinessIdAndProductId(@Body Product productId);
}
