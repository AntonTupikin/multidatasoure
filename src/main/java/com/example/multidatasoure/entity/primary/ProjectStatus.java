package com.example.multidatasoure.entity.primary;

public enum ProjectStatus {
    DRAFT,                  // Инициация
    FEASIBILITY,            // ТЭО/инвест-решение
    LAND_ACQUISITION,       // Участок/аренда/сервитуты/ГПЗУ
    CONCEPT_DESIGN,         // Концепция/эскиз
    DETAILED_DESIGN,        // Рабочая документация
    PERMITTING,             // Разрешения/техусловия/согласования
    FINANCIAL_CLOSE,        // Закрытие финансирования (если критично)
    PROCUREMENT,            // Закупки major equipment/подряд
    PRECONSTRUCTION,        // Мобилизация/подготовка площадки
    CONSTRUCTION,           // СМР
    SUBSTANTIAL_COMPLETION, // Существенная готовность
    COMMISSIONING,          // ПНР
    REGULATORY_ACCEPTANCE,  // Госкомиссия/РВЭ (разрешение на ввод)
    HANDOVER,               // Передача эксплуатационнику/заказчику
    WARRANTY,               // Гарантия
    CLOSED,                 // Закрыт
    // Исключительные:
    ON_HOLD,
    SUSPENDED,
    CANCELLED
}
