package com.garanti.FirstSpringWeb.utilities.results;

import lombok.Getter;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;

@Getter
public class DataResult <T> extends Result{
    private T data;

    public DataResult(String message, @NonNull ResponseEntity<?> responseEntity){
        super(message, responseEntity);
    }

    public DataResult(T data, String message, @NonNull ResponseEntity<?> responseEntity) {
        super(message, responseEntity);
        this.data = data;
    }

    public DataResult(T data, @NonNull ResponseEntity<?> responseEntity) {
        super(responseEntity);
        this.data = data;
    }

}
