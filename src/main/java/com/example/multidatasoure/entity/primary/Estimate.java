package com.example.multidatasoure.entity.primary;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.hibernate.proxy.HibernateProxy;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Смета проекта. У каждого проекта может быть только одна смета.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "estimates")
@FieldNameConstants
public class Estimate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Проект, к которому относится смета. Ограничение UNIQUE на уровне БД обеспечивает 1:1.
     */
    @OneToOne
    @JoinColumn(name = "project_id", nullable = false, unique = true)
    @ToString.Exclude
    private Project project;

    /**
     * Краткое название/заголовок сметы.
     */
    @Column
    private String title;

    /**
     * Валюта сметы (например, RUB, USD).
     */
    @Column(nullable = false, length = 3)
    @Builder.Default
    private String currency = "RUB";

    /**
     * Произвольные заметки по смете.
     */
    @Column(columnDefinition = "TEXT")
    private String notes;

    /**
     * Позиции сметы (материалы/работы).
     */
    @OneToMany(mappedBy = "estimate", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    private List<EstimateItem> items = new ArrayList<>();

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Estimate that = (Estimate) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}

