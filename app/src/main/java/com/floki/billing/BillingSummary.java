package com.floki.billing;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.floki.R;
import com.floki.entity.Consumption;
import com.floki.entity.Summary;
import com.floki.sqlite.SqliteHelperJarvis;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BillingSummary extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    SqliteHelperJarvis sqliteHelperJarvis;

    TextView textCapital;
    TextView textBenefit;
    TextView textAditionalSale;
    TextView textTotalSales;

    PieChart mPieChart;

    //Summary summary;
    List<Consumption> consumptionList;

    public BillingSummary() {

    }

    public static BillingSummary newInstance(String param1, String param2) {
        BillingSummary fragment = new BillingSummary();
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
        View view = inflater.inflate(R.layout.fragment_billing_summary, container, false);

        MaterialSpinner materialSpinner = view.findViewById(R.id.spinner_type_sale_summary);

        mPieChart = (PieChart) view.findViewById(R.id.pie_chart_billing);


        textCapital = view.findViewById(R.id.text_capital);
        textBenefit = view.findViewById(R.id.text_benefit);
        textAditionalSale = view.findViewById(R.id.text_aditional_sale);
        textTotalSales = view.findViewById(R.id.text_total_sales);

        sqliteHelperJarvis = SqliteHelperJarvis.getInstance(getContext());


        String arrayTypeSale[] = {"Total ventas","Por unidad","Por paquete"};
        ArrayAdapter<String> adapterTypeSale = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, arrayTypeSale );
        materialSpinner.setAdapter(adapterTypeSale);

        consumptionList = sqliteHelperJarvis.listConsumptios();

        DecimalFormat formato = new DecimalFormat("#.00");
        Summary summary = new Summary(0F,0F,0F,0F);
        if (consumptionList.size()>0){
/*            Summary summaryUND = showSummaryUND();
            Summary summaryPQT = showSummaryPQT();

            summary.setCapital(summaryUND.getCapital() + summaryPQT.getCapital());
            summary.setBenefit(summaryUND.getBenefit() + summaryPQT.getBenefit());
            summary.setExtras(summaryUND.getExtras() + summaryPQT.getExtras());
            summary.setTotalOrder(summaryUND.getTotalOrder() + summaryPQT.getTotalOrder());*/

            textCapital.setText("S/"+formato.format(summary.getCapital()));
            textBenefit.setText("S/"+formato.format(summary.getBenefit()));
            textAditionalSale.setText("S/"+formato.format(summary.getExtras()));
            textTotalSales.setText("S/"+formato.format(summary.getTotalOrder()));

            showPieChart2(mPieChart, summary);
        }

        materialSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                DecimalFormat formato = new DecimalFormat("#.00");
                if (position == 0){
                    textCapital.setText("S/"+formato.format(summary.getCapital()));
                    textBenefit.setText("S/"+formato.format(summary.getBenefit()));
                    textAditionalSale.setText("S/"+formato.format(summary.getExtras()));
                    textTotalSales.setText("S/"+formato.format(summary.getTotalOrder()));

                    showPieChart2(mPieChart, summary);
                }
                if (position == 1){
/*                    Summary summaryUND = showSummaryUND();
                    textCapital.setText("S/"+formato.format(summaryUND.getCapital()));
                    textBenefit.setText("S/"+formato.format(summaryUND.getBenefit()));
                    textAditionalSale.setText("S/"+formato.format(summaryUND.getExtras()));
                    textTotalSales.setText("S/"+formato.format(summaryUND.getTotalOrder()));

                    showPieChart2(mPieChart, summaryUND);*/
                }
                if (position == 2){
/*                    Summary summaryPQT = showSummaryPQT();
                    textCapital.setText("S/"+formato.format(summaryPQT.getCapital()));
                    textBenefit.setText("S/"+formato.format(summaryPQT.getBenefit()));
                    textAditionalSale.setText("S/"+formato.format(summaryPQT.getExtras()));
                    textTotalSales.setText("S/"+formato.format(summaryPQT.getTotalOrder()));

                    showPieChart2(mPieChart, summaryPQT);*/
                }
            }
        });

        return view;
    }

    private void showPieChart2(PieChart pieChart, Summary summary){
        DecimalFormat format = new DecimalFormat("#.00");

        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        String label = "";

        pieChart.setUsePercentValues(true);
        pieChart.setExtraOffsets(5, 10, 5, 5);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setCenterText("Ventas del día");
        pieChart.setDrawHoleEnabled(true);
        pieChart.setTransparentCircleAlpha(150);
        pieChart.setHoleRadius(35f);
        pieChart.setTransparentCircleRadius(37f);
        pieChart.setDrawCenterText(true);
        pieChart.setRotationAngle(30);
        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(false);



        Map<String, Float> typeAmountMap = new HashMap<>();

        Float capital = Float.parseFloat(format.format(summary.getCapital()/ summary.getTotalOrder()));
        Float benefit = Float.parseFloat(format.format(summary.getBenefit()/ summary.getTotalOrder()));

        typeAmountMap.put("Capital", capital );
        typeAmountMap.put("Benefício", benefit);


        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#304567"));
        colors.add(Color.parseColor("#309967"));


        //input data and fit data into pie chart entry
        for(String type: typeAmountMap.keySet()){
            pieEntries.add(new PieEntry(typeAmountMap.get(type).floatValue(), type));
        }

        PieDataSet pieDataSet = new PieDataSet(pieEntries,label);
        pieDataSet.setValueTextSize(12f);
        pieDataSet.setColors(colors);
        PieData pieData = new PieData(pieDataSet);
        pieData.setDrawValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setData(pieData);

        pieDataSet.setValueFormatter(new MyValueFormatter());
        pieData.setValueFormatter(new DecimalRemover(new DecimalFormat("###,###,###")));
        pieChart.setData(pieData);

        pieChart.invalidate();
        pieChart.setUsePercentValues(true);


        Legend l = pieChart.getLegend();
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);


        l.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(true);
    }

/*    public Summary showSummaryUND(){

        Summary summary = new Summary(0F,0F,0F,0F);
        for (Consumption consumption: consumptionList){
            if (consumption.getTypeProductSale().equals("UND")){
                summary.setCapital(summary.getCapital()+consumption.getQuantity()*(consumption.getPackagePurchasePrice()/consumption.getQuantityPerPackage()));
                summary.setBenefit(summary.getBenefit()+(consumption.getSubtotal()-consumption.getQuantity()*(consumption.getPackagePurchasePrice()/consumption.getQuantityPerPackage())));
                summary.setExtras(summary.getExtras()+consumption.getQuantityBBHH()*consumption.getPriceExtraIIDD());
                summary.setTotalOrder(summary.getTotalOrder()+consumption.getTotal());
            }
        }
        return summary;
    }
    public Summary showSummaryPQT(){
        Summary summary = new Summary(0F,0F,0F,0F);
        for (Consumption consumption: consumptionList){
            if (consumption.getTypeProductSale().equals("PQT")){
                summary.setCapital(summary.getCapital()+consumption.getQuantity()*consumption.getPackagePurchasePrice());
                summary.setBenefit(summary.getBenefit()+(consumption.getSubtotal()-consumption.getQuantity()*consumption.getPackagePurchasePrice()));
                summary.setExtras(summary.getExtras()+consumption.getQuantityBBHH()*consumption.getPriceExtraIIDD());
                summary.setTotalOrder(summary.getTotalOrder()+consumption.getTotal());
            }
        }
        return summary;
    }*/

    public class DecimalRemover extends PercentFormatter {
        protected DecimalFormat mFormat;
        public DecimalRemover(DecimalFormat format) {
            this.mFormat = format;
        }
        @Override public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
         /*   if(value < 10)
                return "";*/
            return mFormat.format(value) + " %";
        }
    }

    public class MyValueFormatter implements IValueFormatter {
        private DecimalFormat mFormat;
        public MyValueFormatter() {
            mFormat = new DecimalFormat("###,###,###");
            // use no decimals
        }
        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            // write your logic here if(value < 10) return "";
            return mFormat.format(value) + "%";
            // in case you want to add percent
        }
    }
}