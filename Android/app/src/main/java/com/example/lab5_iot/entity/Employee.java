package com.example.lab5_iot.entity;

import java.io.Serializable;

public class Employee implements Serializable {
    private int employee_id;
    private String first_name;
    private String last_name;
    private String email;
    private String phone_number;
    private String hire_date;
    private String job_id;
    private float salary;
    private String employee_feedback;
    private int department_id;
    private Integer meeting;
    private String meeting_date;


    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(int department_id) {
        this.department_id = department_id;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getHire_date() {
        return hire_date;
    }

    public void setHire_date(String hire_date) {
        this.hire_date = hire_date;
    }

    public String getJob_id() {
        return job_id;
    }

    public void setJob_id(String job_id) {
        this.job_id = job_id;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public String getEmployee_feedback() {
        return employee_feedback;
    }

    public void setEmployee_feedback(String employee_feedback) {
        this.employee_feedback = employee_feedback;
    }

    public Integer getMeeting() {
        return meeting;
    }

    public void setMeeting(Integer meeting) {
        this.meeting = meeting;
    }

    public String getMeeting_date() {
        return meeting_date;
    }

    public void setMeeting_date(String meeting_date) {
        this.meeting_date = meeting_date;
    }
}
