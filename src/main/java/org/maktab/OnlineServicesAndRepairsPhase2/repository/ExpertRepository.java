package org.maktab.OnlineServicesAndRepairsPhase2.repository;

import lombok.NonNull;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Expert;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExpertRepository extends JpaRepository<Expert, Long>, JpaSpecificationExecutor<Expert> {
    Expert findByNationalCode(String userName);

    Expert findByEmailAddress(String email);

    @Query("select e from Expert e where e.userStatus = 'ACCEPTED'")
    List<Expert> findAcceptedExperts();

    @Query("select e.services from Expert e where e.id = :id")
    Expert findExpertServices(@Param("id") Long expertId);

    @Query("select e from Expert e where e.userStatus = 'WAITING_APPROVAL' ")
    List<Expert> waitingApprovalExperts();

    @Modifying
    @Query("update Expert e set e.userStatus = 'ACCEPTED' where e.id = :id")
    Expert updateExpertStatus(@Param(value = "id") Long id);

    @NonNull
    List<Expert> findAll(Specification<Expert> specification);
}
