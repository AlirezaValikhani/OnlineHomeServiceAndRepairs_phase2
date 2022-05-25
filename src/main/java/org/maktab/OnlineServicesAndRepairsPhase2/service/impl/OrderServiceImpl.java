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
        Customer customer = customerService.getById(order.getId());
        if(customer == null)
            throw new NotFoundCustomerException();
        Specialty specialty = specialtyService.getById(order.getId());
        if(specialty == null)
            throw new NotFoundSpecialtyException();
        if (customer.getBalance() < specialty.getBasePrice())
            throw new NotEnoughBalanceException();
            Order toSaveOrder = new Order(order.getBidPriceOrder(),order.getJobDescription(),order.getAddress()
                    ,order.getOrderRegistrationDate(),order.getOrderExecutionDate()
                    ,OrderStatus.WAITING_FOR_EXPERT_SUGGESTION,customer,specialty);
            return save(toSaveOrder);
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
        Expert foundedExpert = expertService.getById(expert.getId());
        if(foundedExpert == null)
            throw new NotFoundExpertException();
        return orderRepository.getByCityAndServiceAndStatus(expert.getCity()
                ,expert.getServices());
    }

    @Override
    public Order chooseExpertForOrder(Order order) {
        Expert expert = expertService.getById(order.getId());
        if(expert == null)
            throw new NotFoundExpertException();
        Order foundedOrder = orderRepository.getById(order.getId());
        if(foundedOrder == null)
            throw new NotFoundOrderException();
        Order toSaveOrder = new Order(order.getBidPriceOrder(),order.getJobDescription(),order.getAddress()
                ,order.getOrderRegistrationDate(),order.getOrderExecutionDate()
                ,OrderStatus.WAITING_FOR_THE_SPECIALIST_TO_COME_TO_YOUR_PLACE,expert,order.getCustomer()
                ,order.getService(),order.getOffers());
        return save(toSaveOrder);
    }


}