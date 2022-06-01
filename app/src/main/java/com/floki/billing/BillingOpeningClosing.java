package com.floki.billing;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.floki.R;
import com.floki.entity.Caja;
import com.floki.entity.Consumption;
import com.floki.entity.ListPreferences;
import com.floki.entity.Summary;
import com.floki.settings.SettingPreferences;
import com.floki.sqlite.SqliteHelperJarvis;
import com.google.android.material.chip.Chip;

import java.util.List;
import java.util.Objects;

public class BillingOpeningClosing extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private TextView openingMoney;

    private SqliteHelperJarvis sqliteHelperJarvis;
    private SettingPreferences settingPreferences;
    private ListPreferences listPreferences;

    Caja caja;

    public BillingOpeningClosing() {
    }

    public static BillingOpeningClosing newInstance(String param1, String param2) {
        BillingOpeningClosing fragment = new BillingOpeningClosing();
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_biling_opening_closing, container, false);
        openingMoney = view.findViewById(R.id.opening_money);
        TextView cash = view.findViewById(R.id.cash);
        TextView totalSale = view.findViewById(R.id.total_sale);
        TextView closeMoney = view.findViewById(R.id.close_money);

        Chip openUp = view.findViewById(R.id.open_up);
        Chip close = view.findViewById(R.id.close);

        sqliteHelperJarvis = SqliteHelperJarvis.getInstance(getContext());
        List<Consumption> consumptionList = sqliteHelperJarvis.listConsumptios();
        settingPreferences = new SettingPreferences(getContext());
        listPreferences = new SettingPreferences(getContext()).getListPreferences();

        Summary summary = getBalance(consumptionList);
        caja = listPreferences.getCaja();

        if (Objects.isNull(caja)){
            caja = new Caja();
        }else {
            openingMoney.setText("S/"+caja.getOpeningMoney());
            cash.setText("S/"+caja.getOpeningMoney());
            totalSale.setText("S/"+summary.getTotalOrder());
            closeMoney.setText("S/"+(caja.getOpeningMoney()+summary.getTotalOrder()));
        }

        openingMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openingMoney.setText("");

            }
        });

        openUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                caja.setOpeningMoney(Float.parseFloat(openingMoney.getText().toString()));
                openingMoney.setText("S/"+caja.getOpeningMoney());
                openingMoney.clearFocus();
                cash.setText("S/"+caja.getOpeningMoney());
                listPreferences.setCaja(caja);
                settingPreferences.save(listPreferences);
                openUp.setEnabled(false);
            }
        });

        return view;
    }

    public Summary getBalance(List<Consumption> consumptionList){
        Summary summary = new Summary(0F,0F, 0F, 0F);
        for (Consumption consumption: consumptionList){
            summary.setCapital(summary.getCapital() + consumption.getPricePurchaseUnitXmayor()*consumption.getQuantity());
            summary.setTotalOrder(summary.getTotalOrder() + consumption.getTotalConsumption());
        }
        summary.setBenefit(summary.getTotalOrder() - summary.getCapital());
        return summary;
    }
}