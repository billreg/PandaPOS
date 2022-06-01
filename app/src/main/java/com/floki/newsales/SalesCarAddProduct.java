package com.floki.newsales;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.floki.R;
import com.floki.adapter.ProductSalesAdapter;
import com.floki.adapter.ProductSalesAdapterAdd;
import com.floki.entity.ListPreferences;
import com.floki.entity.Product;
import com.floki.entity.Subcategory;
import com.floki.settings.SettingPreferences;
import com.floki.sqlite.SqliteHelperJarvis;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class SalesCarAddProduct extends AppCompatActivity {

    private Activity activity;
    private ListView newListProducts;
    private ListPreferences listPreferences;

    SqliteHelperJarvis sqliteHelperJarvis;
    private ArrayList<Product> productListViewArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_car_add_product);
        TabLayout tabLayoutSubcategorySales = findViewById(R.id.tabs_subcategory_sales);
        newListProducts =findViewById(R.id.list_add_product);

        activity = this;
        sqliteHelperJarvis = SqliteHelperJarvis.getInstance(getBaseContext());
        listPreferences = new SettingPreferences(getBaseContext()).getListPreferences();


        List<Subcategory> subcategoryList = sqliteHelperJarvis.listSubcategoriesByActive();
        tabLayoutSubcategorySales.removeAllTabs();
        for (Subcategory subcategory: subcategoryList){
            tabLayoutSubcategorySales.addTab(tabLayoutSubcategorySales.newTab().setText(subcategory.getName()));
        }

        if (tabLayoutSubcategorySales.getTabCount() != 0){
            productListViewArrayList.clear();
            TabLayout.Tab tab = tabLayoutSubcategorySales.getTabAt(0);
            String text = tab.getText().toString();
            Subcategory subcategory = sqliteHelperJarvis.getSubcategoryByName(text);
            List<Product> productList = sqliteHelperJarvis.listProductsByCategoryId(subcategory.getSubcategoryId());
            for (Product product: productList){
                productListViewArrayList.add(product);
            }
            ProductSalesAdapterAdd productSalesAdapterAdd = new ProductSalesAdapterAdd(this, productListViewArrayList);
            newListProducts.setAdapter(productSalesAdapterAdd);
        }
        tabLayoutSubcategorySales.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                productListViewArrayList.clear();
                Subcategory subcategory = sqliteHelperJarvis.getSubcategoryByName(tab.getText().toString());
                List<Product> productList = sqliteHelperJarvis.listProductsByCategoryId(subcategory.getSubcategoryId());
                for (Product product: productList){
                    productListViewArrayList.add(product);
                }
                ProductSalesAdapterAdd productSalesAdapterAdd = new ProductSalesAdapterAdd(activity, productListViewArrayList);
                newListProducts.setAdapter(productSalesAdapterAdd);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
}