package com.example.multidatasoure.repository.primary;

import com.example.multidatasoure.entity.primary.EstimateItemHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EstimateItemHistoryRepository extends JpaRepository<EstimateItemHistory, Long> {
    List<EstimateItemHistory> findAllByItemIdOrderByChangedAtDesc(Long itemId);
}

