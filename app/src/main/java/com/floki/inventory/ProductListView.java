package com.floki.inventory;

import java.io.Serializable;


public class ProductListView implements Serializable {

    private String nameProduct;
    private String quantityPerPackage;
    private Boolean active;

    public ProductListView(String nameProduct,String quantityPerPackage, Boolean active) {
        this.nameProduct = nameProduct;
        this.quantityPerPackage = quantityPerPackage;
        this.active = active;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public String getQuantityPerPackage() {
        return quantityPerPackage;
    }

    public void setQuantityPerPackage(String quantityPerPackage) {
        this.quantityPerPackage = quantityPerPackage;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
