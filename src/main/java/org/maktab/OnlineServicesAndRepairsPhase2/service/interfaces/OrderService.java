package org.maktab.OnlineServicesAndRepairsPhase2.service.interfaces;

import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.ExpertDto;
import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.OrderDto;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Order;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Specialty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Set;

public interface OrderService {
    List<Order> findByCustomerId(Long customerId);
    ResponseEntity<List<OrderDto>> getByExpertSuggestion();
    ResponseEntity<OrderDto> findById(OrderDto orderDto);
    ResponseEntity<OrderDto> saveOrder(OrderDto orderDto);
    Order save(Order order);
    ResponseEntity<List<OrderDto>> getByCityAndService(ExpertDto expertDto);
    ResponseEntity<OrderDto> chooseExpertForOrder(OrderDto orderDto);
}
