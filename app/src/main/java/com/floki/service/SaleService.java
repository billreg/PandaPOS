package com.floki.service;

import com.floki.entity.Sale;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HTTP;

public interface SaleService {

    @HTTP(method = "POST", path = "/floki/sales/save", hasBody = true)
    Call<Sale> saveSale(@Body Sale sale);

/*    @HTTP(method = "GET", path = "/jarvis/products/getByBusinessId/{id}")
    Call<List<Product>> getByBusinessId(@Path("id") int categoryId);*/

}
