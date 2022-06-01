package com.floki.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Switch;
import android.widget.TextView;

import com.floki.R;
import com.floki.entity.Product;
import com.floki.inventory.ProductDetails;
import com.floki.sqlite.SqliteHelperJarvis;

import java.io.Serializable;
import java.util.ArrayList;


public class ProductAdapter extends BaseAdapter {
    private ArrayList<Product> products;
    private Context context;
    private LayoutInflater inflater;

    SqliteHelperJarvis sqliteHelperJarvis;

    public ProductAdapter(Context context, ArrayList<Product> products) {
        this.context = context;
        this.products = products;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Product product = (Product) getItem(position);

        convertView = LayoutInflater.from(context).inflate(R.layout.listview_inventory_product, null);

        TextView nameProduct =  convertView.findViewById(R.id.name_product);
        TextView presentation =  convertView.findViewById(R.id.name_presetation);
        TextView quantityPerPackage =  convertView.findViewById(R.id.quantity_per_package);
        TextView volumen =  convertView.findViewById(R.id.volumen);


        TextView salePrice =  convertView.findViewById(R.id.sale_price);
        TextView salePriceUnd =  convertView.findViewById(R.id.sale_price_unt);
        TextView purchasePrice =  convertView.findViewById(R.id.purchase_price);

        Switch switchActive = (Switch) convertView.findViewById(R.id.switch_active);

        sqliteHelperJarvis = SqliteHelperJarvis.getInstance(context);


        nameProduct.setText(product.getNameProduct().substring(0,1).toUpperCase()+product.getNameProduct().substring(1).toLowerCase());
        presentation.setText(product.getPresentation());
        quantityPerPackage.setText("1x"+product.getQuantityPerPackage()+ " UND");
        volumen.setText(product.getVolumeWeight());


        salePriceUnd.setText("S/ "+product.getUnitSalePriceXminor().toString());
        Double num1 =redondearDecimales( product.getUnitSalePriceXmayor()*product.getQuantityPerPackage(), 2);
        salePrice.setText("S/ "+num1);
        Double num2 =redondearDecimales( product.getUnitPurchasePriceXmayor()*product.getQuantityPerPackage(), 2);
        purchasePrice.setText("S/"+num2);

        switchActive.setChecked(product.getActive());

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

        switchActive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                product.setActive(switchActive.isChecked());
                sqliteHelperJarvis.updateProduct(product);
                //Toast.makeText(context,"Actualizado correctamente", Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }

    public static double redondearDecimales(double valorInicial, int numeroDecimales) {
        double parteEntera, resultado;
        resultado = valorInicial;
        parteEntera = Math.floor(resultado);
        resultado=(resultado-parteEntera)*Math.pow(10, numeroDecimales);
        resultado=Math.round(resultado);
        resultado=(resultado/Math.pow(10, numeroDecimales))+parteEntera;
        return resultado;
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



    static class ViewHolder{
        TextView nameProduct;
        TextView quantityPerPackage;
        TextView purchasePrice;
        TextView salePrice ;

        Switch switchActive ;
    }
}
