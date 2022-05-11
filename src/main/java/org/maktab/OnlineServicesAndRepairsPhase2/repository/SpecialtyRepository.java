package org.maktab.OnlineServicesAndRepairsPhase2.repository;

import org.maktab.OnlineServicesAndRepairsPhase2.entity.Specialty;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface SpecialtyRepository extends CrudRepository<Specialty,Long> {
    Specialty findByName(String serviceName);

    @Query("select s from Specialty s where s.id = :id")
    Specialty getById(@Param("id") Long id);
}
