package org.maktab.OnlineServicesAndRepairsPhase2.controller;

import org.dozer.DozerBeanMapper;
import org.maktab.OnlineServicesAndRepairsPhase2.configuration.security.SecurityUtil;
import org.maktab.OnlineServicesAndRepairsPhase2.configuration.security.jwt.PasswordEncoderConfiguration;
import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.CustomerDto;
import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.DynamicSearch;
import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.OnlinePaymentDto;
import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.PaymentDto;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Customer;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Offer;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Order;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.base.User;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.enums.Role;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.enums.UserStatus;
import org.maktab.OnlineServicesAndRepairsPhase2.exceptions.NotFoundCustomerException;
import org.maktab.OnlineServicesAndRepairsPhase2.exceptions.NotFoundOrderException;
import org.maktab.OnlineServicesAndRepairsPhase2.service.impl.CustomerServiceImpl;
import org.maktab.OnlineServicesAndRepairsPhase2.service.impl.OfferServiceImpl;
import org.maktab.OnlineServicesAndRepairsPhase2.service.impl.OrderServiceImpl;
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
    private final PasswordEncoderConfiguration passwordEncoderConfiguration;
    private final OrderServiceImpl orderService;
    private final OfferServiceImpl offerService;
    private final DozerBeanMapper mapper;
    private final ModelMapper modelMapper;

    public CustomerController(CustomerServiceImpl customerService, PasswordEncoderConfiguration passwordEncoderConfiguration, OrderServiceImpl orderService, OfferServiceImpl offerService) {
        this.customerService = customerService;
        this.passwordEncoderConfiguration = passwordEncoderConfiguration;
        this.orderService = orderService;
        this.offerService = offerService;
        this.mapper = new DozerBeanMapper();
        this.modelMapper = new ModelMapper();
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@Valid @RequestBody CustomerDto customerDto) {
        Customer customer = new Customer(customerDto.getFirstName(),customerDto.getLastName(),
                customerDto.getEmailAddress(),customerDto.getNationalCode(),
                passwordEncoderConfiguration.passwordEncoder().encode(customerDto.getPassword()),
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
        Customer customer = new Customer(passwordEncoderConfiguration.passwordEncoder().encode(customerDto.getPassword()));
        Customer returnedCustomer = customerService.changePassword(customer);
        CustomerDto returnedCustomerDto = modelMapper.map(returnedCustomer, CustomerDto.class);
        return ResponseEntity.ok(returnedCustomerDto);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/payment")
    public ResponseEntity<String> payment(@RequestBody PaymentDto paymentDto) {
        Order order = orderService.getById(paymentDto.getOrderId());
        if(order == null)
            throw new NotFoundOrderException();
        Offer offer = offerService.getById(paymentDto.getOfferId());
        User user = SecurityUtil.getCurrentUser();
        Customer foundedCustomer = customerService.getById(user.getId());
        String message = customerService.offlinePayment(foundedCustomer,offer,order);
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


    @PreAuthorize("hasRole('ADMIN')")
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


    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping(value = "/onlinePayment")
    public ResponseEntity<String> onlinePayment(@RequestBody OnlinePaymentDto onlinePaymentDto) {
        String message = customerService.onlinePayment(onlinePaymentDto.getOrderId(),onlinePaymentDto.getOfferId(),
                onlinePaymentDto.getCardNumber(),onlinePaymentDto.getCvv2(),
                onlinePaymentDto.getExpirationDate(),onlinePaymentDto.getSecondPassword());
        return ResponseEntity.ok(message);
    }
}