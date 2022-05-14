package org.maktab.OnlineServicesAndRepairsPhase2.service.interfaces;

import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.ExpertDto;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Expert;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.sql.Timestamp;
import java.util.List;

public interface ExpertService {
    Expert findByNationalCode(String userName);
    Expert findByEmailAddress(String email);
    List<Expert> findAcceptedExperts();
    Expert findExpertServices(Long expertId);
    ResponseEntity<ExpertDto> save(ExpertDto expertDto);
    ResponseEntity<ExpertDto> changePassword(ExpertDto expertDto);
    ResponseEntity<List<ExpertDto>> waitingApprovalExperts();
    Expert expertApproval(Long id);
    Expert getById(Long id);
    void updateProfessionalStatus(Long id);
}
