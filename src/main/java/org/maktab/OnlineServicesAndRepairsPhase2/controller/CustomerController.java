package org.maktab.OnlineServicesAndRepairsPhase2.controller;

import org.dozer.DozerBeanMapper;
import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.CustomerDto;
import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.ExpertDto;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Category;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Customer;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Expert;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.enums.UserStatus;
import org.maktab.OnlineServicesAndRepairsPhase2.service.impl.CategoryServiceImpl;
import org.maktab.OnlineServicesAndRepairsPhase2.service.impl.CustomerServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<CustomerDto> save(@RequestBody CustomerDto customerDto){
        Customer customer = mapper.map(customerDto, Customer.class);
        customer.setUserStatus(UserStatus.NEW);
        Customer returnedCustomer = customerService.save(customer);
        CustomerDto returnedCustomerDto = modelMapper.map(returnedCustomer, CustomerDto.class);
        if (returnedCustomer != null)
            return ResponseEntity.ok(returnedCustomerDto);
        else return ResponseEntity.notFound().build();
    }

    @PutMapping("/updatePassword")
    public ResponseEntity<CustomerDto> changePassword(@RequestBody CustomerDto customerDto){
        Customer customer = mapper.map(customerDto, Customer.class);
        Customer returnedCustomer = customerService.changePassword(customer);
        CustomerDto returnedCustomerDto = modelMapper.map(returnedCustomer, CustomerDto.class);
            return ResponseEntity.ok(returnedCustomerDto);
    }
}
