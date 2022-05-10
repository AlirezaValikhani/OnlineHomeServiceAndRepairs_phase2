package org.maktab.OnlineServicesAndRepairsPhase2.service.interfaces;

import org.maktab.OnlineServicesAndRepairsPhase2.entity.Expert;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExpertService {
    Expert findByNationalCode(String userName);
    Expert findByEmailAddress(String email);
    List<Expert> findAcceptedExperts();
    List<Expert> findWaitingApprovalExperts();
    Expert findExpertServices(Long expertId);
}
