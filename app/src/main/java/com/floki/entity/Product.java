package com.floki.entity;

import java.io.Serializable;

public class Product implements Serializable {
    private Integer productId;
    private Integer fkBusinessId;
    private Integer fkCategoryId;
    private Integer fkSubcategoryId;
    private Integer fkBrandId ;
    private Integer fkSupplierId;

    // ---------------------------------------
    private String barcode;
    private String nameProduct;

    private String presentation;
    private String volumeWeight;
    private Boolean isDrink;

    private Integer quantityPerPackage;

    // ---------------------------------------
    private Float unitPurchasePriceXmayor;
    private Float unitSalePriceXmayor;
    private Float unitSalePriceXminor;

    private String image;
    private String description;
    private Boolean active;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getFkBusinessId() {
        return fkBusinessId;
    }

    public void setFkBusinessId(Integer fkBusinessId) {
        this.fkBusinessId = fkBusinessId;
    }

    public Integer getFkCategoryId() {
        return fkCategoryId;
    }

    public void setFkCategoryId(Integer fkCategoryId) {
        this.fkCategoryId = fkCategoryId;
    }

    public Integer getFkSubcategoryId() {
        return fkSubcategoryId;
    }

    public void setFkSubcategoryId(Integer fkSubcategoryId) {
        this.fkSubcategoryId = fkSubcategoryId;
    }

    public Integer getFkBrandId() {
        return fkBrandId;
    }

    public void setFkBrandId(Integer fkBrandId) {
        this.fkBrandId = fkBrandId;
    }

    public Integer getFkSupplierId() {
        return fkSupplierId;
    }

    public void setFkSupplierId(Integer fkSupplierId) {
        this.fkSupplierId = fkSupplierId;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public String getPresentation() {
        return presentation;
    }

    public void setPresentation(String presentation) {
        this.presentation = presentation;
    }

    public String getVolumeWeight() {
        return volumeWeight;
    }

    public void setVolumeWeight(String volumeWeight) {
        this.volumeWeight = volumeWeight;
    }

    public Boolean getDrink() {
        return isDrink;
    }

    public void setDrink(Boolean drink) {
        isDrink = drink;
    }

    public Integer getQuantityPerPackage() {
        return quantityPerPackage;
    }

    public void setQuantityPerPackage(Integer quantityPerPackage) {
        this.quantityPerPackage = quantityPerPackage;
    }

    public Float getUnitPurchasePriceXmayor() {
        return unitPurchasePriceXmayor;
    }

    public void setUnitPurchasePriceXmayor(Float unitPurchasePriceXmayor) {
        this.unitPurchasePriceXmayor = unitPurchasePriceXmayor;
    }

    public Float getUnitSalePriceXmayor() {
        return unitSalePriceXmayor;
    }

    public void setUnitSalePriceXmayor(Float unitSalePriceXmayor) {
        this.unitSalePriceXmayor = unitSalePriceXmayor;
    }

    public Float getUnitSalePriceXminor() {
        return unitSalePriceXminor;
    }

    public void setUnitSalePriceXminor(Float unitSalePriceXminor) {
        this.unitSalePriceXminor = unitSalePriceXminor;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
