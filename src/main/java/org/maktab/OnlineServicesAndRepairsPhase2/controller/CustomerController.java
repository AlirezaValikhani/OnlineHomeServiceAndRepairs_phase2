package org.maktab.OnlineServicesAndRepairsPhase2.controller;

import org.dozer.DozerBeanMapper;
import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.CustomerDto;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Customer;
import org.maktab.OnlineServicesAndRepairsPhase2.service.impl.CustomerServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerServiceImpl customerService;
    private final DozerBeanMapper mapper;
    private final ModelMapper modelMapper;

    public CustomerController(CustomerServiceImpl customerService) {
        this.customerService = customerService;
        this.mapper = new DozerBeanMapper();
        this.modelMapper = new ModelMapper();
    }

    @PostMapping("/save")
    public ResponseEntity<CustomerDto> save(@Valid @RequestBody CustomerDto customerDto) {
        Customer customer = mapper.map(customerDto, Customer.class);
        Customer returnedCustomer = customerService.save(customer);
        CustomerDto returnedCustomerDto = modelMapper.map(returnedCustomer, CustomerDto.class);
        return new ResponseEntity<>(returnedCustomerDto, HttpStatus.CREATED);
    }

    @PutMapping("/updatePassword")
    public ResponseEntity<CustomerDto> changePassword(@RequestBody CustomerDto customerDto) {
        Customer customer = mapper.map(customerDto, Customer.class);
        Customer returnedCustomer = customerService.changePassword(customer);
        CustomerDto returnedCustomerDto = modelMapper.map(returnedCustomer, CustomerDto.class);
        return ResponseEntity.ok(returnedCustomerDto);
    }

    @PostMapping("/payment")
    public ResponseEntity<String> payment(@RequestBody CustomerDto customerDto) {
        Customer customer = mapper.map(customerDto, Customer.class);
        String message = customerService.payment(customer);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/rating")
    public ResponseEntity<String> rating(@RequestBody CustomerDto customerDto) {
        Customer customer = mapper.map(customerDto, Customer.class);
        String message = customerService.rating(customer,customerDto.getExpertId());
        return ResponseEntity.ok(message);
    }
}
