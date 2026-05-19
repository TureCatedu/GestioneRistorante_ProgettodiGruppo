package com.example.progettofinale.errorResponse;

import java.time.LocalDateTime;
//record per response error
public record ErrorResponse(
    int status, 
    String message,
    LocalDateTime timestamp
) {}
