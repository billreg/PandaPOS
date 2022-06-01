package com.floki.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ListPreferences implements Serializable {

    @SerializedName("business")
    Business business;

    @SerializedName("person")
    Person person;

    @SerializedName("employee")
    Employee employee;

    @SerializedName("token")
    String token;

    @SerializedName("sale")
    Sale sale;

    @SerializedName("consumptionList")
    List<Consumption> consumptionList;

    @SerializedName("updated")
    Boolean updated;

    @SerializedName("caja")
    Caja caja;

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Employee getEmployee() {
        return employee;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    public List<Consumption> getConsumptionList() {
        return consumptionList;
    }

    public void setConsumptionList(List<Consumption> consumptionList) {
        this.consumptionList = consumptionList;
    }

    public Boolean getUpdated() {
        return updated;
    }

    public void setUpdated(Boolean updated) {
        this.updated = updated;
    }

    public Caja getCaja() {
        return caja;
    }

    public void setCaja(Caja caja) {
        this.caja = caja;
    }
}


