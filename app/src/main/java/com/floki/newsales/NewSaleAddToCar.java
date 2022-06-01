package com.floki.newsales;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.floki.R;
import com.floki.entity.ListPreferences;
import com.google.android.material.chip.Chip;
import com.floki.entity.Consumption;
import com.floki.entity.Sale;
import com.floki.settings.SettingPreferences;
import com.floki.sqlite.SqliteHelperJarvis;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NewSaleAddToCar extends AppCompatActivity {

    private Activity activity;

    private TableLayout tabla;
    private Button buttoNnewsale;
    private TextView idSale;
    private TextView txTotalSale;
    private Chip chipAddProduct;
    private Chip chipFrequentProduct;
    private Chip chipSearchProduct;
    private Chip chipScannerProduct;
    private Button completeSaleVoucher;
    private Button cancelSaleVoucher;

    private Sale sale;
    private List<Consumption> listConsumption = new ArrayList<>();
    private Float totalSaleConsumption;

    private SqliteHelperJarvis sqliteHelperJarvis;
    private SettingPreferences settingPreferences;
    private ListPreferences listPreferences;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_sale_add_to_car);

        activity = this;
        settingPreferences = new SettingPreferences(getBaseContext());
        listPreferences = new SettingPreferences(getBaseContext()).getListPreferences();

        tabla =  findViewById(R.id.table_generate_voucher);
        buttoNnewsale =findViewById(R.id.button_new_sale);
        idSale = findViewById(R.id.id_sale);
        sqliteHelperJarvis = SqliteHelperJarvis.getInstance(getBaseContext());
        txTotalSale =findViewById(R.id.text_total_sale_generate_voucher);

        chipAddProduct =findViewById(R.id.chip_add_product);
        chipFrequentProduct =findViewById(R.id.chip_frequent_product);
        chipSearchProduct =findViewById(R.id.chip_search_product);
        chipScannerProduct =findViewById(R.id.chip_scanner_product);

        completeSaleVoucher =findViewById(R.id.button_complet_sale_voucher);
        cancelSaleVoucher =findViewById(R.id.button_cacel_sale_voucher);


        buttoNnewsale.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                sale = createSale();
                idSale.setText("ID: "+ sale.getSaleId().toString());
                buttoNnewsale.setEnabled(false);
            }
        });

        chipAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Objects.isNull(sale)){
                    Intent intent = new Intent(getBaseContext(), SalesCarAddProduct.class);
                    mStartForResult.launch(intent);
                }else {
                    Toast.makeText(getBaseContext(), "Crear nueva venta", Toast.LENGTH_SHORT).show();
                }
            }
        });
        chipFrequentProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Objects.isNull(sale)){
                    Intent intent = new Intent(getBaseContext(), SalesCarAddFrequentProduct.class);
                    mStartForResult.launch(intent);
                }else {
                    Toast.makeText(getBaseContext(), "Crear nueva venta", Toast.LENGTH_SHORT).show();
                }
            }
        });
        chipSearchProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Objects.isNull(sale)){
                    Intent intent = new Intent(getBaseContext(), SalesCarSearchProduct.class);
                    mStartForResult.launch(intent);
                }else {
                    Toast.makeText(getBaseContext(), "Crear nueva venta", Toast.LENGTH_SHORT).show();
                }
            }
        });
        chipScannerProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (!Objects.isNull(sale)){
                    Intent intent = new Intent(getBaseContext(), SalesCarSacanner.class);
                    mStartForResult.launch(intent);
                }else {
                    Toast.makeText(getBaseContext(), "Crear nueva venta", Toast.LENGTH_SHORT).show();
                }
            }
        });

        completeSaleVoucher.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                if (!Objects.isNull(sale)){
                    if (listConsumption.size() > 0){

                        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

                        builder.setTitle("")
                                .setMessage("Completar venta ?")
                                .setPositiveButton("COMPLETAR",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                sqliteHelperJarvis.addSale(sale);
                                                String fecha = null;
                                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                                    fecha = LocalDateTime.now().toString();
                                                }
                                                for (int i= 0; i<listConsumption.size();i++ ){
                                                    listConsumption.get(i).setConsumptionDate(fecha);
                                                }
                                                sqliteHelperJarvis.addConsumptions(listConsumption);
                                                sale = null;
                                                listConsumption = new ArrayList<>();
                                                listPreferences.setSale(sale);
                                                listPreferences.setConsumptionList(listConsumption);
                                                settingPreferences.save(listPreferences);
                                                idSale.setText("");
                                                txTotalSale.setText("");
                                                tabla.removeAllViews();
                                                buttoNnewsale.setEnabled(true);
                                                Toast.makeText(getBaseContext(), "Venta completada", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                .setNegativeButton("CANCELAR",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                //listener.onNegativeButtonClick();
                                            }
                                        });
                        builder.create().show();



                    }else {
                        Toast.makeText(getBaseContext(), "No hay ningun producto", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getBaseContext(), "No exite ninguna venta", Toast.LENGTH_SHORT).show();
                }
            }
        });
        cancelSaleVoucher.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                sale = null;
                listConsumption= new ArrayList<>();
                listPreferences.setSale(sale);
                listPreferences.setConsumptionList(listConsumption);
                settingPreferences.save(listPreferences);
                idSale.setText("");
                tabla.removeAllViews();
                txTotalSale.setText("");
                buttoNnewsale.setEnabled(true);
            }
        });

        sale = listPreferences.getSale();
        listConsumption = listPreferences.getConsumptionList();
        if (!Objects.isNull(sale) && !Objects.isNull(listConsumption)){
            idSale.setText("ID: "+ sale.getSaleId().toString());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                showConsumptionBySale(listConsumption, tabla);
            }
            buttoNnewsale.setEnabled(false);
            if (listConsumption.size()>0){
                totalSaleConsumption = 0F;
                for (Consumption consumo: listConsumption){
                    totalSaleConsumption = totalSaleConsumption+consumo.getTotalConsumption();
                }
                txTotalSale.setText("Total: S/"+totalSaleConsumption);
            }
        }else {
            listConsumption = new ArrayList<>();
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public Sale createSale(){

        String seq = sqliteHelperJarvis.getSquenceSales();
        int id;
        if (seq.isEmpty()){
            id = 1;
        }else {
            id = Integer.parseInt(seq)+1;
        }
        Sale sale = new Sale();
        sale.setSaleId(id);
        sale.setFkBusinessId(1);
        sale.setFkClientId(1);
        sale.setFkEmployeeId(1);

        sale.setSaleDate(LocalDateTime.now().toString());
        sale.setVoucherType("Nota venta");
        sale.setVoucherNumber("001");
        sale.setPayType("En efectivo");

        sale.setTotal(0.00F);
        sale.setIgv(0.00F);
        sale.setDescripcion("");
        sale.setEstado(false);
        return sale;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void showConsumptionBySale(List<Consumption> listConsumption, TableLayout tabla){
        tabla.removeAllViews();
        int color;
        for(int i=0;i<listConsumption.size();i++){
            if (i % 2 == 0){
                color = Color.WHITE;
            }else{
                color = Color.rgb(210, 224,239);
            }
            TableRow row=new TableRow(getBaseContext());
            TextView textView;

            textView = new TextView(getBaseContext());
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setPadding(15, 15, 15, 15);
            textView.setBackgroundResource(R.color.white);
            String text = listConsumption.get(i).getNameProduct();
            textView.setText(text.substring(0, 1).toUpperCase() + text.substring(1).toLowerCase());
            textView.setTextSize(17);
            textView.setTextColor(Color.BLACK);
            textView.setBackgroundColor(color);
            row.addView(textView);

            textView = new TextView(getBaseContext());
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setPadding(15, 15, 15, 15);
            textView.setBackgroundResource(R.color.white);
            textView.setText(Integer.toString(listConsumption.get(i).getQuantity())+" "+listConsumption.get(i).getTypeSale());
            textView.setTextSize(17);
            textView.setTextColor(Color.BLACK);
            textView.setGravity(Gravity.CENTER);
            textView.setBackgroundColor(color);
            row.addView(textView);

            textView = new TextView(getBaseContext());
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setPadding(15, 15, 15, 15);
            textView.setBackgroundResource(R.color.white);
            textView.setText(Float.toString(listConsumption.get(i).getSalePrice()));
            textView.setTextSize(17);
            textView.setTextColor(Color.BLACK);
            textView.setGravity(Gravity.CENTER);
            textView.setBackgroundColor(color);
            row.addView(textView);

            textView = new TextView(getBaseContext());
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setPadding(15, 15, 15, 15);
            textView.setBackgroundResource(R.color.white);
            textView.setText(Float.toString(listConsumption.get(i).getTotalConsumption()));
            textView.setTextSize(17);
            textView.setTextColor(Color.BLACK);
            textView.setGravity(Gravity.CENTER);
            textView.setBackgroundColor(color);
            row.addView(textView);


            row.setClickable(true);
            row.setId(i);
            row.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.Q)
                @Override
                public void onClick(View v) {

                }
            });
            tabla.addView(row);
        }
    }


    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        Bundle bundle = intent.getExtras();
                        Consumption consumption =(Consumption) bundle.getSerializable("consumption");
                        if (consumption != null ){
                            consumption.setFkSaleId(sale.getSaleId());
                            listConsumption.add(consumption);
                            listPreferences.setSale(sale);
                            listPreferences.setConsumptionList(listConsumption);
                            settingPreferences.save(listPreferences);
                            showConsumptionBySale( listConsumption, tabla);
                            if (listConsumption.size()>0){
                                totalSaleConsumption = 0F;
                                for (Consumption consumo: listConsumption){
                                    totalSaleConsumption = totalSaleConsumption+consumo.getTotalConsumption();
                                }
                                txTotalSale.setText("Total: S/"+totalSaleConsumption);
                            }
                        }
                    }
                }
            });

}
