package org.maktab.OnlineServicesAndRepairsPhase2.repository;

import org.maktab.OnlineServicesAndRepairsPhase2.entity.base.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    @Query("select u from User u where u.nationalCode = :nationalCode")
    User findByNationalCodeReturnObject(@Param("nationalCode") String nationalCode);

    User getById(Long id);

    Optional<User> findByNationalCode(String nationalCode);

    User findByEmailAddress(String email);
}
