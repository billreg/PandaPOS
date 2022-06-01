package com.floki.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.floki.R;
import com.floki.entity.Category;

import java.util.ArrayList;

public class AdaptadorHomeCategoriesSpinner extends BaseAdapter {

    private Context context;
    private ArrayList<Category> listEntidad;

    public AdaptadorHomeCategoriesSpinner(Context context, ArrayList<Category> categories) {
        this.context = context;
        this.listEntidad = categories;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Category category = (Category) getItem(position);

        convertView = LayoutInflater.from(context).inflate(R.layout.spinner_categories, null);

        TextView textView = convertView.findViewById(R.id.name_category);

        textView.setText(category.getName());

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
