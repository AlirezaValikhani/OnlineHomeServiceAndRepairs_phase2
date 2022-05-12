package org.maktab.OnlineServicesAndRepairsPhase2.controller;

import org.dozer.DozerBeanMapper;
import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.ExpertDto;
import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.OrderDto;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Customer;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Expert;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Order;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Specialty;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.enums.OrderStatus;
import org.maktab.OnlineServicesAndRepairsPhase2.service.impl.CustomerServiceImpl;
import org.maktab.OnlineServicesAndRepairsPhase2.service.impl.ExpertServiceImpl;
import org.maktab.OnlineServicesAndRepairsPhase2.service.impl.OrderServiceImpl;
import org.maktab.OnlineServicesAndRepairsPhase2.service.impl.SpecialtyServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderServiceImpl orderService;
    private final CustomerServiceImpl customerService;
    private final SpecialtyServiceImpl specialtyService;
    private final ExpertServiceImpl expertService;
    private final DozerBeanMapper mapper;
    private final ModelMapper modelMapper;

    public OrderController(OrderServiceImpl orderService, CustomerServiceImpl customerService, SpecialtyServiceImpl specialtyService,ExpertServiceImpl expertService) {
        this.orderService = orderService;
        this.customerService = customerService;
        this.specialtyService = specialtyService;
        this.expertService = expertService;
        this.mapper = new DozerBeanMapper();
        this.modelMapper = new ModelMapper();
    }

    @PostMapping("/save")
    public ResponseEntity<OrderDto> save(@RequestBody OrderDto orderDto) {
        Customer customer = customerService.getById(orderDto.getCustomerId());
        Specialty specialty = specialtyService.getById(orderDto.getSpecialtyId());
        Order order = mapper.map(orderDto, Order.class);
        if (order != null) {
            order.setOrderStatus(OrderStatus.WAITING_FOR_EXPERT_SUGGESTION);
            order.setCustomer(customer);
            order.setService(specialty);
            Order returnedOrder = orderService.save(order);
            OrderDto returnedOrderDto = modelMapper.map(returnedOrder, OrderDto.class);
            return ResponseEntity.ok(returnedOrderDto);
        } else return ResponseEntity.notFound().build();
    }

    /*@GetMapping("/getOrderByServiceNameAndCity")
    public ResponseEntity<List<OrderDto>> getByCityAndService(@RequestBody OrderDto orderDto) {
        Specialty specialty = specialtyService.getById(orderDto.getSpecialtyId());
        Order order = mapper.map(orderDto, Order.class);
        List<Order> orders = orderService.getByServiceNameAndCityAndStatus(specialty.getName(),
                order.getAddress());
        List<OrderDto> orderDtoList = new ArrayList<>();
        for (Order o:orders) {
            OrderDto returnedOrderDto = modelMapper.map(o, OrderDto.class);
            orderDtoList.add(returnedOrderDto);
        }
            return ResponseEntity.ok(orderDtoList);
    }*/

    @GetMapping("/findById")
    public ResponseEntity<OrderDto> findById(@RequestParam OrderDto orderDto) {
        Order order = mapper.map(orderDto, Order.class);
        Order returnedOrder = orderService.getById(order.getId());
        OrderDto returnedOrderDto = modelMapper.map(returnedOrder, OrderDto.class);
        if (returnedOrderDto != null)
            return ResponseEntity.ok(returnedOrderDto);
        else
            return ResponseEntity.notFound().build();
    }

    @GetMapping("/getByExpertSuggestion")
    public ResponseEntity<List<OrderDto>> getByExpertSuggestion(){
        List<Order> orders = orderService.loadByExpertSuggestionStatus();
        List<OrderDto> orderDtoList = new ArrayList<>();
        for (Order o:orders) {
            OrderDto orderDto = modelMapper.map(o, OrderDto.class);
            orderDtoList.add(orderDto);
        }
        return ResponseEntity.ok(orderDtoList);
    }

    @GetMapping("/getByCityAndService")
    public ResponseEntity<List<OrderDto>> getByCityAndService(@RequestBody ExpertDto expertDto) {
        Expert expert = expertService.getById(expertDto.getId());
        List<Order> returnedOrders = orderService.getByServiceNameAndCityAndStatus(expert.getCity(),expert.getServices());
        List<OrderDto> orderDtoList = new ArrayList<>();
        for (Order o:returnedOrders) {
            OrderDto orderDto = modelMapper.map(o, OrderDto.class);
            orderDtoList.add(orderDto);
        }
        return ResponseEntity.ok(orderDtoList);
    }

    @PostMapping("/chooseExpertForOrder")
    public ResponseEntity<OrderDto> chooseExpertForOrder(@RequestBody OrderDto orderDto){
        Expert expert = expertService.getById(orderDto.getExpertId());
        Order order = orderService.getById(orderDto.getId());
        order.setOrderStatus(OrderStatus.WAITING_FOR_THE_SPECIALIST_TO_COME_TO_YOUR_PLACE);
        order.setExpert(expert);
        Order returnedOrder = orderService.save(order);
        OrderDto returnedOrderDto = modelMapper.map(returnedOrder, OrderDto.class);
        return ResponseEntity.ok(returnedOrderDto);
    }
}




    /*@GetMapping("/getByCityAndService")
    public ResponseEntity<List<OrderDto>> getByCityAndService(@RequestBody ExpertDto expertDto) {
        Expert expert = expertService.getById(expertDto.getId());
        List<Order> orders = new ArrayList<>();
        List<OrderDto> orderDtoList = new ArrayList<>();
        Set<Specialty> specialties = expert.getServices();
        for (Specialty s:specialties) {
            List<Order> returnedOrders = orderService.getByServiceNameAndCityAndStatus(s.getName(),expert.getCity());
            orders.addAll(returnedOrders);
        }
        for (Order o:orders) {
            OrderDto returnedOrderDto = modelMapper.map(o, OrderDto.class);
            orderDtoList.add(returnedOrderDto);
        }
        return ResponseEntity.ok(orderDtoList);
    }*/
