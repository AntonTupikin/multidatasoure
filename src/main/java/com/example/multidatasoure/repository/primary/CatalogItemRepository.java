package com.example.multidatasoure.repository.primary;

import com.example.multidatasoure.entity.primary.CatalogItem;
import com.example.multidatasoure.entity.primary.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatalogItemRepository extends JpaRepository<CatalogItem, Long> {
    Page<CatalogItem> findAllByOwner(User owner, Pageable pageable);
    Page<CatalogItem> findAllByOwnerAndMaterialNameContainingIgnoreCase(User owner, String materialName, Pageable pageable);
}

