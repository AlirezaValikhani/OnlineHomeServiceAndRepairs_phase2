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


    public OrderController(OrderServiceImpl orderService, CustomerServiceImpl customerService, SpecialtyServiceImpl specialtyService,ExpertServiceImpl expertService) {
        this.orderService = orderService;
    }

    @PostMapping("/save")
    public ResponseEntity<OrderDto> save(@RequestBody OrderDto orderDto) {
        return save(orderDto);
    }

    @GetMapping("/findById")
    public ResponseEntity<OrderDto> findById(@RequestParam OrderDto orderDto) {
        return orderService.findById(orderDto);
    }

    @GetMapping("/getByExpertSuggestion")
    public ResponseEntity<List<OrderDto>> getByExpertSuggestion(){
        return orderService.getByExpertSuggestion();
    }

    @GetMapping("/getByCityAndService")
    public ResponseEntity<List<OrderDto>> getByCityAndService(@RequestBody ExpertDto expertDto) {
        return orderService.getByCityAndService(expertDto);
    }

    @PostMapping("/chooseExpertForOrder")
    public ResponseEntity<OrderDto> chooseExpertForOrder(@RequestBody OrderDto orderDto){
        return orderService.chooseExpertForOrder(orderDto);
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
