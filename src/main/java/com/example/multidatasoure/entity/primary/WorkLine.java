package com.example.multidatasoure.entity.primary;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "work_lines")
@FieldNameConstants
public class WorkLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "work_id", nullable = false)
    @ToString.Exclude
    private Work work;

    @ManyToOne(optional = false)
    @JoinColumn(name = "estimate_item_id", nullable = false)
    @ToString.Exclude
    private EstimateItem estimateItem;

    @Column(name = "qty_planned", precision = 19, scale = 3, nullable = false)
    @Builder.Default
    private BigDecimal qtyPlanned = BigDecimal.ZERO;

    @Column(name = "qty_actual", precision = 19, scale = 3, nullable = false)
    @Builder.Default
    private BigDecimal qtyActual = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    @Builder.Default
    private WorkLineStatus status = WorkLineStatus.PLANNED;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        WorkLine that = (WorkLine) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}

