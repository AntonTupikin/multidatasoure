package com.example.multidatasoure.entity.primary;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "estimate_item_history")
@FieldNameConstants
public class EstimateItemHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "item_id", nullable = false)
    @ToString.Exclude
    private EstimateItem item;

    @ManyToOne(optional = false)
    @JoinColumn(name = "changed_by", nullable = false)
    private User changedBy;

    @Column(name = "changed_at", nullable = false)
    private Instant changedAt;

    @Column(name = "old_unit", length = 50)
    private String oldUnit;

    @Column(name = "new_unit", length = 50)
    private String newUnit;

    @Column(name = "old_quantity", precision = 19, scale = 3)
    private BigDecimal oldQuantity;

    @Column(name = "new_quantity", precision = 19, scale = 3)
    private BigDecimal newQuantity;

    @Column(name = "old_unit_price", precision = 19, scale = 2)
    private BigDecimal oldUnitPrice;

    @Column(name = "new_unit_price", precision = 19, scale = 2)
    private BigDecimal newUnitPrice;
}

