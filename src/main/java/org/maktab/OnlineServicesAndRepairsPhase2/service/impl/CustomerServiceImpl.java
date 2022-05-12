package org.maktab.OnlineServicesAndRepairsPhase2.service.impl;

import org.maktab.OnlineServicesAndRepairsPhase2.entity.Customer;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Expert;
import org.maktab.OnlineServicesAndRepairsPhase2.repository.CustomerRepository;
import org.maktab.OnlineServicesAndRepairsPhase2.service.interfaces.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
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
    public Customer save(Customer customer) {
        if(customerRepository.findByNationalCode(customer.getNationalCode()) != null){
            return null;
        }else
        return customerRepository.save(customer);
    }

    @Override
    public Customer getById(Long id) {
        return customerRepository.getById(id);
    }

    @Override
    public Customer changePassword(Customer customer) {
        Customer returnedCustomer = customerRepository.getById(customer.getId());
        returnedCustomer.setPassword(customer.getPassword());
        return customerRepository.save(returnedCustomer);
    }
}
