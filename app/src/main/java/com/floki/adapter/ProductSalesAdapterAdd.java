package com.floki.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.floki.R;
import com.floki.entity.Business;
import com.floki.entity.Consumption;
import com.floki.entity.ListPreferences;
import com.floki.entity.Product;
import com.floki.entity.Sale;
import com.floki.settings.SettingPreferences;
import com.floki.sqlite.SqliteHelperJarvis;
import com.google.android.material.chip.Chip;

import java.time.LocalDateTime;
import java.util.ArrayList;


public class ProductSalesAdapterAdd extends BaseAdapter {
    private ArrayList<Product> products;
    private Activity contex;

    private SqliteHelperJarvis sqliteHelperJarvis;
    private SettingPreferences settingPreferences;
    private ListPreferences listPreferences;

    int quantityMenos;
    int quantityPlus;


    public ProductSalesAdapterAdd(Activity activity, ArrayList<Product> products) {
        this.contex = activity;
        this.products = products;
        sqliteHelperJarvis = SqliteHelperJarvis.getInstance(activity);
        settingPreferences = new SettingPreferences(activity);
        listPreferences = settingPreferences.getListPreferences();
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Product product = (Product) getItem(position);
        sqliteHelperJarvis = SqliteHelperJarvis.getInstance(contex);
        Sale sale = createSale();

        Business business = listPreferences.getBusiness();
        Consumption consumption = createConsumption(business, sale, product);

        convertView = LayoutInflater.from(contex).inflate(R.layout.listview_sales_product_addtocar, null);

        TextView listSaleNameproduct = convertView.findViewById(R.id.list_sale_name_product);
        TextView listSalePresentation = convertView.findViewById(R.id.list_sale_presentation);
        TextView volumen = convertView.findViewById(R.id.list_sale_volumen_wegth);
        TextView  total = convertView.findViewById(R.id.total);

        TextView newSalePriceUnit =  convertView.findViewById(R.id.new_sale_price_unit);

        Chip menossales = convertView.findViewById(R.id.menossales);
        TextView  salesquantity = convertView.findViewById(R.id.new_sales_quantity);
        Chip  massales = convertView.findViewById(R.id.massales);
        Button addtocar = convertView.findViewById(R.id.sale_product);



        listSaleNameproduct.setText(product.getNameProduct());
        listSalePresentation.setText(product.getPresentation());
        volumen.setText(product.getVolumeWeight());
        newSalePriceUnit.setText("S/"+product.getUnitSalePriceXminor().toString());

        menossales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantityMenos = Integer.parseInt(salesquantity.getText().toString());
                if (quantityMenos>0){
                    quantityMenos = quantityMenos - 1;
                    salesquantity.setText(String.valueOf(quantityMenos));
                }
                Consumption consumptionNew = updateConsumtion(consumption,quantityMenos);
                total.setText("S/"+consumptionNew.getTotalSale().toString());
            }
        });

        massales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantityPlus = Integer.parseInt(salesquantity.getText().toString());
                quantityPlus = quantityPlus + 1;
                salesquantity.setText(String.valueOf(quantityPlus));
                Consumption consumptionNew = updateConsumtion(consumption,quantityPlus);
                total.setText("S/"+consumptionNew.getTotalSale().toString());
            }
        });

        addtocar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = Integer.parseInt(salesquantity.getText().toString());
                if (quantity>0){
                    Consumption consumptionNew = updateConsumtion(consumption,quantity);
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("consumption", consumptionNew);
                    intent.putExtras(bundle);
                    contex.setResult(contex.RESULT_OK, intent);
                    contex.finish();
                }else {
                    Toast.makeText(contex, "La cantidad debe ser mayor que cero", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return convertView;
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
    public Consumption createConsumption(Business business, Sale sale, Product product){
        Consumption consumption = new Consumption();

        consumption.setFkBusinessId(business.getBusinessId());
        consumption.setFkSaleId(sale.getSaleId());
        consumption.setFkCategoryId(product.getFkCategoryId());
        consumption.setFkSubcategoryId(product.getFkSubcategoryId());
        consumption.setFkProductId(product.getProductId());

        consumption.setNameProduct(product.getNameProduct()+" "+product.getVolumeWeight());
        consumption.setQuantityPerPackage(product.getQuantityPerPackage());
        consumption.setPricePurchaseUnitXmayor(product.getUnitPurchasePriceXmayor());
        consumption.setPriceSaleUnitXmayor(product.getUnitSalePriceXmayor());
        consumption.setPriceSaleUnitXminor(product.getUnitSalePriceXminor());

        consumption.setTypeSale("UND");
        consumption.setSalePrice(product.getUnitSalePriceXminor());
        consumption.setQuantity(1);
        consumption.setTotalSale(consumption.getSalePrice()*consumption.getQuantity());

        consumption.setIcedDrink(false);
        consumption.setQuantityIIDD(0);
        consumption.setPriceIIDD(0.00F);
        consumption.setTotalIIDD(consumption.getPriceIIDD()*consumption.getQuantityIIDD());

        consumption.setTotalConsumption(consumption.getTotalSale() * consumption.getTotalIIDD());
        consumption.setConsumptionDate("");
        consumption.setActive(false);
        return consumption;
    }

    public Consumption updateConsumtion(Consumption consumption, int quantity){
        consumption.setQuantity(quantity);
        consumption.setTotalSale(consumption.getSalePrice()*consumption.getQuantity());
        consumption.setTotalConsumption(consumption.getTotalSale()+consumption.getTotalIIDD());
        return consumption;
    }

}
