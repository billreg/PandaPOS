package com.floki;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MenuItem;

import com.floki.entity.Business;
import com.floki.entity.ListPreferences;
import com.floki.newsales.MainNewSales;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.floki.billing.MainBilling;
import com.floki.controller.AuthenticationController;
import com.floki.controller.CategoryController;
import com.floki.controller.ProductController;
import com.floki.controller.SubcategoryController;
import com.floki.entity.AuthenticationResponse;
import com.floki.entity.Category;
import com.floki.entity.Product;
import com.floki.entity.Subcategory;
import com.floki.home.MainHome;
import com.floki.inventory.MainInventory;
import com.floki.login.Login;
import com.floki.settings.SettingPreferences;
import com.floki.sqlite.SqliteHelperJarvis;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ListPreferences listPreferences;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationViewMain = findViewById(R.id.main_navigation);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        listPreferences = new SettingPreferences(getBaseContext()).getListPreferences();

        String jwt = listPreferences.getToken();

        if (Objects.isNull(jwt)) {
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
        } else {
            bottomNavigationViewMain = findViewById(R.id.main_navigation);
            bottomNavigationViewMain.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    if (item.getItemId() == R.id.navigation_home) {
                        showSelectedFragment(new MainHome());
                    }
                    if (item.getItemId() == R.id.navigation_inventory) {
                        showSelectedFragment(new MainInventory());
                    }
                    if (item.getItemId() == R.id.navigation_sales) {
                        showSelectedFragment(new MainNewSales());
                    }
                    if (item.getItemId() == R.id.navigation_billing) {
                        showSelectedFragment(new MainBilling());
                    }
                    return false;
                }
            });
            if (savedInstanceState == null) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.show_fragment, new MainBilling()).commit();
            }
        }
    }

    private void showSelectedFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.show_fragment, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();

    }

    @Override
    public void onBackPressed() { moveTaskToBack(true); }

}