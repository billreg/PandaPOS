package com.floki.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.floki.R;

import java.util.ArrayList;

public class AdaptadorHome extends BaseAdapter {

    private Context context;
    private ArrayList<EntidadHome> listEntidad;

    public AdaptadorHome(Context context, ArrayList<EntidadHome> listEntidad) {
        this.context = context;
        this.listEntidad = listEntidad;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final EntidadHome entidad = (EntidadHome) getItem(position);

        convertView = LayoutInflater.from(context).inflate(R.layout.listview_home_custom, null);
        TextView tvTitulo = (TextView) convertView.findViewById(R.id.name);
        TextView textView =  convertView.findViewById(R.id.symbol);


        tvTitulo.setText(entidad.getTitulo());
        textView.setText(entidad.getTitulo2());

/*
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HomeShowCategories.class);
                context.startActivity(intent);

            }
        });
*/

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
