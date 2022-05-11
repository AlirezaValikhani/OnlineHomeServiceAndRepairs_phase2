package org.maktab.OnlineServicesAndRepairsPhase2.service.impl;

import org.maktab.OnlineServicesAndRepairsPhase2.entity.Order;
import org.maktab.OnlineServicesAndRepairsPhase2.repository.OrderRepository;
import org.maktab.OnlineServicesAndRepairsPhase2.service.interfaces.OrderService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Order> findByCustomerId(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    @Override
    public List<Order> loadByExpertSuggestionStatus() {
        return orderRepository.getByExpertSuggestionStatus();
    }

    @Override
    public Order getById(Long id) {
        return orderRepository.getById(id);
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getByServiceNameAndCityAndStatus(String serviceName,String city) {
        return orderRepository.getByServiceNameAndCityAndStatus(serviceName,city);
    }
}
