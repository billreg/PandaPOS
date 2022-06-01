package com.floki.service;

import com.floki.entity.Employee;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HTTP;
import retrofit2.http.Path;

public interface EmployeeService {
    @HTTP(method = "POST", path = "/floki/employees/save", hasBody = true)
    Call<Employee> save (@Body Employee employee);

    @HTTP(method = "GET", path = "/floki/employees/username/{username}")
    Call<Employee> getByUsername(@Path("username") String username);

}
