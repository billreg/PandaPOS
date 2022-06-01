package com.floki.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Switch;
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


public class ProductSalesAdapter extends BaseAdapter {
    private ArrayList<Product> products;
    private Context context;

    private SqliteHelperJarvis sqliteHelperJarvis;
    private SettingPreferences settingPreferences;
    private ListPreferences listPreferences;


    int quantityMenos;
    int quantityPlus;

    public ProductSalesAdapter(Context context, ArrayList<Product> products) {
        this.context = context;
        this.products = products;
        sqliteHelperJarvis = SqliteHelperJarvis.getInstance(context);
        settingPreferences = new SettingPreferences(context);
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

        convertView = LayoutInflater.from(context).inflate(R.layout.listview_sales_product, null);

        Sale sale = createSale();
        Business business = listPreferences.getBusiness();
        Consumption consumption = createConsumption(business, sale, product);

        TextView  saleNameProduct = convertView.findViewById(R.id.list_sale_name_product);
        TextView  salePresentation = convertView.findViewById(R.id.list_sale_presentation);
        TextView  saleVolumenWegth = convertView.findViewById(R.id.list_sale_volumen_wegth);


        Chip  massales = convertView.findViewById(R.id.massales);
        TextView  salesquantity = convertView.findViewById(R.id.new_sales_quantity);
        TextView  typeSale = convertView.findViewById(R.id.type_sale);
        Chip  menossales = convertView.findViewById(R.id.menossales);

        TextView  salePriceUnit = convertView.findViewById(R.id.new_sale_price_unit);
        TextView  total = convertView.findViewById(R.id.total);
        Button saleProduct = convertView.findViewById(R.id.sale_product);

        saleNameProduct.setText(product.getNameProduct());
        salePresentation.setText(product.getPresentation());
        saleVolumenWegth.setText(product.getVolumeWeight());
        salePriceUnit.setText("S/"+product.getUnitSalePriceXminor().toString());
        typeSale.setText(consumption.getTypeSale());


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

        saleProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = Integer.parseInt(salesquantity.getText().toString());
                if (quantity>0){
                    Consumption consumptionNew = updateConsumtion(consumption,quantity);
                    sqliteHelperJarvis.addSale(sale);
                    sqliteHelperJarvis.addConsumption(consumptionNew);
                    Toast.makeText(context, "Venta completada", Toast.LENGTH_SHORT).show();
                    salesquantity.setText(String.valueOf(0));
                    total.setText("");
                }else {
                    Toast.makeText(context, "La cantidad debe ser mayor de cero", Toast.LENGTH_SHORT).show();
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
        consumption.setConsumptionDate(LocalDateTime.now().toString());
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












/*    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        final Product product = (Product) getItem(position);
        ViewHolder holder;
        if (convertView == null){

            //Inflamos la vista con nuestro propio layout
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_inventory_product, null);
            holder = new ViewHolder();

            holder.nameProduct = (TextView) convertView.findViewById(R.id.name_product);
            holder.quantityPerPackage = (TextView) convertView.findViewById(R.id.quantity_per_package);
            holder.purchasePrice = (TextView) convertView.findViewById(R.id.purchase_price);
            holder.salePrice = (TextView) convertView.findViewById(R.id.sale_price);

            holder.switchActive = (Switch) convertView.findViewById(R.id.switch_active);

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.nameProduct.setText(product.getNameProduct());
        holder.quantityPerPackage.setText(product.getQuantityPerPackage().toString()+ " UND/PQT");
        holder.purchasePrice.setText(product.getPackagePurchasePrice().toString());
        holder.salePrice.setText(product.getPackageSalePrice().toString());
        holder.switchActive.setChecked(product.getActive());

        //Devolvemos la vista inflada
        return convertView;
    }*/

 /*   @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Product product = (Product) getItem(position);

        TextView nameProduct = null;
        TextView quantityPerPackage = null;
        TextView purchasePrice = null;
        TextView salePrice = null;

        Switch switchActive = null;



        View view;
        ViewHolder holder;
        if (convertView==null){

            view = LayoutInflater.from(context).inflate(R.layout.listview_inventory_product, null);

            nameProduct = (TextView) view.findViewById(R.id.name_product);
            quantityPerPackage = (TextView) view.findViewById(R.id.quantity_per_package);
            purchasePrice = (TextView) view.findViewById(R.id.purchase_price);
            salePrice = (TextView) view.findViewById(R.id.sale_price);

            switchActive = (Switch) view.findViewById(R.id.switch_active);

            nameProduct.setText(product.getNameProduct());
            quantityPerPackage.setText(product.getQuantityPerPackage()+ " UND/PQT");
            purchasePrice.setText(product.getPackagePurchasePrice().toString());
            salePrice.setText(product.getPackageSalePrice().toString());
            switchActive.setChecked(product.getActive());


        }else{
            view = convertView;

        }

        return view;
    }
*/

/*    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Product product = (Product) getItem(position);

        View view;
        ViewHolder holder;
        if (convertView==null){

            view = LayoutInflater.from(context).inflate(R.layout.listview_inventory_product, null);

            holder = new ViewHolder();

            holder.nameProduct = (TextView) view.findViewById(R.id.name_product);
            holder.quantityPerPackage = (TextView) view.findViewById(R.id.quantity_per_package);
            holder.purchasePrice = (TextView) view.findViewById(R.id.purchase_price);
            holder.salePrice = (TextView) view.findViewById(R.id.sale_price);
            holder.switchActive = (Switch) view.findViewById(R.id.switch_active);

            view.setTag(holder);

        }else{
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }

        holder.nameProduct.setText(product.getNameProduct());
        holder.quantityPerPackage.setText(product.getQuantityPerPackage()+ " UND/PQT");
        holder.purchasePrice.setText(product.getPackagePurchasePrice().toString());
        holder.salePrice.setText(product.getPackageSalePrice().toString());
        holder.switchActive.setChecked(product.getActive());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetails.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("product", (Serializable) product);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        return view;
    }*/

/*    static class ViewHolder{
        TextView nameProduct;
        TextView quantityPerPackage;
        TextView purchasePrice;
        TextView salePrice ;

        Switch switchActive ;
    }*/
