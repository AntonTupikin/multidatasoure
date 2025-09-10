package com.example.multidatasoure.scenario.works;

import com.example.multidatasoure.controller.response.WorksResponse;
import com.example.multidatasoure.mapper.WorksMapper;
import com.example.multidatasoure.service.WorksService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WorksGetScenario {
    private final WorksService worksService;
    private final WorksMapper worksMapper;

    @Transactional(readOnly = true)
    public WorksResponse getById(Long userId, Long id) {
        var entity = worksService.getById(id);
        return worksMapper.toResponse(entity);
    }
}
