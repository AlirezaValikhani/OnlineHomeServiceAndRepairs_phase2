package org.maktab.OnlineServicesAndRepairsPhase2.controller;

import org.dozer.DozerBeanMapper;
import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.ExpertDto;
import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.OrderDto;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Expert;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Order;
import org.maktab.OnlineServicesAndRepairsPhase2.service.impl.CustomerServiceImpl;
import org.maktab.OnlineServicesAndRepairsPhase2.service.impl.ExpertServiceImpl;
import org.maktab.OnlineServicesAndRepairsPhase2.service.impl.OrderServiceImpl;
import org.maktab.OnlineServicesAndRepairsPhase2.service.impl.SpecialtyServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderServiceImpl orderService;
    private final DozerBeanMapper mapper;
    private final ModelMapper modelMapper;


    public OrderController(OrderServiceImpl orderService, CustomerServiceImpl customerService, SpecialtyServiceImpl specialtyService,ExpertServiceImpl expertService) {
        this.orderService = orderService;
        this.mapper = new DozerBeanMapper();
        this.modelMapper = new ModelMapper();
    }

    @PostMapping("/save")
    public ResponseEntity<OrderDto> save(@RequestBody OrderDto orderDto) {
        Order order = mapper.map(orderDto, Order.class);
        Order returnedOrder = orderService.saveOrder(order);
        OrderDto returnedOrderDto = modelMapper.map(returnedOrder, OrderDto.class);
        return new ResponseEntity<>(returnedOrderDto, HttpStatus.CREATED);
    }

    @GetMapping("/findById")
    public ResponseEntity<OrderDto> findById(@RequestParam OrderDto orderDto) {
        Order order = mapper.map(orderDto, Order.class);
        Order returnedOrder = orderService.findById(order);
        OrderDto returnedOrderDto = modelMapper.map(returnedOrder, OrderDto.class);
        if (returnedOrderDto != null)
            return ResponseEntity.ok(returnedOrderDto);
        else
            return ResponseEntity.notFound().build();
    }

    @GetMapping("/getByExpertSuggestion")
    public ResponseEntity<List<OrderDto>> getByExpertSuggestion(){
        List<Order> orders = orderService.getByExpertSuggestion();
        List<OrderDto> orderDtoList = new ArrayList<>();
        for (Order o:orders) {
            OrderDto orderDto = modelMapper.map(o, OrderDto.class);
            orderDtoList.add(orderDto);
        }
        return ResponseEntity.ok(orderDtoList);
    }

    @GetMapping("/getByCityAndService")
    public ResponseEntity<List<OrderDto>> getByCityAndService(@RequestBody ExpertDto expertDto) {
        Expert expert = mapper.map(expertDto, Expert.class);
        List<Order> returnedOrders = orderService.getByCityAndService(expert);
        List<OrderDto> orderDtoList = new ArrayList<>();
        for (Order o:returnedOrders) {
            OrderDto orderDto = modelMapper.map(o, OrderDto.class);
            orderDtoList.add(orderDto);
        }
        return ResponseEntity.ok(orderDtoList);
    }

    @PostMapping("/chooseExpertForOrder")
    public ResponseEntity<OrderDto> chooseExpertForOrder(@RequestBody OrderDto orderDto){
        Order order = mapper.map(orderDto, Order.class);
        Order returnedOrder = orderService.chooseExpertForOrder(order);
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
