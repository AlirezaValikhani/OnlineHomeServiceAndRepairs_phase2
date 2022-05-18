package org.maktab.OnlineServicesAndRepairsPhase2.service.interfaces;

import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.ExpertDto;
import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.OrderDto;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Expert;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

public interface ExpertService {
    Expert findByNationalCode(String userName);
    Expert findByEmailAddress(String email);
    List<Expert> findAcceptedExperts();
    Expert findExpertServices(Long expertId);
    ResponseEntity<ExpertDto> save(ExpertDto expertDto) throws IOException;
    ResponseEntity<ExpertDto> changePassword(ExpertDto expertDto);
    ResponseEntity<List<ExpertDto>> waitingApprovalExperts();
    Expert expertApproval(Long id);
    Expert getById(Long id);
    void updateProfessionalStatus(Long id);
    void startOfWork(OrderDto orderDto);
    void done(OrderDto orderDto);
}
