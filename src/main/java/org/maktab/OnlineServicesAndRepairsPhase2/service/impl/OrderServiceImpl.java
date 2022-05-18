package org.maktab.OnlineServicesAndRepairsPhase2.service.impl;

import org.dozer.DozerBeanMapper;
import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.ExpertDto;
import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.OrderDto;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Customer;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Expert;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Order;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Specialty;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.enums.OrderStatus;
import org.maktab.OnlineServicesAndRepairsPhase2.exceptions.*;
import org.maktab.OnlineServicesAndRepairsPhase2.repository.OrderRepository;
import org.maktab.OnlineServicesAndRepairsPhase2.service.interfaces.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CustomerServiceImpl customerService;
    private final SpecialtyServiceImpl specialtyService;
    private final ExpertServiceImpl expertService;
    private final DozerBeanMapper mapper;
    private final ModelMapper modelMapper;

    public OrderServiceImpl(OrderRepository orderRepository, CustomerServiceImpl customerService, SpecialtyServiceImpl specialtyService, ExpertServiceImpl expertService) {
        this.orderRepository = orderRepository;
        this.customerService = customerService;
        this.specialtyService = specialtyService;
        this.expertService = expertService;
        this.mapper = new DozerBeanMapper();
        this.modelMapper = new ModelMapper();
    }

    @Override
    public List<Order> findByCustomerId(Long customerId) {
        List<Order> foundedOrderList = orderRepository.findByCustomerId(customerId);
        if(foundedOrderList.size() == 0)
            throw new NotFoundOrderException();
        return foundedOrderList;
    }

    @Override
    public ResponseEntity<List<OrderDto>> getByExpertSuggestion() {
        List<Order> orders = orderRepository.getByExpertSuggestionStatus();
        List<OrderDto> orderDtoList = new ArrayList<>();
        for (Order o:orders) {
            OrderDto orderDto = modelMapper.map(o, OrderDto.class);
            orderDtoList.add(orderDto);
        }
        return ResponseEntity.ok(orderDtoList);
    }

    @Override
    public ResponseEntity<OrderDto> findById(OrderDto orderDto) {
        Order order = mapper.map(orderDto, Order.class);
        Order returnedOrder = orderRepository.getById(order.getId());
        if(returnedOrder == null)
            throw new NotFoundOrderException();
        OrderDto returnedOrderDto = modelMapper.map(returnedOrder, OrderDto.class);
        if (returnedOrderDto != null)
            return ResponseEntity.ok(returnedOrderDto);
        else
            return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<OrderDto> saveOrder(OrderDto orderDto) {
        Customer customer = customerService.getById(orderDto.getCustomerId());
        if(customer == null)
            throw new NotFoundCustomerException();
        Specialty specialty = specialtyService.getById(orderDto.getSpecialtyId());
        if(specialty == null)
            throw new NotFoundSpecialtyException();
        if (customer.getBalance() < specialty.getBasePrice())
            throw new NotEnoughBalanceException();
        Order order = mapper.map(orderDto, Order.class);
        if (order != null) {
            Order toSaveOrder = new Order(order.getBidPriceOrder(),order.getJobDescription(),order.getAddress()
                    ,order.getOrderRegistrationDate(),order.getOrderExecutionDate()
                    ,OrderStatus.WAITING_FOR_EXPERT_SUGGESTION,customer,specialty);
            Order returnedOrder = save(toSaveOrder);
            OrderDto returnedOrderDto = modelMapper.map(returnedOrder, OrderDto.class);
            return new ResponseEntity<>(returnedOrderDto, HttpStatus.CREATED);
        } else return ResponseEntity.notFound().build();
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
    public ResponseEntity<List<OrderDto>> getByCityAndService(ExpertDto expertDto) {
        Expert expert = expertService.getById(expertDto.getId());
        if(expert == null)
            throw new NotFoundExpertException();
        List<Order> returnedOrders = orderRepository.getByCityAndServiceAndStatus(expert.getCity()
                ,expert.getServices());
        List<OrderDto> orderDtoList = new ArrayList<>();
        for (Order o:returnedOrders) {
            OrderDto orderDto = modelMapper.map(o, OrderDto.class);
            orderDtoList.add(orderDto);
        }
        return ResponseEntity.ok(orderDtoList);
    }

    @Override
    public ResponseEntity<OrderDto> chooseExpertForOrder(OrderDto orderDto) {
        Expert expert = expertService.getById(orderDto.getExpertId());
        if(expert == null)
            throw new NotFoundExpertException();
        Order order = orderRepository.getById(orderDto.getId());
        if(order == null)
            throw new NotFoundOrderException();
        Order toSaveOrder = new Order(order.getBidPriceOrder(),order.getJobDescription(),order.getAddress()
                ,order.getOrderRegistrationDate(),order.getOrderExecutionDate()
                ,OrderStatus.WAITING_FOR_THE_SPECIALIST_TO_COME_TO_YOUR_PLACE,expert,order.getCustomer()
                ,order.getService(),order.getOffers());
        Order returnedOrder = save(toSaveOrder);
        OrderDto returnedOrderDto = modelMapper.map(returnedOrder, OrderDto.class);
        return ResponseEntity.ok(returnedOrderDto);
    }
}