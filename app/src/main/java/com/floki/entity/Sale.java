package com.floki.entity;

public class Sale {
    private static Integer numberSale = 0;
    private Integer saleId;
    private Integer fkBusinessId;
    private Integer fkEmployeeId;
    private Integer fkClientId;
    private String saleDate;
    private String voucherType;
    private String voucherNumber;
    private String payType;
    private float total;
    private float igv;
    private String descripcion;
    private boolean estado;

    public static Integer getNumberSale() {
        return numberSale;
    }

    public static void setNumberSale(Integer numberSale) {
        Sale.numberSale = numberSale;
    }

    public Integer getSaleId() {
        return saleId;
    }

    public void setSaleId(Integer saleId) {
        this.saleId = saleId;
    }

    public Integer getFkBusinessId() {
        return fkBusinessId;
    }

    public void setFkBusinessId(Integer fkBusinessId) {
        this.fkBusinessId = fkBusinessId;
    }

    public Integer getFkEmployeeId() {
        return fkEmployeeId;
    }

    public void setFkEmployeeId(Integer fkEmployeeId) {
        this.fkEmployeeId = fkEmployeeId;
    }

    public Integer getFkClientId() {
        return fkClientId;
    }

    public void setFkClientId(Integer fkClientId) {
        this.fkClientId = fkClientId;
    }

    public String getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public void setVoucherType(String voucherType) {
        this.voucherType = voucherType;
    }

    public String getVoucherNumber() {
        return voucherNumber;
    }

    public void setVoucherNumber(String voucherNumber) {
        this.voucherNumber = voucherNumber;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public float getIgv() {
        return igv;
    }

    public void setIgv(float igv) {
        this.igv = igv;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}
