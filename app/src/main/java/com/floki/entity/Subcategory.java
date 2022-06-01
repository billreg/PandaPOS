package com.floki.entity;

public class Subcategory {
    private Integer subcategoryId;
    private Integer fkCategoryId;
    private String name;
    private Boolean active;

    public Integer getSubcategoryId() {
        return subcategoryId;
    }

    public void setSubcategoryId(Integer subcategoryId) {
        this.subcategoryId = subcategoryId;
    }

    public Integer getFkCategoryId() {
        return fkCategoryId;
    }

    public void setFkCategoryId(Integer fkIdCategory) {
        this.fkCategoryId = fkIdCategory;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
