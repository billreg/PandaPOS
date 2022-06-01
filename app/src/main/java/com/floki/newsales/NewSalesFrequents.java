package com.floki.newsales;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.floki.R;
import com.floki.entity.Product;
import com.floki.adapter.ProductSalesAdapter;
import com.floki.sqlite.SqliteHelperJarvis;

import java.util.ArrayList;
import java.util.List;

public class NewSalesFrequents extends Fragment {

    private String mParam1;
    private String mParam2;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private ListView newListProducts;
    private SqliteHelperJarvis sqliteHelperJarvis;
    private ArrayList<Product> productListViewArrayList = new ArrayList<>();

    public NewSalesFrequents() {
    }

    public static NewSalesFrequents newInstance(String param1, String param2) {
        NewSalesFrequents fragment = new NewSalesFrequents();
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_sales_frequents, container, false);

        newListProducts =view.findViewById(R.id.new_list_products_frequent);

        sqliteHelperJarvis = SqliteHelperJarvis.getInstance(getActivity().getBaseContext());

        List<Integer> lisInteger = sqliteHelperJarvis.listProductsConsumptionsFrequent();
        List<Product> productList = sqliteHelperJarvis.listProductByFrequency(lisInteger);
        for (Product product: productList){
            productListViewArrayList.add(product);
        }
        ProductSalesAdapter productSalesAdapter = new ProductSalesAdapter(getContext(), productListViewArrayList);
        newListProducts.setAdapter(productSalesAdapter);
        return view;
    }
}