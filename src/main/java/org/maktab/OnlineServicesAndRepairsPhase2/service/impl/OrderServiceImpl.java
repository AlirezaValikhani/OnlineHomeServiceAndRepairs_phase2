package org.maktab.OnlineServicesAndRepairsPhase2.service.impl;

import org.dozer.DozerBeanMapper;
import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.ExpertDto;
import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.OrderDto;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Customer;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Expert;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Order;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Specialty;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.enums.OrderStatus;
import org.maktab.OnlineServicesAndRepairsPhase2.repository.OrderRepository;
import org.maktab.OnlineServicesAndRepairsPhase2.service.interfaces.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
        return orderRepository.findByCustomerId(customerId);
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
        OrderDto returnedOrderDto = modelMapper.map(returnedOrder, OrderDto.class);
        if (returnedOrderDto != null)
            return ResponseEntity.ok(returnedOrderDto);
        else
            return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<OrderDto> saveOrder(OrderDto orderDto) {
        Customer customer = customerService.getById(orderDto.getCustomerId());
        Specialty specialty = specialtyService.getById(orderDto.getSpecialtyId());
        Order order = mapper.map(orderDto, Order.class);
        if (order != null) {
            order.setOrderStatus(OrderStatus.WAITING_FOR_EXPERT_SUGGESTION);
            order.setCustomer(customer);
            order.setService(specialty);
            Order returnedOrder = save(order);
            OrderDto returnedOrderDto = modelMapper.map(returnedOrder, OrderDto.class);
            return ResponseEntity.ok(returnedOrderDto);
        } else return ResponseEntity.notFound().build();
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public ResponseEntity<List<OrderDto>> getByCityAndService(ExpertDto expertDto) {
        Expert expert = expertService.getById(expertDto.getId());
        List<Order> returnedOrders = orderRepository.getByCityAndServiceAndStatus(expert.getCity(),expert.getServices());
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
        Order order = orderRepository.getById(orderDto.getId());
        order.setOrderStatus(OrderStatus.WAITING_FOR_THE_SPECIALIST_TO_COME_TO_YOUR_PLACE);
        order.setExpert(expert);
        Order returnedOrder = save(order);
        OrderDto returnedOrderDto = modelMapper.map(returnedOrder, OrderDto.class);
        return ResponseEntity.ok(returnedOrderDto);
    }
}
