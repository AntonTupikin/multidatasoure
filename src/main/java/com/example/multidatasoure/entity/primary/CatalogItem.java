package com.example.multidatasoure.entity.primary;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Глобальная позиция сметы (элемент каталога),
 * создается один раз и может быть добавлена в разные сметы.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "catalog_items")
@FieldNameConstants
public class CatalogItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Владелец каталога (мультиарендность).
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    private User owner;

    /**
     * Наименование позиции сметы.
     */
    @Column(name = "material_name", nullable = false, length = 255)
    private String materialName;

    /**
     * Единица измерения.
     */
    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private UnitOfMeasure unit;

    /**
     * Базовая цена за единицу (опционально).
     */
    @Column(name = "unit_price", nullable = false, precision = 19, scale = 2)
    @Builder.Default
    private BigDecimal unitPrice = BigDecimal.ZERO;

    /**
     * Категория/раздел.
     */
    @Enumerated(EnumType.STRING)
    @Column(length = 100)
    private ItemCategory category;

    /**
     * Опциональный контрагент.
     */
    @ManyToOne
    @JoinColumn(name = "business_partner_id")
    private BusinessPartner businessPartner;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        CatalogItem that = (CatalogItem) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
