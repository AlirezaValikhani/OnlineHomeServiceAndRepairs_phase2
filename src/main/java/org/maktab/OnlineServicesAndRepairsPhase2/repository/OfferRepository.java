package org.maktab.OnlineServicesAndRepairsPhase2.repository;

import org.maktab.OnlineServicesAndRepairsPhase2.entity.Offer;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Specialty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface OfferRepository extends CrudRepository<Offer,Long> {
    List<Offer> findByOrderId(Long orderId);

    @Query("select o from Offer o where o.id = :id")
    Offer getById(@Param("id") Long id);
}
