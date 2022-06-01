package com.floki.home;

import java.io.Serializable;


public class EntidadHome implements Serializable {

    private String name;
    private String simbol;

    public EntidadHome(String name, String simbol) {
        this.name = name;
        this.simbol = simbol;
    }


    public String getTitulo() {
        return name;
    }

    public String getTitulo2() {
        return simbol;
    }
}
