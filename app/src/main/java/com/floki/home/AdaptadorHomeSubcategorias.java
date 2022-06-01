package com.floki.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Switch;
import android.widget.TextView;

import com.floki.R;
import com.floki.entity.Subcategory;
import com.floki.sqlite.SqliteHelperJarvis;

import java.util.ArrayList;

public class AdaptadorHomeSubcategorias extends BaseAdapter {

    private Context context;
    private ArrayList<Subcategory> listEntidad;

    public AdaptadorHomeSubcategorias(Context context, ArrayList<Subcategory> listEntidad) {
        this.context = context;
        this.listEntidad = listEntidad;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Subcategory subcategory = (Subcategory) getItem(position);

        convertView = LayoutInflater.from(context).inflate(R.layout.listview_home_categories, null);
        TextView tvTitulo = (TextView) convertView.findViewById(R.id.name_category);
        Switch aSwitch =  convertView.findViewById(R.id.switch_active);


        tvTitulo.setText(subcategory.getName());
        aSwitch.setChecked(subcategory.getActive());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SqliteHelperJarvis sqliteHelperJarvis = SqliteHelperJarvis.getInstance(context);
                subcategory.setActive(aSwitch.isChecked());
                sqliteHelperJarvis.updateSubcategory(subcategory);
            }
        });

        return convertView;
    }

    @Override
    public int getCount() {
        return listEntidad.size();
    }

    @Override
    public Object getItem(int position) {
        return listEntidad.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


}
