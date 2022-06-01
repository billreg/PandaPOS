package com.floki.controller;

import com.floki.entity.Employee;
import com.floki.service.EmployeeService;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EmployeeController {
    Employee employeereturn;

    private String  baseUrl = "https://flokipos.herokuapp.com/";

    private Response<Employee> response;

    public Employee saveEmployee(Employee employee) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        EmployeeService employeeService = retrofit.create(EmployeeService.class);
        Call<Employee> call = employeeService.save(employee);

        try {
            response = call.execute();
            if (response.isSuccessful()){
                return response.body();
            }else {
                return null;
            }
        }catch (Exception e){

        }
        return employeereturn;
    }

    public Employee getEmployeeByUsername(String username){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        EmployeeService employeeService = retrofit.create(EmployeeService.class);
        Call<Employee> call = employeeService.getByUsername(username);
        try {
            response = call.execute();
            if (response.isSuccessful()){
                return response.body();
            }else {
                return null;
            }
        }catch (Exception e){

        }
        return employeereturn;
    }


}
