package com.garanti.FirstSpringWeb.utilities.results;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Result {
    private String message;
    @NonNull
    private ResponseEntity<?> responseEntity;
}
