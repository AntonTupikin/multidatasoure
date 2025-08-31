package com.example.multidatasoure.controller.request;

import java.util.List;

/**
 * Частичное обновление проекта.
 * Поддерживается назначение списка работников (employees).
 * Если список null — считаем, что нужно очистить назначения.
 */
public record ProjectPatchRequest(List<Long> employeeIds) {}
