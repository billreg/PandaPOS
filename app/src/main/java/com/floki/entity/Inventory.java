package com.floki.entity;

public class Inventory {
    private Integer inventoryId;
    private Integer fkProductId;
    private Integer fkBusinessId;
    private String nameProduc;
    private Float purchasePriceUnit;
    private Integer quantityUnits;
    private Integer quantityRemainning;
    private String date;
    private Boolean active;

    public Integer getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Integer inventoryId) {
        this.inventoryId = inventoryId;
    }

    public Integer getFkProductId() {
        return fkProductId;
    }

    public void setFkProductId(Integer fkProductId) {
        this.fkProductId = fkProductId;
    }

    public Integer getFkBusinessId() {
        return fkBusinessId;
    }

    public void setFkBusinessId(Integer fkBusinessId) {
        this.fkBusinessId = fkBusinessId;
    }

    public String getNameProduc() {
        return nameProduc;
    }

    public void setNameProduc(String nameProduc) {
        this.nameProduc = nameProduc;
    }

    public Float getPurchasePriceUnit() {
        return purchasePriceUnit;
    }

    public void setPurchasePriceUnit(Float purchasePriceUnit) {
        this.purchasePriceUnit = purchasePriceUnit;
    }

    public Integer getQuantityUnits() {
        return quantityUnits;
    }

    public void setQuantityUnits(Integer quantityUnits) {
        this.quantityUnits = quantityUnits;
    }

    public Integer getQuantityRemainning() {
        return quantityRemainning;
    }

    public void setQuantityRemainning(Integer quantityRemainning) {
        this.quantityRemainning = quantityRemainning;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
