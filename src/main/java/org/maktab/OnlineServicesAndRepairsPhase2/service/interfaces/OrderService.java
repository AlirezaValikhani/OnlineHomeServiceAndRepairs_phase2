package org.maktab.OnlineServicesAndRepairsPhase2.service.interfaces;

import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.ExpertDto;
import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.OrderDto;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Expert;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Order;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Specialty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Set;

public interface OrderService {
    List<Order> findByCustomerId(Long customerId);
    List<Order> getByExpertSuggestion();
    Order findById(Order order);
    Order saveOrder(Order order);
    Order save(Order order);
    Order getById(Long id);
    List<Order> getByCityAndService(Expert expert);
    Order chooseExpertForOrder(Order order);
}
