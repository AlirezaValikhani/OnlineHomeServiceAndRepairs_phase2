package org.maktab.OnlineServicesAndRepairsPhase2.controller;

import org.dozer.DozerBeanMapper;
import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.OrderDto;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Customer;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Expert;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Order;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Specialty;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.enums.OrderStatus;
import org.maktab.OnlineServicesAndRepairsPhase2.exceptions.NotFoundCustomerException;
import org.maktab.OnlineServicesAndRepairsPhase2.exceptions.NotFoundExpertException;
import org.maktab.OnlineServicesAndRepairsPhase2.exceptions.NotFoundOrderException;
import org.maktab.OnlineServicesAndRepairsPhase2.exceptions.NotFoundSpecialtyException;
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
    private final CustomerServiceImpl customerService;
    private final SpecialtyServiceImpl specialtyService;
    private final ExpertServiceImpl expertService;
    private final DozerBeanMapper mapper;
    private final ModelMapper modelMapper;


    public OrderController(OrderServiceImpl orderService, CustomerServiceImpl customerService, SpecialtyServiceImpl specialtyService, ExpertServiceImpl expertService, CustomerServiceImpl customerService1, SpecialtyServiceImpl specialtyService1, ExpertServiceImpl expertService1) {
        this.orderService = orderService;
        this.customerService = customerService1;
        this.specialtyService = specialtyService1;
        this.expertService = expertService1;
        this.mapper = new DozerBeanMapper();
        this.modelMapper = new ModelMapper();
    }

    @PostMapping("/save")
    public ResponseEntity<OrderDto> save(@RequestBody OrderDto orderDto) {
        Customer customer = customerService.getById(orderDto.getCustomerId());
        if(customer == null)
            throw new NotFoundCustomerException();
        Specialty specialty = specialtyService.getById(orderDto.getSpecialtyId());
        if(specialty == null)
            throw new NotFoundSpecialtyException();
        Order order = new Order(orderDto.getBidPriceOrder(),orderDto.getJobDescription(),
                orderDto.getAddress(),orderDto.getOrderRegistrationDate(),
                orderDto.getOrderExecutionDate(), OrderStatus.WAITING_FOR_EXPERT_SUGGESTION,
                customer,specialty);
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
    public ResponseEntity<List<OrderDto>> getByCityAndService(@RequestBody OrderDto orderDto) {
        Expert foundedExpert = expertService.getById(orderDto.getExpertId());
        if(foundedExpert == null)
            throw new NotFoundExpertException();
        List<Order> returnedOrders = orderService.getByCityAndService(foundedExpert);
        List<OrderDto> orderDtoList = new ArrayList<>();
        for (Order o:returnedOrders) {
            OrderDto convertToOrderDto = modelMapper.map(o, OrderDto.class);
            orderDtoList.add(convertToOrderDto);
        }
        return ResponseEntity.ok(orderDtoList);
    }

    @PostMapping("/chooseExpertForOrder")
    public ResponseEntity<OrderDto> chooseExpertForOrder(@RequestBody OrderDto orderDto){
        Expert expert = expertService.getById(orderDto.getExpertId());
        if(expert == null)
            throw new NotFoundExpertException();
        Order foundedOrder = orderService.getById(orderDto.getId());
        if(foundedOrder == null)
            throw new NotFoundOrderException();
        foundedOrder.setExpert(expert);
        foundedOrder.setOrderStatus(OrderStatus.WAITING_FOR_THE_SPECIALIST_TO_COME_TO_YOUR_PLACE);
        Order returnedOrder = orderService.save(foundedOrder);
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
