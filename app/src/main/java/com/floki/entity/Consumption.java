package com.floki.entity;

import java.io.Serializable;

public class Consumption implements Serializable {


    private Integer consumptionId;
    private Integer fkBusinessId;
    private Integer fkSaleId;
    private Integer fkCategoryId;
    private Integer fkSubcategoryId;
    private Integer fkProductId;

    private String nameProduct; //--
    private Integer quantityPerPackage;

    private Float pricePurchaseUnitXmayor;
    private Float priceSaleUnitXmayor;
    private Float priceSaleUnitXminor;

    private String typeSale;
    private Float salePrice;
    private Integer quantity;
    private Float totalSale;

    private boolean icedDrink;
    private Integer quantityIIDD;
    private Float priceIIDD;
    private Float totalIIDD;

    private Float totalConsumption;
    private String consumptionDate;
    private Boolean active;


    public Integer getConsumptionId() {
        return consumptionId;
    }

    public void setConsumptionId(Integer consumptionId) {
        this.consumptionId = consumptionId;
    }

    public Integer getFkBusinessId() {
        return fkBusinessId;
    }

    public void setFkBusinessId(Integer fkBusinessId) {
        this.fkBusinessId = fkBusinessId;
    }

    public Integer getFkSaleId() {
        return fkSaleId;
    }

    public void setFkSaleId(Integer fkSaleId) {
        this.fkSaleId = fkSaleId;
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

    public Integer getFkProductId() {
        return fkProductId;
    }

    public void setFkProductId(Integer fkProductId) {
        this.fkProductId = fkProductId;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public Integer getQuantityPerPackage() {
        return quantityPerPackage;
    }

    public void setQuantityPerPackage(Integer quantityPerPackage) {
        this.quantityPerPackage = quantityPerPackage;
    }

    public Float getPricePurchaseUnitXmayor() {
        return pricePurchaseUnitXmayor;
    }

    public void setPricePurchaseUnitXmayor(Float pricePurchaseUnitXmayor) {
        this.pricePurchaseUnitXmayor = pricePurchaseUnitXmayor;
    }

    public Float getPriceSaleUnitXmayor() {
        return priceSaleUnitXmayor;
    }

    public void setPriceSaleUnitXmayor(Float priceSaleUnitXmayor) {
        this.priceSaleUnitXmayor = priceSaleUnitXmayor;
    }

    public Float getPriceSaleUnitXminor() {
        return priceSaleUnitXminor;
    }

    public void setPriceSaleUnitXminor(Float priceSaleUnitXminor) {
        this.priceSaleUnitXminor = priceSaleUnitXminor;
    }

    public String getTypeSale() {
        return typeSale;
    }

    public void setTypeSale(String typeSale) {
        this.typeSale = typeSale;
    }

    public Float getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Float salePrice) {
        this.salePrice = salePrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Float getTotalSale() {
        return totalSale;
    }

    public void setTotalSale(Float totalSale) {
        this.totalSale = totalSale;
    }

    public boolean isIcedDrink() {
        return icedDrink;
    }

    public void setIcedDrink(boolean icedDrink) {
        this.icedDrink = icedDrink;
    }

    public Integer getQuantityIIDD() {
        return quantityIIDD;
    }

    public void setQuantityIIDD(Integer quantityIIDD) {
        this.quantityIIDD = quantityIIDD;
    }

    public Float getPriceIIDD() {
        return priceIIDD;
    }

    public void setPriceIIDD(Float priceIIDD) {
        this.priceIIDD = priceIIDD;
    }

    public Float getTotalIIDD() {
        return totalIIDD;
    }

    public void setTotalIIDD(Float totalIIDD) {
        this.totalIIDD = totalIIDD;
    }

    public Float getTotalConsumption() {
        return totalConsumption;
    }

    public void setTotalConsumption(Float totalConsumption) {
        this.totalConsumption = totalConsumption;
    }

    public String getConsumptionDate() {
        return consumptionDate;
    }

    public void setConsumptionDate(String consumptionDate) {
        this.consumptionDate = consumptionDate;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
