package com.example.multidatasoure.repository.primary;

import com.example.multidatasoure.entity.primary.User;
import com.example.multidatasoure.entity.primary.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WorkRepository extends JpaRepository<Work, Long> {
    List<Work> findAllByEmployeeOrderByActualStartDateDescIdDesc(User employee);

    Optional<Work> findByIdAndEmployee(Long id, User employee);
    Optional<Work> findByIdAndEmployee_Supervisor(Long id, User user);

    @Query("select distinct w from Work w join w.lines l join l.estimateItem ei where ei.estimate.id = :estimateId order by w.id desc")
    List<Work> findAllByEstimateId(@Param("estimateId") Long estimateId);

}
