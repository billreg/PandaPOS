package com.floki.newsales;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.floki.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.tabs.TabLayout;

public class MainNewSales extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    Chip buttonNewSearchProduct;
    Chip buttonNewSearchByBarcode;
    Chip buttonNewAddToCar;
    Chip buttonNewSalesHistory;

    TabLayout tabsNewSales;


    public MainNewSales() {
    }

    public static MainNewSales newInstance(String param1, String param2) {
        MainNewSales fragment = new MainNewSales();
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
        View view = inflater.inflate(R.layout.fragment_main_new_sales, container, false);

        buttonNewSearchProduct = view.findViewById(R.id.button_new_search_product);
        buttonNewSearchByBarcode = view.findViewById(R.id.button_new_search_by_barcode);
        buttonNewAddToCar = view.findViewById(R.id.button_new_add_to_car);
        buttonNewSalesHistory = view.findViewById(R.id.button_new_sales_history);

        tabsNewSales = view.findViewById(R.id.tabs_new_sales);


        buttonNewSearchProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), NewSalesSearchProduct.class);
                startActivity(intent);
            }
        });
        buttonNewSearchByBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SalesScanner.class);
                startActivity(intent);
            }
        });
        buttonNewAddToCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), NewSaleAddToCar.class);
                startActivity(intent);
            }
        });
        buttonNewSalesHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), NewSalesHistory.class);
                startActivity(intent);
            }
        });

        tabsNewSales.addTab(tabsNewSales.newTab().setText("TODOS LOS PRODUCTOS"));
        tabsNewSales.addTab(tabsNewSales.newTab().setText("VENTAS FRECUENTES"));

        tabsNewSales.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0 ){
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.frame_layout_new_sales, new NewSalesAllProducts()).commit();
                }
                if (tab.getPosition() == 1 ){
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.frame_layout_new_sales, new NewSalesFrequents()).commit();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_layout_new_sales, new NewSalesAllProducts()).commit();
            tabsNewSales.getTabAt(0).select();
        }

        return view;
    }
}