package com.floki.controller;

import com.floki.entity.Person;
import com.floki.service.PersonService;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PersonController {
    Person personreturn;

    private String  baseUrl = "https://flokipos.herokuapp.com/";

    private Response<Person> response;

    public Person savePerson(Person person) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PersonService personService = retrofit.create(PersonService.class);
        Call<Person> call = personService.save(person);

        try {
            response = call.execute();
            if (response.isSuccessful()){
                return response.body();
            }else {
                return null;
            }
        }catch (Exception e){

        }


      /*  call.enqueue(new Callback<Person>() {
            @Override
            public void onResponse(Call<Person> call, Response<Person> response) {
                if (response.isSuccessful()){
                    personreturn = response.body();
                }
            }

            @Override
            public void onFailure(Call<Person> call, Throwable t) {
                personreturn = null;
            }
        });*/
        return personreturn;
    }


}
