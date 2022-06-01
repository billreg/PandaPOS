package com.floki.newsales;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.widget.ListView;

import com.floki.R;
import com.floki.adapter.ProductSalesAdapter;
import com.floki.adapter.ProductSalesAdapterAdd;
import com.floki.entity.Product;
import com.floki.sqlite.SqliteHelperJarvis;

import java.util.ArrayList;
import java.util.List;

public class SalesCarAddFrequentProduct extends AppCompatActivity {

    private ListView newListProducts;
    private SqliteHelperJarvis sqliteHelperJarvis;
    private ArrayList<Product> productListViewArrayList = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_car_add_frequent_product);

        newListProducts =findViewById(R.id.list_add_frequent_product);

        sqliteHelperJarvis = SqliteHelperJarvis.getInstance(getBaseContext());

        List<Integer> lisInteger = sqliteHelperJarvis.listProductsConsumptionsFrequent();
        List<Product> productList = sqliteHelperJarvis.listProductByFrequency(lisInteger);
        for (Product product: productList){
            productListViewArrayList.add(product);
        }
        ProductSalesAdapterAdd productSalesAdapter = new ProductSalesAdapterAdd(this, productListViewArrayList);
        newListProducts.setAdapter(productSalesAdapter);
    }
}