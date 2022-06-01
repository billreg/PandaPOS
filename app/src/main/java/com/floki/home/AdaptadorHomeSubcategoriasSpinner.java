package com.floki.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.floki.R;
import com.floki.entity.Subcategory;

import java.util.ArrayList;

public class AdaptadorHomeSubcategoriasSpinner extends BaseAdapter {

    private Context context;
    private ArrayList<Subcategory> listEntidad;

    public AdaptadorHomeSubcategoriasSpinner(Context context, ArrayList<Subcategory> listEntidad) {
        this.context = context;
        this.listEntidad = listEntidad;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Subcategory subcategory = (Subcategory) getItem(position);

        convertView = LayoutInflater.from(context).inflate(R.layout.spinner_categories, null);
        TextView textView = convertView.findViewById(R.id.name_category);

        textView.setText(subcategory.getName());

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
