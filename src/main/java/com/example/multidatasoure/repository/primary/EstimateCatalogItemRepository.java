package com.example.multidatasoure.repository.primary;

import com.example.multidatasoure.entity.primary.CatalogItem;
import com.example.multidatasoure.entity.primary.Estimate;
import com.example.multidatasoure.entity.primary.EstimateCatalogItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EstimateCatalogItemRepository extends JpaRepository<EstimateCatalogItem, Long> {
    List<EstimateCatalogItem> findAllByEstimateOrderByPositionNoAscIdAsc(Estimate estimate);
    Optional<EstimateCatalogItem> findByEstimateAndCatalogItem(Estimate estimate, CatalogItem catalogItem);
}

