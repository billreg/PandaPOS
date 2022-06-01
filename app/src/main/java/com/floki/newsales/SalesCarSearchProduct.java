package com.floki.newsales;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.floki.R;
import com.floki.entity.Product;
import com.floki.adapter.ProductSalesAdapterAdd;
import com.floki.sqlite.SqliteHelperJarvis;

import java.util.ArrayList;
import java.util.List;

public class SalesCarSearchProduct extends AppCompatActivity {

    SqliteHelperJarvis sqliteHelperJarvis;

    ListView listAddProduct;

    private ArrayList<Product> productListView = new ArrayList<>();
    private ArrayList<Product> productListViewArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_car_search_product);

        ListView listAddProduct =findViewById(R.id.list_add_search_product);
        SearchView searchView = (SearchView) findViewById(R.id.button_sales_search_product);

        sqliteHelperJarvis = SqliteHelperJarvis.getInstance(getBaseContext());

        ProductSalesAdapterAdd productSalesAdapter = new ProductSalesAdapterAdd(this, productListViewArrayList);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                productListViewArrayList.clear();
                List<Product> productList = sqliteHelperJarvis.listProductsByName(newText);
                for (Product product: productList){
                    productListViewArrayList.add(product);
                }
                listAddProduct.setAdapter(productSalesAdapter);
                return false;
            }
        });
    }
}