package com.floki.billing;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.floki.R;
import com.floki.entity.Consumption;
import com.floki.entity.Summary;
import com.floki.sqlite.SqliteHelperJarvis;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BillingSummaryDay extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    SqliteHelperJarvis sqliteHelperJarvis;

    TextView textCapital;
    TextView textBenefit;
    TextView textTotalSales;

    PieChart mPieChart;

    public BillingSummaryDay() {
    }

    public static BillingSummaryDay newInstance(String param1, String param2) {
        BillingSummaryDay fragment = new BillingSummaryDay();
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
        View view =inflater.inflate(R.layout.fragment_billing_summary_day, container, false);

        mPieChart = (PieChart) view.findViewById(R.id.pie_chart_billing);


        textCapital = view.findViewById(R.id.text_capital);
        textBenefit = view.findViewById(R.id.text_benefit);
        textTotalSales = view.findViewById(R.id.text_total_sales);

        sqliteHelperJarvis = SqliteHelperJarvis.getInstance(getContext());
        List<Consumption> consumptionList = sqliteHelperJarvis.listConsumptios();
        DecimalFormat formato = new DecimalFormat("#.00");
        Summary summary = new Summary(0F,0F,0F,0F);
        summary =  getBalance(consumptionList);

        textCapital.setText("S/"+formato.format(summary.getCapital()));
        textBenefit.setText("S/"+formato.format(summary.getBenefit()));
        textTotalSales.setText("S/"+formato.format(summary.getTotalOrder()));

        showPieChart2(mPieChart, summary);

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
        typeAmountMap.put("Beneficio", benefit);


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

        pieDataSet.setValueFormatter(new BillingSummaryDay.MyValueFormatter());
        pieData.setValueFormatter(new BillingSummaryDay.DecimalRemover(new DecimalFormat("###,###,###")));
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

    public Summary getBalance(List<Consumption> consumptionList){
        Summary summary = new Summary(0F,0F, 0F, 0F);
        for (Consumption consumption: consumptionList){
            summary.setCapital(summary.getCapital() + consumption.getPricePurchaseUnitXmayor()*consumption.getQuantity());
            summary.setTotalOrder(summary.getTotalOrder() + consumption.getTotalConsumption());
        }
        summary.setBenefit(summary.getTotalOrder() - summary.getCapital());
        return summary;
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

}