package org.maktab.OnlineServicesAndRepairsPhase2.service.interfaces;

import org.maktab.OnlineServicesAndRepairsPhase2.entity.Order;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Specialty;

import java.util.List;
import java.util.Set;

public interface OrderService {
    List<Order> findByCustomerId(Long customerId);
    List<Order> loadByExpertSuggestionStatus();
    Order getById(Long id);
    Order save(Order order);
    List<Order> getByServiceNameAndCityAndStatus(String serviceName, Set<Specialty> specialties);
}
