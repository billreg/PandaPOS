package com.floki.newsales;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.floki.R;
import com.floki.entity.Consumption;
import com.floki.sqlite.SqliteHelperJarvis;

import java.time.LocalDateTime;
import java.util.List;

public class NewSalesHistory extends AppCompatActivity {

    SqliteHelperJarvis sqliteHelperJarvis;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_sales_history);
        TableLayout tableLayout =findViewById(R.id.table_sales_history);
        sqliteHelperJarvis = SqliteHelperJarvis.getInstance(getBaseContext());

        List<Consumption> consumptionList = sqliteHelperJarvis.listConsumptios();
        showConsumptionHistory(tableLayout, consumptionList, getBaseContext());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void showConsumptionHistory(TableLayout table, List<Consumption> consumptionList, Context context){
        table.removeAllViews();
        int color;
        for(int i=1;i<=consumptionList.size();i++){
            if (i % 2 == 1){
                color = Color.WHITE;
            }else{
                color = Color.rgb(210, 224,239);
            }

            Consumption consumption = consumptionList.get(i-1);

            TableRow row=new TableRow(context);
            TextView textView;

            textView = new TextView(context);
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setPadding(15, 15, 0, 15);
            textView.setBackgroundResource(R.color.white);
            textView.setText(consumption.getFkSaleId().toString());
            textView.setTextSize(16);
            textView.setTextColor(Color.BLACK);
            textView.setBackgroundColor(color);
            row.addView(textView);

            textView = new TextView(context);
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setPadding(0, 15, 15, 15);
            textView.setBackgroundResource(R.color.white);
            String text = consumption.getNameProduct();
            textView.setText(text.substring(0, 1).toUpperCase() + text.substring(1).toLowerCase());
            textView.setTextSize(16);
            textView.setTextColor(Color.BLACK);
            textView.setBackgroundColor(color);
            row.addView(textView);

            textView = new TextView(context);
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setPadding(15, 15, 15, 15);
            textView.setBackgroundResource(R.color.white);
            textView.setText(Integer.toString(consumption.getQuantity())+" "+consumption.getTypeSale());
            textView.setTextSize(16);
            textView.setTextColor(Color.BLACK);
            textView.setGravity(Gravity.CENTER);
            textView.setBackgroundColor(color);
            row.addView(textView);

            textView = new TextView(context);
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setPadding(15, 15, 15, 15);
            textView.setBackgroundResource(R.color.white);
            textView.setText(Float.toString(consumption.getSalePrice()));
            textView.setTextSize(16);
            textView.setTextColor(Color.BLACK);
            textView.setGravity(Gravity.CENTER);
            textView.setBackgroundColor(color);
            row.addView(textView);

            textView = new TextView(context);
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setPadding(15, 15, 15, 15);
            textView.setBackgroundResource(R.color.white);
            textView.setText(Float.toString(consumption.getTotalConsumption()));
            textView.setTextSize(16);
            textView.setTextColor(Color.BLACK);
            textView.setGravity(Gravity.CENTER);
            textView.setBackgroundColor(color);
            row.addView(textView);

            textView = new TextView(context);
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setPadding(15, 15, 15, 15);
            textView.setBackgroundResource(R.color.white);
            LocalDateTime localDateTime = LocalDateTime.parse(consumption.getConsumptionDate());
            textView.setText(localDateTime.getHour()+":"+localDateTime.getMinute());
            textView.setTextSize(16);
            textView.setTextColor(Color.BLACK);
            textView.setGravity(Gravity.CENTER);
            textView.setBackgroundColor(color);
            row.addView(textView);
            row.setClickable(true);
            row.setId(i);
            row.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onClick(View v) {
                }
            });
            table.addView(row);
        }
    }
}