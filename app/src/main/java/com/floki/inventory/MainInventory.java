package com.floki.inventory;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.floki.adapter.ProductAdapter;
import com.google.android.material.chip.Chip;
import com.google.android.material.tabs.TabLayout;
import com.floki.R;
import com.floki.entity.Category;
import com.floki.entity.Product;
import com.floki.entity.Subcategory;
import com.floki.sqlite.SqliteHelperJarvis;

import java.util.ArrayList;
import java.util.List;

public class MainInventory extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    TabLayout tabLayoutCategory;
    TabLayout tabLayoutSubcategory;
    ListView listProducts;

    Chip buttonSearchProduct;
    Chip buttonSearchByBarcode;
    Chip buuttonAddProduct;

    private ArrayList<Product> productListViewArrayList = new ArrayList<>();
    ProductAdapter productAdapter;

    public MainInventory() {
    }

    public static MainInventory newInstance(String param1, String param2) {
        MainInventory fragment = new MainInventory();
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
        View view = inflater.inflate(R.layout.fragment_main_inventory, container, false);

        tabLayoutCategory = view.findViewById(R.id.tabs_category);
        tabLayoutSubcategory = view.findViewById(R.id.tabs_subcategory);
        listProducts =view.findViewById(R.id.list_products);

        buttonSearchProduct = view.findViewById(R.id.button_search_product);
        buttonSearchByBarcode = view.findViewById(R.id.button_search_by_barcode);
        buuttonAddProduct = view.findViewById(R.id.buutton_add_product);

        SqliteHelperJarvis sqliteHelperJarvis = SqliteHelperJarvis.getInstance(getActivity().getBaseContext());

        List<Category> categories = sqliteHelperJarvis.listCategoriesByActive();
        for (Category category: categories){
            tabLayoutCategory.addTab(tabLayoutCategory.newTab().setText(category.getName()));
        }



        TabLayout.Tab tab = tabLayoutCategory.getTabAt(0);
        tabLayoutCategory.selectTab(tab);
        tabLayoutCategory.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                List<Subcategory> subcategoryList = sqliteHelperJarvis.listSubcategoriesByCategoryIdAndActive(tab.getPosition()+1);
                tabLayoutSubcategory.removeAllTabs();
                for (Subcategory subcategory: subcategoryList){
                    tabLayoutSubcategory.addTab(tabLayoutSubcategory.newTab().setText(subcategory.getName()));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        if (tabLayoutCategory.getSelectedTabPosition() == 0){

            List<Subcategory> subcategoryList = sqliteHelperJarvis.listSubcategoriesByCategoryIdAndActive(1);
            tabLayoutSubcategory.removeAllTabs();
            for (Subcategory subcategory: subcategoryList){
                tabLayoutSubcategory.addTab(tabLayoutSubcategory.newTab().setText(subcategory.getName()));
            }

            List<Product> productList = sqliteHelperJarvis.listProductsByCategoryId(1);
            for (Product product: productList){
                productListViewArrayList.add(product);
            }
            productAdapter = new ProductAdapter(getContext(), productListViewArrayList);
            listProducts.setAdapter(productAdapter);
        }

        tabLayoutSubcategory.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                productListViewArrayList.clear();
                Subcategory subcategory = sqliteHelperJarvis.getSubcategoryByName(tab.getText().toString());
                List<Product> productList = sqliteHelperJarvis.listProductsByCategoryId(subcategory.getSubcategoryId());
                for (Product product: productList){
                    productListViewArrayList.add(product);
                }
                productAdapter = new ProductAdapter(getContext(), productListViewArrayList);
                listProducts.setAdapter(productAdapter);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



        buttonSearchProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), InventorySearchProduct.class);
                startActivity(intent);
            }
        });

        buttonSearchByBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), InventorySearchProductByBarcode.class);
                startActivity(intent);
            }
        });

        buuttonAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), InventoryAddProduct.class);
                startActivity(intent);
            }
        });

        return view;
    }
}