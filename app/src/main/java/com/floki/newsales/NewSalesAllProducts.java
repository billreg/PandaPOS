package com.floki.newsales;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.floki.R;
import com.floki.adapter.ProductSalesAdapter;
import com.floki.entity.Business;
import com.floki.entity.Category;
import com.floki.entity.Consumption;
import com.floki.entity.ListPreferences;
import com.floki.entity.Product;
import com.floki.entity.Sale;
import com.floki.entity.Subcategory;
import com.floki.settings.SettingPreferences;
import com.floki.sqlite.SqliteHelperJarvis;
import com.google.android.material.chip.Chip;
import com.google.android.material.tabs.TabLayout;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class NewSalesAllProducts extends Fragment {

    private String mParam1;
    private String mParam2;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";



    ListView newListProducts;
    SqliteHelperJarvis sqliteHelperJarvis;
    private ArrayList<Product> productListViewArrayList = new ArrayList<>();

    ProductSalesAdapter productSalesAdapter;
    private SettingPreferences settingPreferences;
    private ListPreferences listPreferences;


    int quantityMenos;
    int quantityPlus;
    Consumption consumption = new Consumption();

    Chip menossales;
    TextView newSalesQuantity;
    Chip massales;


    public NewSalesAllProducts() {
    }

    public static NewSalesAllProducts newInstance(String param1, String param2) {
        NewSalesAllProducts fragment = new NewSalesAllProducts();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_new_sales_all_products, container, false);

        TabLayout tabLayoutSubcategorySales = view.findViewById(R.id.tabs_subcategory_new_sales);
        newListProducts =view.findViewById(R.id.new_list_products);

        sqliteHelperJarvis = SqliteHelperJarvis.getInstance(getActivity().getBaseContext());
        listPreferences = new SettingPreferences(getContext()).getListPreferences();


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
            ProductSalesAdapter productSalesAdapter = new ProductSalesAdapter(getContext(), productListViewArrayList);
            newListProducts.setAdapter(productSalesAdapter);
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
                ProductSalesAdapter productSalesAdapter = new ProductSalesAdapter(getContext(), productListViewArrayList);
                newListProducts.setAdapter(productSalesAdapter);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return view;
    }

}