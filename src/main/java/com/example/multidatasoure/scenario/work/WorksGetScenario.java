package com.example.multidatasoure.scenario.work;

import com.example.multidatasoure.controller.response.WorkResponse;
import com.example.multidatasoure.entity.primary.Work;
import com.example.multidatasoure.mapper.WorkMapper;
import com.example.multidatasoure.service.WorkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WorksGetScenario {
    private final WorkService workService;
    private final WorkMapper workMapper;

    @Transactional(readOnly = true)
    public WorkResponse getById(Long userId, Long id) {
        Work entity = workService.getById(id);
        return workMapper.toResponse(entity);
    }
}
