package com.example.multidatasoure.entity.primary;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "works")
@FieldNameConstants
public class Work {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(nullable = false)
    @ManyToOne
    @ToString.Exclude
    private User employee;
    @Enumerated(EnumType.STRING)

    @Column(nullable = false)
    private WorkStatus workStatus;

    @Column
    private OffsetDateTime plannedStartDate;

    @Column
    private OffsetDateTime plannedEndDate;

    @Column
    private OffsetDateTime actualStartDate;

    @Column
    private OffsetDateTime actualEndDate;

    @JoinColumn(nullable = false)
    @ManyToOne
    @ToString.Exclude
    private Estimate estimate;

    @JoinColumn
    @ManyToOne
    @ToString.Exclude
    private EstimateItem estimateItem;

    @Column(nullable = false, precision = 19, scale = 3)
    @Builder.Default
    private BigDecimal estimateItemQuantity = BigDecimal.ZERO;

    @JoinColumn
    @ManyToOne
    @ToString.Exclude
    private EstimateCatalogItem estimateCatalogItem;

    @Column(nullable = false, precision = 19, scale = 3)
    @Builder.Default
    private BigDecimal estimateCatalogItemQuantity = BigDecimal.ZERO;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Work work = (Work) o;
        return getId() != null && Objects.equals(getId(), work.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
