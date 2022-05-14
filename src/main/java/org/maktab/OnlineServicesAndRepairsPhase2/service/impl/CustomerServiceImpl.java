package org.maktab.OnlineServicesAndRepairsPhase2.service.impl;

import org.dozer.DozerBeanMapper;
import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.CustomerDto;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Customer;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Expert;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.enums.UserStatus;
import org.maktab.OnlineServicesAndRepairsPhase2.repository.CustomerRepository;
import org.maktab.OnlineServicesAndRepairsPhase2.service.interfaces.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final DozerBeanMapper mapper;
    private final ModelMapper modelMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
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
        customer.setUserStatus(UserStatus.NEW);
        Customer returnedCustomer = customerRepository.save(customer);
        CustomerDto returnedCustomerDto = modelMapper.map(returnedCustomer, CustomerDto.class);
            return ResponseEntity.ok(returnedCustomerDto);
    }

    @Override
    public Customer getById(Long id) {
        return customerRepository.getById(id);
    }

    @Override
    public ResponseEntity<CustomerDto> changePassword(CustomerDto customerDto) {
        Customer customer = mapper.map(customerDto, Customer.class);
        Customer foundedCustomer = customerRepository.getById(customer.getId());
        Customer returnedCustomer = customerRepository.save(foundedCustomer);
        CustomerDto returnedCustomerDto = modelMapper.map(returnedCustomer, CustomerDto.class);
        return ResponseEntity.ok(returnedCustomerDto);
    }
}
