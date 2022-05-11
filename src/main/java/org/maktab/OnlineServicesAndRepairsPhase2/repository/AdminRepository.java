package org.maktab.OnlineServicesAndRepairsPhase2.repository;

import org.maktab.OnlineServicesAndRepairsPhase2.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AdminRepository extends JpaRepository<Admin,Long> {
    Admin findByNationalCode(String userName);

    @Query("select a.firstName,a.lastName,a.nationalCode,a.password from Admin a where a.nationalCode = :nationalCode")
    Admin findAdminByNationalCode(@Param("nationalCode") String nationalCode);

    @Query("update Admin set password = :password where nationalCode = :nationalCode")
    Admin update(@Param("password") String password,@Param("nationalCode") String nationalCode);
}
