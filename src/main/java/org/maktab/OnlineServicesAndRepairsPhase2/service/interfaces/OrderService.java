package org.maktab.OnlineServicesAndRepairsPhase2.service.interfaces;

import org.maktab.OnlineServicesAndRepairsPhase2.entity.Order;

import java.util.List;

public interface OrderService {
    List<Order> findByCustomerId(Long customerId);
    List<Order> loadByExpertSuggestionStatus();
    Order getById(Long id);
    Order save(Order order);
    List<Order> getByServiceNameAndCityAndStatus(String serviceName,String city);
}
