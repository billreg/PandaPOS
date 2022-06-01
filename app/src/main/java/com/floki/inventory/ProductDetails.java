package com.floki.inventory;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.floki.R;
import com.floki.controller.ProductController;
import com.floki.entity.Category;
import com.floki.entity.Product;
import com.floki.entity.Subcategory;
import com.floki.home.AdaptadorHomeCategoriesSpinner;
import com.floki.home.AdaptadorHomeSubcategoriasSpinner;
import com.floki.sqlite.SqliteHelperJarvis;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;

public class ProductDetails extends AppCompatActivity {

    private ProductController productController;
    private SqliteHelperJarvis sqliteHelperJarvis;

    private EditText nameProductDetails;
    private EditText presentation;
    private EditText volumenPeso;

    private MaterialSpinner spinnerCategoryDetails;
    private MaterialSpinner spinnerSubcategoryDetails;

    private EditText quantityPerPackage;

    private EditText purchasePricePackage;
    private EditText salePricePackage;
    private EditText salePriceUnit;

    private TextView barcode;
    private EditText description;
    private Switch switchActive;

    private Button addBarcode;
    private Button updateProduct;



    private ArrayList<Category> categories = new ArrayList<>();
    private ArrayList<Subcategory> subcategories = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        productController = new ProductController();
        sqliteHelperJarvis = SqliteHelperJarvis.getInstance(getBaseContext());

        nameProductDetails = findViewById(R.id.name_product_details);
        presentation = findViewById(R.id.presentation);
        volumenPeso = findViewById(R.id.volumen_peso);


        spinnerCategoryDetails =findViewById(R.id.spinner_category_details);
        spinnerSubcategoryDetails = findViewById(R.id.spinner_subcategory_details);

        quantityPerPackage = findViewById(R.id.quantity_per_package);

        purchasePricePackage = findViewById(R.id.purchase_price_package);
        salePricePackage = findViewById(R.id.sale_price_package);
        salePriceUnit = findViewById(R.id.sale_price_unit);

        barcode = findViewById(R.id.barcode);
        description = findViewById(R.id.description);
        switchActive = findViewById(R.id.switch_active);

        addBarcode = findViewById(R.id.add_barcode);
        updateProduct = findViewById(R.id.update_product);


        Bundle bundle = getIntent().getExtras();
        Product product = (Product) bundle.getSerializable("product");

        List<Category> categoryList = sqliteHelperJarvis.listCategoriesByActive();
        List<Subcategory> subcategoryList = sqliteHelperJarvis.listSubcategoriesByCategoryIdAndActive(product.getFkCategoryId());

        String categoriesNew[] = new String[categoryList.size()];
        String subcategoriesNew[] = new String[subcategoryList.size()];
        for (int i = 0 ; i< categoryList.size(); i++){
            categoriesNew[i] = categoryList.get(i).getName();
        }

        for (int i = 0 ; i< subcategoryList.size(); i++){
            subcategoriesNew[i] = subcategoryList.get(i).getName();
        }

        ArrayAdapter<String> adapterCategories = new ArrayAdapter<>(getBaseContext(), R.layout.spinner_item,categoriesNew);
        ArrayAdapter<String> adapterSubcategories = new ArrayAdapter<>(getBaseContext(), R.layout.spinner_item,subcategoriesNew);

        spinnerCategoryDetails.setAdapter(adapterCategories);
        spinnerSubcategoryDetails.setAdapter(adapterSubcategories);

        spinnerCategoryDetails.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                List<Subcategory> subcategoryList = sqliteHelperJarvis.listSubcategoriesByCategoryIdAndActive(position+1);
                String subcategoriesNew[] = new String[subcategoryList.size()];
                for (int i = 0 ; i< subcategoryList.size(); i++){
                    subcategoriesNew[i] = subcategoryList.get(i).getName();
                }
                ArrayAdapter<String> adapterSubcategories = new ArrayAdapter<>(getBaseContext(), R.layout.spinner_item,subcategoriesNew);
                spinnerSubcategoryDetails.setAdapter(adapterSubcategories);
            }
        });


        addBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), InventoryAddBarcode.class);
                mStartForResult.launch(intent);
            }
        });

        updateProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Product productUpdated = getProductDetails(product);
                sqliteHelperJarvis.updateProduct(productUpdated);
                productController.updateProductByBusinessId(productUpdated);
                Toast.makeText(getApplicationContext(),"Actualizado correctamente", Toast.LENGTH_SHORT).show();
            }
        });

        setProductDetails(product, subcategoriesNew);
    }

    public void setProductDetails(Product product,  String[] subcategories){

        nameProductDetails.setText(product.getNameProduct().substring(0,1).toUpperCase()+product.getNameProduct().substring(1).toLowerCase());
        presentation.setText(product.getPresentation());
        volumenPeso.setText(product.getVolumeWeight());

        spinnerCategoryDetails.setSelectedIndex(product.getFkCategoryId()-1);
        spinnerSubcategoryDetails.setSelectedIndex(returnPosition(subcategories, product.getNameProduct()));

        quantityPerPackage.setText(product.getQuantityPerPackage().toString());
        Double purchasePrice = redondearDecimales(product.getUnitPurchasePriceXmayor()*product.getQuantityPerPackage(),2);
        purchasePricePackage.setText(String.valueOf(purchasePrice));
        Double salePrice = redondearDecimales(product.getUnitSalePriceXmayor()*product.getQuantityPerPackage(),2);
        salePricePackage.setText(String.valueOf(salePrice));
        salePriceUnit.setText(String.valueOf(product.getUnitSalePriceXminor()));

        barcode.setText(product.getBarcode());
        description.setText(product.getDescription());
        switchActive.setChecked(product.getActive().booleanValue());

    }

    public Product getProductDetails(Product product){

        product.setNameProduct(nameProductDetails.getText().toString());

        product.setPresentation(presentation.getText().toString());
        product.setVolumeWeight(volumenPeso.getText().toString());


        product.setFkCategoryId(spinnerCategoryDetails.getSelectedIndex()+1);
        String name = spinnerSubcategoryDetails.getText().toString();
        Subcategory subcategory = sqliteHelperJarvis.getSubcategoryByName(name);
        product.setFkSubcategoryId(subcategory.getSubcategoryId());

        product.setQuantityPerPackage(Integer.parseInt(quantityPerPackage.getText().toString()));
        product.setUnitPurchasePriceXmayor(Float.parseFloat(purchasePricePackage.getText().toString())/product.getQuantityPerPackage());
        product.setUnitSalePriceXmayor(Float.parseFloat(salePricePackage.getText().toString())/product.getQuantityPerPackage());
        product.setUnitSalePriceXminor(Float.parseFloat(salePriceUnit.getText().toString()));

        product.setBarcode(barcode.getText().toString());
        product.setDescription(description.getText().toString());
        product.setActive(switchActive.isChecked());

        return product;
    }

    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        String barc = intent.getStringExtra("barcode");
                        barcode.setText(barc);
                    }
                }
            });

    public static double redondearDecimales(double valorInicial, int numeroDecimales) {
        double parteEntera, resultado;
        resultado = valorInicial;
        parteEntera = Math.floor(resultado);
        resultado=(resultado-parteEntera)*Math.pow(10, numeroDecimales);
        resultado=Math.round(resultado);
        resultado=(resultado/Math.pow(10, numeroDecimales))+parteEntera;
        return resultado;
    }

    public Integer returnPosition(String[] array, String name){
        int position = 0;
        for ( int i = 0; i< array.length; i++){
            if (array[i] == name){
                position = i;
            }
        }
        return position;
    }

}