package com.example.lab5_iot.service;

import com.example.lab5_iot.entity.Employee;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TutorService {

    @GET("/trabajadores/{managerId}")
    Call<List<Employee>> getEmployeesByManager(@Path("managerId") int managerId);

    @GET("/buscar/{employeeId}")
    Call<Employee> getEmployeeById(@Path("employeeId") int employeeId);

    @POST("/asignartutorias/{managerId}/{employeeId}")
    Call<HashMap<String, String>> postAssignment(@Path("managerId") int managerId,
                                              @Path("employeeId") int employeeId);
}
