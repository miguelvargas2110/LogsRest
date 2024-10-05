package co.edu.uniquindio.microservicios.logsrestservice.Entities;

import lombok.Data;

@Data
public class ApiSuccess {
    private int statusCode;
    private String status;
    private String message;
    private String path;
    private Object data; // Logs o cualquier otra respuesta
    private int totalPages;
    private long totalElements;

    // Constructor simple para respuestas sin paginación
    public ApiSuccess(int statusCode, String status, String message, String path) {
        this.statusCode = statusCode;
        this.status = status;
        this.message = message;
        this.path = path;
    }

    // Constructor para respuestas con paginación
    public ApiSuccess(int statusCode, String status, String message, String path, Object data, int totalPages, long totalElements) {
        this.statusCode = statusCode;
        this.status = status;
        this.message = message;
        this.path = path;
        this.data = data;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }

    // Getters y setters

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }
}

