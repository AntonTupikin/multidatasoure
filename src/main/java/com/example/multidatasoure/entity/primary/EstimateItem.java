package com.example.multidatasoure.entity.primary;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Позиция сметы: материал/работа с количеством и ценой.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "estimate_items")
@FieldNameConstants
public class EstimateItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "estimate_id", nullable = false)
    @ToString.Exclude
    private Estimate estimate;

    /**
     * Наименование материала/работы.
     */
    @Column(nullable = false)
    private String materialName;

    /**
     * Единица измерения (шт, м2, кг и т.п.).
     */
    @Column(length = 50)
    private String unit;

    /**
     * Количество. Поддерживает дробные значения.
     */
    @Column(nullable = false, precision = 19, scale = 3)
    @Builder.Default
    private BigDecimal quantity = BigDecimal.ZERO;

    /**
     * Цена за единицу.
     */
    @Column(nullable = false, precision = 19, scale = 2)
    @Builder.Default
    private BigDecimal unitPrice = BigDecimal.ZERO;

    /**
     * Категория/раздел сметы (опционально).
     */
    @Column(length = 100)
    private String category;

    /**
     * Порядок отображения позиции внутри сметы (опционально).
     */
    @Column
    private Integer positionNo;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        EstimateItem that = (EstimateItem) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}

