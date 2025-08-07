package com.example.taskmanage.service;

public interface ImportExportService {

    byte[] exportObject(Object[] data);
    String mapStatus(String status);
    String mapPriority(String priority);
}
