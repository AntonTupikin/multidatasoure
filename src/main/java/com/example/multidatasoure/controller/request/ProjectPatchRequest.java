package com.example.multidatasoure.controller.request;

/**
 * Частичное обновление проекта.
 * Сейчас поддерживается назначение/снятие работника (employee).
 */
public record ProjectPatchRequest(Long employeeId) {}

