package org.maktab.OnlineServicesAndRepairsPhase2.service.impl;

import org.maktab.OnlineServicesAndRepairsPhase2.entity.Customer;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Expert;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Order;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Specialty;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.enums.OrderStatus;
import org.maktab.OnlineServicesAndRepairsPhase2.exceptions.*;
import org.maktab.OnlineServicesAndRepairsPhase2.repository.OrderRepository;
import org.maktab.OnlineServicesAndRepairsPhase2.service.interfaces.OrderService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CustomerServiceImpl customerService;
    private final SpecialtyServiceImpl specialtyService;
    private final ExpertServiceImpl expertService;

    public OrderServiceImpl(OrderRepository orderRepository, @Lazy CustomerServiceImpl customerService, SpecialtyServiceImpl specialtyService, @Lazy ExpertServiceImpl expertService) {
        this.orderRepository = orderRepository;
        this.customerService = customerService;
        this.specialtyService = specialtyService;
        this.expertService = expertService;
    }

    @Override
    public List<Order> findByCustomerId(Long customerId) {
        List<Order> foundedOrderList = orderRepository.findByCustomerId(customerId);
        if(foundedOrderList.size() == 0)
            throw new NotFoundOrderException();
        return foundedOrderList;
    }

    @Override
    public List<Order> getByExpertSuggestion() {
        return orderRepository.getByExpertSuggestionStatus();
    }

    @Override
    public Order findById(Order order) {
        Order returnedOrder = orderRepository.getById(order.getId());
        if(returnedOrder == null)
            throw new NotFoundOrderException();
        return returnedOrder;
    }

    @Override
    public Order saveOrder(Order order) {
            return save(order);
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order getById(Long id) {
        return orderRepository.getById(id);
    }

    @Override
    public List<Order> getByCityAndService(Expert expert) {
        return orderRepository.getByCityAndServiceAndStatus(expert.getCity()
                ,expert.getServices());
    }

    @Override
    public Order chooseExpertForOrder(Order order) {
        return save(order);
    }
}