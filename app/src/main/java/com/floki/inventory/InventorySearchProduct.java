package com.floki.inventory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.floki.R;
import com.floki.adapter.ProductAdapter;
import com.floki.entity.Product;
import com.floki.sqlite.SqliteHelperJarvis;

import java.util.ArrayList;
import java.util.List;


public class InventorySearchProduct extends AppCompatActivity {

    SqliteHelperJarvis sqliteHelperJarvis;
    Activity activity;
    private ArrayList<Product> productListViewArrayList = new ArrayList<>();
    ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_search_product);

        SearchView inventorySearchProduct = (SearchView) findViewById(R.id.inventory_search_product);
        ListView listSearchProducts =findViewById(R.id.list_search_products);

        sqliteHelperJarvis = SqliteHelperJarvis.getInstance(getBaseContext());
        activity = this;

        inventorySearchProduct.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!newText.isEmpty()){
                    List<Product> productList = sqliteHelperJarvis.listProductsByName(newText);
                    productListViewArrayList.clear();
                    for (Product product: productList){
                        productListViewArrayList.add(product);
                    }
                    productAdapter = new ProductAdapter(activity, productListViewArrayList);
                    listSearchProducts.setAdapter(productAdapter);
                }else {
                    productListViewArrayList.clear();
                    productAdapter = new ProductAdapter(activity, productListViewArrayList);
                    listSearchProducts.setAdapter(productAdapter);
                }
                return false;
            }
        });

    }
}