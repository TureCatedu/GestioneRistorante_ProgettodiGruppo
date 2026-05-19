package com.example.progettofinale.errorResponse;

import java.time.LocalDateTime;

public record ErrorResponse(
    int status, 
    String message,
    LocalDateTime timestamp
) {}
