package com.floki.home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.floki.R;
import com.floki.entity.Category;
import com.floki.sqlite.SqliteHelperJarvis;

import java.util.ArrayList;
import java.util.List;

public class HomeShowCategories extends AppCompatActivity {



    ListView listViewShowCategories;
    String categorieslist[] = new String[9];

    private AdaptadorHomeCategories adaptadorHomeCategories;
    private ArrayList<Category> entidadHomeCategoriesArrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_show_categories);

        SqliteHelperJarvis sqliteHelperJarvis = SqliteHelperJarvis.getInstance(getBaseContext());

        listViewShowCategories = findViewById(R.id.list_view_show_categories);

        List<Category> categories = sqliteHelperJarvis.listCategoriesByCategoryId(1,9);

        for (Category category: categories){
            entidadHomeCategoriesArrayList.add(category);
        }


        adaptadorHomeCategories = new AdaptadorHomeCategories(getApplicationContext(), entidadHomeCategoriesArrayList);

        listViewShowCategories.setAdapter(adaptadorHomeCategories);

        listViewShowCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

}