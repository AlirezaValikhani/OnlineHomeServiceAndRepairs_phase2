package org.maktab.OnlineServicesAndRepairsPhase2.controller;

import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.CustomerDto;
import org.maktab.OnlineServicesAndRepairsPhase2.service.impl.CustomerServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerServiceImpl customerService;

    public CustomerController(CustomerServiceImpl customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/save")
    public ResponseEntity<CustomerDto> save(@Valid @RequestBody CustomerDto customerDto) {
        return customerService.save(customerDto);
    }

    @PutMapping("/updatePassword")
    public ResponseEntity<CustomerDto> changePassword(@RequestBody CustomerDto customerDto) {
        return customerService.changePassword(customerDto);
    }

    @PostMapping("/payment")
    public ResponseEntity<String> payment(@RequestBody CustomerDto customerDto) {
        return customerService.payment(customerDto);
    }
}
