package com.swati.project.uber.uberApp.advices;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApiResponse<T> {   //this will be returned if all things go well

    private LocalDateTime timeStamp;
    private T data;
    private ApiError error;

    public ApiResponse(){
        this.timeStamp = LocalDateTime.now();
    }

    public ApiResponse(T data){
        this();
        this.data = data;
    }

    public ApiResponse(ApiError error) {
        this();
        this.error = error;
    }
}
