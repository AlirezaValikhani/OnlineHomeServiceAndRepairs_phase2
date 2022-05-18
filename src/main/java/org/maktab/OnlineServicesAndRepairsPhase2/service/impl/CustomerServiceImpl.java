package org.maktab.OnlineServicesAndRepairsPhase2.service.impl;

import org.dozer.DozerBeanMapper;
import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.CustomerDto;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Customer;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Order;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.enums.OrderStatus;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.enums.UserStatus;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.enums.UserType;
import org.maktab.OnlineServicesAndRepairsPhase2.exceptions.DuplicateNationalCodeException;
import org.maktab.OnlineServicesAndRepairsPhase2.exceptions.NotFoundCustomerException;
import org.maktab.OnlineServicesAndRepairsPhase2.exceptions.NotFoundOrderException;
import org.maktab.OnlineServicesAndRepairsPhase2.repository.CustomerRepository;
import org.maktab.OnlineServicesAndRepairsPhase2.service.interfaces.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final OrderServiceImpl orderService;
    private final DozerBeanMapper mapper;
    private final ModelMapper modelMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, OrderServiceImpl orderService) {
        this.customerRepository = customerRepository;
        this.orderService = orderService;
        this.mapper = new DozerBeanMapper();
        this.modelMapper = new ModelMapper();
    }

    @Override
    public Customer findByNationalCode(String nationalCode) {
        return customerRepository.findByNationalCode(nationalCode);
    }

    @Override
    public Customer findByEmailAddress(String email) {
        return customerRepository.findByEmailAddress(email);
    }

    @Override
    public ResponseEntity<CustomerDto> save(CustomerDto customerDto) {
        Customer customer = mapper.map(customerDto, Customer.class);
        Customer foundedCustomer = customerRepository.findByNationalCode(customer.getNationalCode());
        if(foundedCustomer != null)
            throw new DuplicateNationalCodeException();
        Customer toSaveCustomer = new Customer(customer.getFirstName(),customer.getLastName(),
                customer.getEmailAddress(),customer.getNationalCode(),customer.getPassword(),customer.getCredit(),
                customer.getBalance(),UserStatus.NEW,UserType.CUSTOMER);
        Customer returnedCustomer = customerRepository.save(toSaveCustomer);
        CustomerDto returnedCustomerDto = modelMapper.map(returnedCustomer, CustomerDto.class);
        return new ResponseEntity<>(returnedCustomerDto, HttpStatus.CREATED);
    }

    @Override
    public Customer getById(Long id) {
        return customerRepository.getById(id);
    }

    @Override
    public ResponseEntity<CustomerDto> changePassword(CustomerDto customerDto) {
        Customer customer = mapper.map(customerDto, Customer.class);
        Customer foundedCustomer = customerRepository.getById(customer.getId());
        if(customer.getNationalCode() == null)
            throw new NotFoundCustomerException();
        Customer returnedCustomer = customerRepository.save(foundedCustomer);
        CustomerDto returnedCustomerDto = modelMapper.map(returnedCustomer, CustomerDto.class);
        return ResponseEntity.ok(returnedCustomerDto);
    }

    @Override
    public ResponseEntity<String> payment(CustomerDto customerDto) {
        Order order = orderService.getById(customerDto.getOrderId()[0]);
        if(order == null)
            throw new NotFoundOrderException();
        Customer customer = customerRepository.getById(customerDto.getId());
        if(customer == null)
            throw new NotFoundCustomerException();
        Double cost = order.getBidPriceOrder() - customer.getBalance();
        customer.setBalance(cost);
        Customer returnedCustomer = customerRepository.save(customer);
        order.setOrderStatus(OrderStatus.PAID);
        Order returnedOrder = orderService.save(order);
        return ResponseEntity.ok("Order ID : " + returnedOrder.getId() + "paid successfully");
    }
}
