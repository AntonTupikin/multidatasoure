package com.example.multidatasoure.repository.primary;

import com.example.multidatasoure.entity.primary.Estimate;
import com.example.multidatasoure.entity.primary.EstimateItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EstimateItemRepository extends JpaRepository<EstimateItem, Long> {
    List<EstimateItem> findAllByEstimateOrderByPositionNoAscIdAsc(Estimate estimate);
}

