package com.floki.login;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteClosable;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.floki.controller.CategoryController;
import com.floki.controller.ProductController;
import com.floki.controller.SubcategoryController;
import com.floki.entity.Category;
import com.floki.entity.ListPreferences;
import com.floki.entity.Product;
import com.floki.entity.Subcategory;
import com.floki.sqlite.SqliteHelperJarvis;
import com.google.android.material.textfield.TextInputEditText;
import com.floki.MainActivity;
import com.floki.R;
import com.floki.controller.AuthenticationController;
import com.floki.controller.BusinessController;
import com.floki.controller.EmployeeController;
import com.floki.entity.AuthenticationRequest;
import com.floki.entity.AuthenticationResponse;
import com.floki.entity.Business;
import com.floki.entity.Employee;
import com.floki.settings.SettingPreferences;

import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class Login extends AppCompatActivity {

    private TextView register;

    private AuthenticationRequest authenticationRequest;
    private SettingPreferences settingPreferences;
    private ListPreferences listPreferences;
    SqliteHelperJarvis sqliteHelperJarvis;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextInputEditText username = findViewById(R.id.username);
        TextInputEditText password = findViewById(R.id.password);
        Button startSesion = findViewById(R.id.start_sesion);
        register = findViewById(R.id.register);


        sqliteHelperJarvis = SqliteHelperJarvis.getInstance(this);
        settingPreferences = new SettingPreferences(getBaseContext());
        listPreferences = new SettingPreferences(getBaseContext()).getListPreferences();

        authenticationRequest = new AuthenticationRequest();
        AuthenticationController authenticationController = new AuthenticationController();

        startSesion.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                try {
                    authenticationRequest.setUsername(username.getText().toString());
                    authenticationRequest.setPassword(password.getText().toString());

                    EmployeeController employeeController = new EmployeeController();
                    BusinessController businessController = new BusinessController();
                    CategoryController categoryController = new CategoryController();
                    SubcategoryController subcategoryController = new SubcategoryController();
                    ProductController productController = new ProductController();

                    AuthenticationResponse authenticationResponse = authenticationController.getToken(authenticationRequest);
                    if (!Objects.isNull(authenticationResponse)){
                        String token = authenticationResponse.getJwt();

                        if (Objects.isNull(listPreferences.getBusiness())){

                            Employee employee = employeeController.getEmployeeByUsername(authenticationRequest.getUsername());
                            Business business = businessController.getBusinessById(employee.getEmployeeId());

                            List<Category> listCategories = categoryController.getAllByBusinessId();
                            List<Subcategory> listSubcategories = subcategoryController.getAll();
                            List<Product> listProducts = productController.getByBusinessId(business.getBusinessId());

                            sqliteHelperJarvis.addCategories(listCategories);
                            sqliteHelperJarvis.addSubcategories(listSubcategories);
                            sqliteHelperJarvis.addProducts(listProducts);

                            listPreferences.setToken(token);
                            listPreferences.setBusiness(business);
                            listPreferences.setEmployee(employee);
                            settingPreferences.save(listPreferences);

                            Intent intent = new Intent(getBaseContext(), MainActivity.class);
                            startActivity(intent);
                            Toast.makeText(getBaseContext(), "Início de sesion exitoso", Toast.LENGTH_SHORT).show();
                        }else {

                            Employee employee = listPreferences.getEmployee();
                            Employee employee1 = employeeController.getEmployeeByUsername(authenticationRequest.getUsername());

                            if (employee.getFkBusinessId() == employee1.getFkBusinessId()){
                                listPreferences.setToken(token);
                                settingPreferences.save(listPreferences);
                                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                                startActivity(intent);
                                Toast.makeText(getBaseContext(), "Início de sesion exitoso", Toast.LENGTH_SHORT).show();
                            }else {
                                settingPreferences.delete();
                                sqliteHelperJarvis.deleteDataBase(getBaseContext());

                                Employee employeeNew = employeeController.getEmployeeByUsername(authenticationRequest.getUsername());
                                Business business = businessController.getBusinessById(employeeNew.getEmployeeId());

                                List<Category> listCategories = categoryController.getAllByBusinessId();
                                List<Subcategory> listSubcategories = subcategoryController.getAll();
                                List<Product> listProducts = productController.getByBusinessId(employeeNew.getFkBusinessId());

                                sqliteHelperJarvis.addCategories(listCategories);
                                sqliteHelperJarvis.addSubcategories(listSubcategories);
                                sqliteHelperJarvis.addProducts(listProducts);

                                listPreferences.setToken(token);
                                listPreferences.setBusiness(business);
                                listPreferences.setEmployee(employeeNew);
                                settingPreferences.save(listPreferences);

                                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                                startActivity(intent);
                                Toast.makeText(getBaseContext(), "Início de sesion exitoso", Toast.LENGTH_SHORT).show();

                            }
                        }
                    }else {
                        Toast.makeText(getBaseContext(), "Verifique su usuario y contraseña", Toast.LENGTH_SHORT).show();
                    }

                } catch (IOException e) {
                    e.printStackTrace();

                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), Register.class);
                startActivity(intent);
            }
        });

        }

    @Override
    public void onBackPressed() { moveTaskToBack(true); }


    private String generateHashedPass(String pass) {
        // hash a plaintext password using the typical log rounds (10)
        return BCrypt.hashpw(pass, BCrypt.gensalt());
    }

    private boolean isValid(String clearTextPassword, String hashedPass) {
        // returns true if password matches hash
        return BCrypt.checkpw(clearTextPassword, hashedPass);
    }

    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    }

