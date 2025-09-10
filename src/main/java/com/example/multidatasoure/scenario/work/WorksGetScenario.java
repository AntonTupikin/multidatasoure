package com.example.multidatasoure.scenario.work;

import com.example.multidatasoure.controller.response.WorkResponse;
import com.example.multidatasoure.entity.primary.Role;
import com.example.multidatasoure.entity.primary.User;
import com.example.multidatasoure.entity.primary.Work;
import com.example.multidatasoure.exception.NotFoundException;
import com.example.multidatasoure.mapper.WorkMapper;
import com.example.multidatasoure.service.UserService;
import com.example.multidatasoure.service.WorkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WorksGetScenario {
    private final WorkService workService;
    private final UserService userService;
    private final WorkMapper workMapper;

    @Transactional(readOnly = true)
    public WorkResponse getById(Long userId, Long id) {
        //TODO:
        User user = userService.findById(userId);
        Work work = null;
        if (user.getRole()==Role.SUPERVISOR){
            work = workService.getByIdAndEmployeeSupervisor(id, user);

        }
        if (user.getRole()== Role.EMPLOYEE){
            work = workService.getByIdAndEmployee(id, user);
        }
        if (work == null){
            throw new NotFoundException("message.exception.not-found.works");
        }
        return workMapper.toResponse(work);
    }
}
