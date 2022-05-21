package org.maktab.OnlineServicesAndRepairsPhase2.service.interfaces;

import org.maktab.OnlineServicesAndRepairsPhase2.entity.Expert;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Order;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface ExpertService {
    Expert findByNationalCode(String userName);
    Expert findByEmailAddress(String email);
    List<Expert> findAcceptedExperts();
    Expert findExpertServices(Long expertId);
    Expert save(Expert expert) throws IOException;
    Expert changePassword(Expert expert);
    List<Expert> waitingApprovalExperts();
    Expert expertApproval(Long id);
    Expert getById(Long id);
    void updateProfessionalStatus(Long id);
    String startOfWork(Order order);
    String done(Order order);
    Expert saveExpertObject(Expert expert);
}
