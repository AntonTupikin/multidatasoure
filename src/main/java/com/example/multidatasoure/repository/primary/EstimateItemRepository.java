package com.example.multidatasoure.repository.primary;

import com.example.multidatasoure.entity.primary.Estimate;
import com.example.multidatasoure.entity.primary.EstimateItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import jakarta.persistence.LockModeType;

import java.util.List;
import java.util.Optional;

public interface EstimateItemRepository extends JpaRepository<EstimateItem, Long> {
    List<EstimateItem> findAllByEstimateOrderByPositionNoAscIdAsc(Estimate estimate);

    Optional<EstimateItem> findByIdAndEstimate(Long id, Estimate estimate);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select ei from EstimateItem ei where ei.id = :id")
    Optional<EstimateItem> findByIdForUpdate(@Param("id") Long id);

}
