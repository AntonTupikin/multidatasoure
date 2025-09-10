package com.example.multidatasoure.repository.primary;

import com.example.multidatasoure.entity.primary.User;
import com.example.multidatasoure.entity.primary.Work;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkRepository extends JpaRepository<Work, Long> {
    List<Work> findAllByEmployeeOrderByActualStartDateDescIdDesc(User employee);
}
