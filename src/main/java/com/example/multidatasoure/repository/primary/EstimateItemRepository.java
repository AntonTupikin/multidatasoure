package com.example.multidatasoure.repository.primary;

import com.example.multidatasoure.entity.primary.Estimate;
import com.example.multidatasoure.entity.primary.EstimateItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EstimateItemRepository extends JpaRepository<EstimateItem, Long> {
    List<EstimateItem> findAllByEstimateOrderByPositionNoAscIdAsc(Estimate estimate);

    Optional<EstimateItem> findByIdAndEstimate(Long id, Estimate estimate);

}

