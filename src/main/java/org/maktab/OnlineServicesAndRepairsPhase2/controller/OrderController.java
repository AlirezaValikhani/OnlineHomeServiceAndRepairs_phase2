package org.maktab.OnlineServicesAndRepairsPhase2.controller;

import org.dozer.DozerBeanMapper;
import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.OrderDto;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Customer;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Order;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Specialty;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.enums.OrderStatus;
import org.maktab.OnlineServicesAndRepairsPhase2.service.impl.CustomerServiceImpl;
import org.maktab.OnlineServicesAndRepairsPhase2.service.impl.OrderServiceImpl;
import org.maktab.OnlineServicesAndRepairsPhase2.service.impl.SpecialtyServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderServiceImpl orderService;
    private final CustomerServiceImpl customerService;
    private final SpecialtyServiceImpl specialtyService;

    public OrderController(OrderServiceImpl orderService, CustomerServiceImpl customerService, SpecialtyServiceImpl specialtyService) {
        this.orderService = orderService;
        this.customerService = customerService;
        this.specialtyService = specialtyService;
    }

    @PostMapping("/save")
    public ResponseEntity<Order> save(@RequestBody OrderDto orderDto) {
        Customer customer = customerService.getById(orderDto.getCustomerId());
        Specialty specialty = specialtyService.getById(orderDto.getSpecialtyId());
        DozerBeanMapper mapper = new DozerBeanMapper();
        Order order = mapper.map(orderDto, Order.class);
        if (order != null) {
            order.setOrderStatus(OrderStatus.WAITING_FOR_EXPERT_SUGGESTION);
            order.setCustomer(customer);
            order.setService(specialty);
            return ResponseEntity.ok(orderService.save(order));
        } else return ResponseEntity.notFound().build();
    }

    @GetMapping("/getOrderByServiceNameAndCity")
    public ResponseEntity<List<Order>> getByCityAndService(@RequestBody OrderDto orderDto) {
        DozerBeanMapper mapper = new DozerBeanMapper();
        Order order = mapper.map(orderDto, Order.class);
        List<Order> orders = orderService.getByServiceNameAndCityAndStatus(order.getService().getName(),
                order.getAddress());
        if (orders != null)
            return ResponseEntity.ok(orders);
        else return ResponseEntity.notFound().build();
    }

    @GetMapping("/findById")
    public ResponseEntity<Order> findById(@RequestParam OrderDto orderDto) {
        DozerBeanMapper mapper = new DozerBeanMapper();
        Order order = mapper.map(orderDto, Order.class);
        Order returnedOrder = orderService.getById(order.getId());
        if (returnedOrder != null)
            return ResponseEntity.ok(returnedOrder);
        else
            return ResponseEntity.notFound().build();
    }

    @GetMapping("/getByExpertSuggestion")
    public ResponseEntity<List<Order>> getByExpertSuggestion(){
        return ResponseEntity.ok(orderService.loadByExpertSuggestionStatus());
    }
}
