package com.floki.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.fragment.app.Fragment;

import com.floki.entity.ListPreferences;
import com.floki.inventory.InventoryAdd;
import com.google.android.material.chip.Chip;
import com.floki.R;
import com.floki.login.Register;
import com.floki.login.UserDetails;
import com.floki.entity.Business;
import com.floki.settings.SettingPreferences;

import java.util.ArrayList;

public class MainHome extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    Chip user;
    String options[] = { "Categorías", "Subcategorías"};

    private ArrayList<EntidadHome> arrayEntidad = new ArrayList<>();

    private SettingPreferences settingPreferences;
    private ListPreferences listPreferences;

    ViewFlipper viewFlipper;


    public MainHome() {
    }

    public static MainHome newInstance(String param1, String param2) {
        MainHome fragment = new MainHome();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_home, container, false);

        int images[] = {R.drawable.logopit1, R.drawable.logopit2, R.drawable.logopit3};

        user = (Chip) view.findViewById(R.id.user);
        TextView categories = view.findViewById(R.id.categories);
        TextView typeBusiness = view.findViewById(R.id.type_business);
        //TextView marcar =  view.findViewById(R.id.marcas);
        //TextView categoriesrt =  view.findViewById(R.id.categoriesrt);


        settingPreferences = new SettingPreferences(getContext());
        listPreferences = settingPreferences.getListPreferences();
        Business business = listPreferences.getBusiness();

        typeBusiness.setText(business.getBusinessType());

        EntidadHome entidadHome = new EntidadHome(options[0], ">");
        arrayEntidad.add(entidadHome);
        EntidadHome entidadHome2 = new EntidadHome(options[1], ">");
        arrayEntidad.add(entidadHome2);

        AdaptadorHome adaptadorHome = new AdaptadorHome(getContext(), arrayEntidad);

        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), UserDetails.class);
                startActivity(intent);
            }
        });

        categories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), HomeShowCategories.class);
                startActivity(intent);
            }
        });
/*        marcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Register.class);
                getContext().startActivity(intent);
            }
        });*/
/*        categoriesrt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), InventoryAdd.class);
                getContext().startActivity(intent);
            }
        });*/

        return view;
    }

    public void carrucel(int image){
        ImageView imageView = new ImageView(getContext());
        imageView.setBackgroundResource(image);

        viewFlipper.addView(imageView);
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        viewFlipper.setInAnimation(getContext(), android.R.anim.slide_out_right);
        viewFlipper.setOutAnimation(getContext(), android.R.anim.slide_out_right);
    }
}