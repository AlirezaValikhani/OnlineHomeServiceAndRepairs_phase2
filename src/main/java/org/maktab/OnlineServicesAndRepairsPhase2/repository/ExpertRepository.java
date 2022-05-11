package org.maktab.OnlineServicesAndRepairsPhase2.repository;

import org.maktab.OnlineServicesAndRepairsPhase2.entity.Expert;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ExpertRepository extends CrudRepository<Expert,Long> {
    Expert findByNationalCode(String userName);
    Expert findByEmailAddress(String email);
    Expert getById(Long id);

    @Query(value = "update Expert e set e.password = :password where e.id = :id")
    Expert updatePassword(@Param("password") String password,@Param("id") Long id);

    @Query("select e from Expert e where e.userStatus = 'ACCEPTED'")
    List<Expert> findAcceptedExperts();

    @Query("select e from Expert e where e.userStatus = 'WAITING_APPROVAL'")
    List<Expert> findWaitingApprovalExperts();

    @Query("select e.services from Expert e where e.id = :id")
    Expert findExpertServices(@Param("id") Long expertId);

    @Query("select e from Expert e where e.userStatus = 'WAITING_APPROVAL' ")
    List<Expert> waitingApprovalExperts();
}
