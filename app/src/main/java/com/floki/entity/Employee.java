package com.floki.entity;

public class Employee {
    private Integer employeeId;
    private Integer fkBusinessId;
    private Integer fkPersonId;
    private String userType;
    private String creationDate;
    private String username;
    private String password;
    private Boolean active;
    //private Person person;

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getFkBusinessId() {
        return fkBusinessId;
    }

    public void setFkBusinessId(Integer fkBusinessId) {
        this.fkBusinessId = fkBusinessId;
    }

    public Integer getFkPersonId() {
        return fkPersonId;
    }

    public void setFkPersonId(Integer fkPersonId) {
        this.fkPersonId = fkPersonId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

}
