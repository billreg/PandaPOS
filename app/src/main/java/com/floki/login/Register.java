package com.floki.login;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.floki.MainActivity;
import com.floki.R;
import com.floki.controller.CategoryController;
import com.floki.controller.ProductController;
import com.floki.controller.SubcategoryController;
import com.floki.entity.Category;
import com.floki.entity.ListPreferences;
import com.floki.entity.Product;
import com.floki.entity.Subcategory;
import com.floki.sqlite.SqliteHelperJarvis;
import com.google.android.material.textfield.TextInputEditText;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.floki.controller.AuthenticationController;
import com.floki.controller.BusinessController;
import com.floki.controller.EmployeeController;
import com.floki.controller.PersonController;
import com.floki.entity.AuthenticationRequest;
import com.floki.entity.AuthenticationResponse;
import com.floki.entity.Business;
import com.floki.entity.Employee;
import com.floki.entity.Person;
import com.floki.settings.SettingPreferences;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {

    TextInputEditText email;
    TextInputEditText password;
    TextInputEditText confirmPasword;
    TextInputEditText nameBusioness;
    Button buttonRegister;
    MaterialSpinner materialSpinner;
    TextView login;

    private SettingPreferences settingPreferences;
    private ListPreferences listPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmPasword = findViewById(R.id.confirm_pasword);
        /*nameBusioness = findViewById(R.id.name_busioness);*/

        materialSpinner = findViewById(R.id.material_spinner_business_type);
        buttonRegister = findViewById(R.id.button_register);
        login = findViewById(R.id.login);

        SqliteHelperJarvis sqliteHelperJarvis = SqliteHelperJarvis.getInstance(this);

        String arrayTypeSale[] = {"Minimarket","Bodega","Heladería","Licorería", "Paquetería"};
        ArrayAdapter<String> adapterTypeSale = new ArrayAdapter<String>(getBaseContext(), R.layout.spinner_item, arrayTypeSale );
        materialSpinner.setAdapter(adapterTypeSale);

        settingPreferences = new SettingPreferences(getBaseContext());
        listPreferences = new SettingPreferences(getBaseContext()).getListPreferences();


        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {

                if (validarEmail(email.getText().toString())){
                    if (evaluateRegister()){
                        if (evaluatePassword()){
                            BusinessController businessController = new BusinessController();
                            PersonController personController = new PersonController();
                            EmployeeController employeeController = new EmployeeController();

                            Employee employeeVlid = employeeController.getEmployeeByUsername(email.getText().toString());

                            if (Objects.isNull(employeeVlid)){

                                Business business = createBusiness();
                                business.setBusinessName("Business");
                                business.setBusinessType(materialSpinner.getText().toString());
                                Business businessResponce = businessController.saveBusiness(business);

                                Person person =  createPerson(businessResponce);
                                Person personResponce = personController.savePerson(person);

                                Employee employee = createEmployee(personResponce);
                                employee.setUsername(email.getText().toString());
                                employee.setPassword(confirmPasword.getText().toString());
                                Employee employeeresponce = employeeController.saveEmployee(employee);

                                String token;
                                try {
                                    AuthenticationRequest authenticationRequest = new AuthenticationRequest();
                                    authenticationRequest.setUsername(employee.getUsername());
                                    authenticationRequest.setPassword(employee.getPassword());
                                    AuthenticationController authenticationController = new AuthenticationController();
                                    token = authenticationController.getToken(authenticationRequest).getJwt();
                                } catch (IOException e) {
                                    token = "";
                                    e.printStackTrace();
                                }



                                CategoryController categoryController = new CategoryController();
                                List<Category> listCategories = categoryController.getAllByBusinessId();

                                SubcategoryController subcategoryController = new SubcategoryController();
                                List<Subcategory> listSubcategories = subcategoryController.getAll();

                                ProductController productController = new ProductController();
                                List<Product> listProducts = productController.getByBusinessId(businessResponce.getBusinessId());

                                sqliteHelperJarvis.deleteDataBase(getBaseContext());
                                settingPreferences.delete();

                                listPreferences.setBusiness(businessResponce);
                                listPreferences.setPerson(personResponce);
                                listPreferences.setEmployee(employeeresponce);
                                listPreferences.setToken(token);
                                settingPreferences.save(listPreferences);


                                sqliteHelperJarvis.addCategories(listCategories);
                                sqliteHelperJarvis.addSubcategories(listSubcategories);
                                sqliteHelperJarvis.addProducts(listProducts);

                                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                                startActivity(intent);
                            }else {
                                Toast.makeText(Register.this, "El nombre de usuario ya existe", Toast.LENGTH_SHORT).show();
                            }


                        }else {
                            Toast.makeText(Register.this, "verificar su contraseña", Toast.LENGTH_SHORT).show();
                        }

                    }else {
                        Toast.makeText(Register.this, "Completar todos los campos", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(Register.this, "Correo invalido", Toast.LENGTH_SHORT).show();
                }

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), Login.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onBackPressed() { moveTaskToBack(true); }

    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    public boolean evaluateRegister(){
        if (!email.getText().toString().isEmpty() &&
                !password.getText().toString().isEmpty() &&
                !confirmPasword.getText().toString().isEmpty()){
            return true;
        }
        return false;
    }

    public boolean evaluatePassword(){
        if (password.getText().toString().equals(confirmPasword.getText().toString())){
            return true;
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Business createBusiness(){
        Business business = new Business();
        business.setBusinessName("Primer negocio");
        business.setBusinessType("Minimarket");
        business.setCreationDate(LocalDateTime.now().toString());
        business.setPhone("123 456 789");
        business.setToken("");
        business.setDirection("");
        business.setNumberEmployees(1);
        return business;
    }

    public Person createPerson(Business business){
        Person person = new Person();

        person.setFkBusinessId(business.getBusinessId());
        person.setNames("");
        person.setLastname("");
        person.setSurname("");
        person.setDocumentType("");
        person.setDocumentNumber("");
        person.setDireccion("");
        person.setTelephone("");
        person.setEmail("");
        person.setActive(true);

        return person;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Employee createEmployee(Person person){
        Employee employee = new Employee();
        employee.setFkPersonId(person.getPersonId());
        employee.setFkBusinessId(person.getFkBusinessId());
        employee.setCreationDate(LocalDateTime.now().toString());
        employee.setUserType("ADMINISTRADOR");
        employee.setUsername("");
        employee.setPassword("");
        employee.setActive(true);
        return employee;
    }


}