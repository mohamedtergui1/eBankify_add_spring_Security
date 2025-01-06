package org.example.ebankify.dto;

public record ApiResponse <T>(
         String message
        ,
         T data
) {
}
