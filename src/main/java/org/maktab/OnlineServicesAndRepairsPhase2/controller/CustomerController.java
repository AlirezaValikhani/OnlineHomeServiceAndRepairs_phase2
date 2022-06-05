package org.maktab.OnlineServicesAndRepairsPhase2.controller;

import org.dozer.DozerBeanMapper;
import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.CustomerDto;
import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.DynamicSearch;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Customer;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Offer;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Order;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.enums.Role;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.enums.UserStatus;
import org.maktab.OnlineServicesAndRepairsPhase2.exceptions.NotFoundCustomerException;
import org.maktab.OnlineServicesAndRepairsPhase2.exceptions.NotFoundOfferException;
import org.maktab.OnlineServicesAndRepairsPhase2.exceptions.NotFoundOrderException;
import org.maktab.OnlineServicesAndRepairsPhase2.service.impl.CustomerServiceImpl;
import org.maktab.OnlineServicesAndRepairsPhase2.service.impl.OfferServiceImpl;
import org.maktab.OnlineServicesAndRepairsPhase2.service.impl.OrderServiceImpl;
import org.maktab.OnlineServicesAndRepairsPhase2.util.CustomPasswordEncoder;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerServiceImpl customerService;
    private final OrderServiceImpl orderService;
    private final OfferServiceImpl offerService;
    private final DozerBeanMapper mapper;
    private final ModelMapper modelMapper;

    public CustomerController(CustomerServiceImpl customerService, OrderServiceImpl orderService, OfferServiceImpl offerService) {
        this.customerService = customerService;
        this.orderService = orderService;
        this.offerService = offerService;
        this.mapper = new DozerBeanMapper();
        this.modelMapper = new ModelMapper();
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@Valid @RequestBody CustomerDto customerDto) {
        Customer customer = new Customer(customerDto.getFirstName(),customerDto.getLastName(),
                customerDto.getEmailAddress(),customerDto.getNationalCode(),
                CustomPasswordEncoder.hashPassword(customerDto.getPassword()),
                customerDto.getBalance(),customerDto.getCredit(), UserStatus.NEW, Role.ROLE_CUSTOMER);
        Customer returnedCustomer = customerService.save(customer);
        String message = returnedCustomer.getFirstName() + " " + returnedCustomer.getLastName() +
                " added successfully";
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PatchMapping("/updatePassword")
    @ResponseBody
    public ResponseEntity<CustomerDto> changePassword(@RequestBody CustomerDto customerDto) {
        Customer customer = new Customer(CustomPasswordEncoder.hashPassword(customerDto.getPassword()));
        Customer returnedCustomer = customerService.changePassword(customer);
        CustomerDto returnedCustomerDto = modelMapper.map(returnedCustomer, CustomerDto.class);
        return ResponseEntity.ok(returnedCustomerDto);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/payment")
    public ResponseEntity<String> payment(@RequestBody CustomerDto customerDto) {
        Order order = orderService.getById(customerDto.getOrderId()[0]);
        if(order == null)
            throw new NotFoundOrderException();
        Set<Offer> offerSet = order.getOffers();
        Iterator<Offer> iter = offerSet.iterator();
        Offer first = iter.next();
        Offer foundedOffer = offerService.getById(first.getId());
        if(foundedOffer == null)
            throw new NotFoundOfferException();
        Customer foundedCustomer = customerService.getById(customerDto.getId());
        if(foundedCustomer == null)
            throw new NotFoundCustomerException();
        String message = customerService.payment(foundedCustomer,foundedOffer,order);
        return ResponseEntity.ok(message);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/rating")
    public ResponseEntity<String> rating(@RequestBody CustomerDto customerDto) {
        Customer customer = mapper.map(customerDto, Customer.class);
        String message = customerService.rating(customer,customerDto.getExpertId());
        return ResponseEntity.ok(message);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/showCustomerBalance")
    private ResponseEntity<String> showCustomerBalance(@RequestBody CustomerDto customerDto) {
        Customer customer = mapper.map(customerDto, Customer.class);
        String balance = customerService.showCustomerBalance(customer.getId());
        return ResponseEntity.ok(balance);
    }


    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping(value = "/gridSearch")
    public ResponseEntity<List<CustomerDto>> gridSearch(@ModelAttribute @RequestBody DynamicSearch dynamicSearch) {
        List<Customer> customerList = customerService.filterCustomer(dynamicSearch);
        List<CustomerDto> dtoList = new ArrayList<>();
        for (Customer s:customerList
        ) {
            dtoList.add(mapper.map(s,CustomerDto.class));
        }
        return ResponseEntity.ok(dtoList);
    }
}