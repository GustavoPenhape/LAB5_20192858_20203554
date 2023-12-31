package com.example.lab5_iot.service;

import com.example.lab5_iot.entity.Employee;
import com.example.lab5_iot.entity.EmployeeDto;

import java.util.HashMap;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TutorService {

    @GET("/trabajadores/{managerId}")
    Call<List<Employee>> getEmployeesByManager(@Path("managerId") int managerId);

    @GET("/buscar/{employeeId}")
    Call<EmployeeDto> getEmployeeById(@Path("employeeId") int employeeId);

    @POST("/asignartutorias/{managerId}/{employeeId}")
    Call<HashMap<String, String>> postAssignment(@Path("managerId") int managerId,
                                              @Path("employeeId") int employeeId);

    @FormUrlEncoded
    @POST("/trabajador/tutoria/")
    Call<HashMap<String, String>> postFeedback(@Field("employeeId") int employeeId,
                                               @Field("feedback") String feedback);
}
