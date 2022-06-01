package com.floki.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Switch;
import android.widget.TextView;

import com.floki.R;
import com.floki.entity.Category;
import com.floki.sqlite.SqliteHelperJarvis;

import java.util.ArrayList;

public class AdaptadorHomeCategories extends BaseAdapter {

    private Context context;
    private ArrayList<Category> listEntidad;

    public AdaptadorHomeCategories(Context context, ArrayList<Category> categories) {
        this.context = context;
        this.listEntidad = categories;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Category category = (Category) getItem(position);

        convertView = LayoutInflater.from(context).inflate(R.layout.listview_home_categories, null);
        TextView tvTitulo = (TextView) convertView.findViewById(R.id.name_category);
        Switch aSwitch =  convertView.findViewById(R.id.switch_active);


        tvTitulo.setText(category.getName());
        aSwitch.setChecked(category.getActive());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SqliteHelperJarvis sqliteHelperJarvis = SqliteHelperJarvis.getInstance(context);
                category.setActive(aSwitch.isChecked());
                sqliteHelperJarvis.updateCategory(category);
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
