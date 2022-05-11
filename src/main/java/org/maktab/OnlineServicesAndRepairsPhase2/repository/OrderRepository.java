package org.maktab.OnlineServicesAndRepairsPhase2.repository;

import org.maktab.OnlineServicesAndRepairsPhase2.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order,Long> {
    List<Order> findByCustomerId(Long customerId);

    @Query("select o from Order o where o.orderStatus = 'WAITING_FOR_EXPERT_SUGGESTION'")
    List<Order> getByExpertSuggestionStatus();

    @Query("select o from Order o where o.id = :id")
    Order getById(@Param("id") Long id);

    @Query("select o from Order o where o.service.name = :serviceName and o.address = :city and o.orderStatus = 'WAITING_FOR_PROFESSIONAL_OFFER'")
    List<Order> getByServiceNameAndCityAndStatus(@Param("serviceName") String serviceName , @Param("city") String city);
}
