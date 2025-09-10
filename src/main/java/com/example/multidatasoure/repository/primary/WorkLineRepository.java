package com.example.multidatasoure.repository.primary;

import com.example.multidatasoure.entity.primary.WorkLine;
import com.example.multidatasoure.entity.primary.WorkLineStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface WorkLineRepository extends JpaRepository<WorkLine, Long> {

    @Query("select coalesce(sum(wl.qtyPlanned), 0) from WorkLine wl where wl.estimateItem.id = :itemId and wl.status in :statuses")
    BigDecimal sumPlannedByItemAndStatuses(@Param("itemId") Long estimateItemId,
                                           @Param("statuses") List<WorkLineStatus> statuses);

    @Query("select coalesce(sum(wl.qtyActual), 0) from WorkLine wl where wl.estimateItem.id = :itemId and wl.status = com.example.multidatasoure.entity.primary.WorkLineStatus.DONE")
    BigDecimal sumActualDoneByItem(@Param("itemId") Long estimateItemId);
}

