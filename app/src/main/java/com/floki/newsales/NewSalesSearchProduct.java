package com.floki.newsales;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.os.Bundle;
import android.widget.ListView;

import com.floki.R;
import com.floki.entity.Product;
import com.floki.adapter.ProductSalesAdapter;
import com.floki.sqlite.SqliteHelperJarvis;

import java.util.ArrayList;
import java.util.List;

public class NewSalesSearchProduct extends AppCompatActivity {

    SqliteHelperJarvis sqliteHelperJarvis;

    ListView newListProducts;

    private ArrayList<Product> productListViewArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_sales_search_product);

        SearchView searchView = (SearchView) findViewById(R.id.button_sales_search_product);
        newListProducts =findViewById(R.id.new_list_search_product);

        sqliteHelperJarvis = SqliteHelperJarvis.getInstance(getBaseContext());

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
                ProductSalesAdapter productSalesAdapter = new ProductSalesAdapter(getBaseContext(), productListViewArrayList);
                newListProducts.setAdapter(productSalesAdapter);
                return false;
            }
        });
    }
}