package com.VolunTrack.demo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generic API Response wrapper for consistent data and message delivery.
 * This class encapsulates the response data and a descriptive message,
 * facilitating standardized communication from API endpoints.
 *
 * @param <T> The type of the data being returned in the API response.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private String message;
    private T data;

    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(message, data);
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>("Operación exitosa", data);
    }

    public static ApiResponse<Void> error(String message) {
        return new ApiResponse<>(message, null);
    }

    public static <T> ApiResponse<T> error(String message, T errorData) {
        return new ApiResponse<>(message, errorData);
    }

    public static ApiResponse<Void> noContent(String message) {
        return new ApiResponse<>(message, null);
    }
}