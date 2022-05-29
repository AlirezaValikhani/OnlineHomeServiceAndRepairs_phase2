package org.maktab.OnlineServicesAndRepairsPhase2.repository;

import org.maktab.OnlineServicesAndRepairsPhase2.entity.Order;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Specialty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

public interface OrderRepository extends CrudRepository<Order,Long> {
    List<Order> findByCustomerId(Long customerId);

    @Query("select o from Order o where o.orderStatus = 'WAITING_FOR_EXPERT_SUGGESTION'")
    List<Order> getByExpertSuggestionStatus();

    @Query("select o from Order o where o.id = :id")
    Order getById(@Param("id") Long id);

    @Query("select o from Order o where o.id = :id")
    List<Order> getOrdersById(@Param("id") Long id);

    @Query("from Order o where o.address = :city and " +
            "o.service in (:services) and " +
            "o.orderStatus = 'WAITING_FOR_EXPERT_SUGGESTION' OR " +
            "o.orderStatus = 'WAITING_FOR_EXPERT_SELECTION' ")
    List<Order> getByCityAndServiceAndStatus(@Param("city") String city,
                                             @Param("services") Set<Specialty> specialtySet);
}
