package com.floki.home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.floki.R;
import com.floki.entity.Category;
import com.floki.entity.Subcategory;
import com.floki.sqlite.SqliteHelperJarvis;

import java.util.ArrayList;
import java.util.List;

public class HomeShowSubcategories extends AppCompatActivity {

    private ArrayList<Subcategory> subcategories = new ArrayList<>();

    private ArrayList<Category> entidadHomeCategoriesArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_show_subcategories);
        MaterialSpinner materialSpinner = findViewById(R.id.spinner);
        ListView listView = findViewById(R.id.list_view_show_subcategories);
   

        SqliteHelperJarvis sqliteHelperJarvis = SqliteHelperJarvis.getInstance(getBaseContext());
        List<Category> categoryList = sqliteHelperJarvis.listCategoriesByActive();

        for (Category category: categoryList){
            entidadHomeCategoriesArrayList.add(category);
        }

        //Category arrayList[] = new Category[categoryList.size()];
 /*       ArrayAdapter<Category> spinnerArrayAdapter = new ArrayAdapter<Category>(
          this, R.layout.spinner_subcategories, arrayList );*/

        AdaptadorHomeCategoriesSpinner ada = new AdaptadorHomeCategoriesSpinner(getBaseContext(),entidadHomeCategoriesArrayList);
        materialSpinner.setAdapter(ada);
        if (categoryList.size() > 0){
            materialSpinner.setSelectedIndex(0);
        }

        /*materialSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                //Category category =  materialSpinner.getSele();

                //List<Subcategory> subcategoryList = sqliteHelperJarvis.listSubcategoriesByCategoryId(category.getCategoryId());

                //subcategories.clear();
                //for (Subcategory subcategory: subcategoryList){
                //    subcategories.add(subcategory);
                //}
                //AdaptadorHomeSubcategorias adaptadorHomeSubcategorias= new AdaptadorHomeSubcategorias(getApplicationContext(), subcategories);
                listView.setAdapter(adaptadorHomeSubcategorias);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/

    }
}