package com.floki.inventory;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.floki.R;
import com.floki.entity.Business;
import com.floki.entity.Inventory;
import com.floki.entity.ListPreferences;
import com.floki.entity.Product;
import com.floki.settings.SettingPreferences;
import com.floki.sqlite.SqliteHelperJarvis;
import com.google.android.material.chip.Chip;

public class InventoryAdd extends AppCompatActivity {

    SqliteHelperJarvis sqliteHelperJarvis;
    SettingPreferences settingPreferences;
    ListPreferences listPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_add);
        //Chip scanerAddHistory =findViewById(R.id.scaner_add_history);

        sqliteHelperJarvis = SqliteHelperJarvis.getInstance(getBaseContext());
        settingPreferences = new SettingPreferences(getBaseContext());
        listPreferences = settingPreferences.getListPreferences();


       /* scanerAddHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Business business = listPreferences.getBusiness();
                Product product = sqliteHelperJarvis.getProductByProductId(200);

                Inventory inventory = generateInventory(business, product);
                sqliteHelperJarvis.addInventory(inventory);


                Intent intent = new Intent(getBaseContext(), InventoryAddSearchProduct.class);
                startActivity(intent);
            }
        });*/
    }

    public Inventory generateInventory( Business business, Product product){
        Inventory inventory = new Inventory();

        //inventory.setInventoryId();
        inventory.setFkProductId(product.getProductId());
        inventory.setFkBusinessId(business.getBusinessId());
        inventory.setNameProduc(product.getNameProduct());
        inventory.setPurchasePriceUnit(1.50F);
        inventory.setQuantityUnits(10);
        inventory.setQuantityRemainning(10);
        inventory.setActive(true);
        return inventory;
    }
}