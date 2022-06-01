package com.floki.service;

import com.floki.entity.Person;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HTTP;

public interface PersonService {
    @HTTP(method = "POST", path = "/floki/persons/save", hasBody = true)
    Call<Person> save (@Body Person person);

}
