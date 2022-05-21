package org.maktab.OnlineServicesAndRepairsPhase2.service.impl;

import org.dozer.DozerBeanMapper;
import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.CustomerDto;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Customer;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Expert;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Order;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.enums.OrderStatus;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.enums.UserStatus;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.enums.UserType;
import org.maktab.OnlineServicesAndRepairsPhase2.exceptions.DuplicateNationalCodeException;
import org.maktab.OnlineServicesAndRepairsPhase2.exceptions.NotFoundCustomerException;
import org.maktab.OnlineServicesAndRepairsPhase2.exceptions.NotFoundExpertException;
import org.maktab.OnlineServicesAndRepairsPhase2.exceptions.NotFoundOrderException;
import org.maktab.OnlineServicesAndRepairsPhase2.repository.CustomerRepository;
import org.maktab.OnlineServicesAndRepairsPhase2.service.interfaces.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final ExpertServiceImpl expertService;
    private final OrderServiceImpl orderService;

    public CustomerServiceImpl(CustomerRepository customerRepository, ExpertServiceImpl expertService, OrderServiceImpl orderService) {
        this.customerRepository = customerRepository;
        this.expertService = expertService;
        this.orderService = orderService;
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
        Customer foundedCustomer = customerRepository.findByNationalCode(customer.getNationalCode());
        if(foundedCustomer != null)
            throw new DuplicateNationalCodeException();
        Customer toSaveCustomer = new Customer(customer.getFirstName(),customer.getLastName(),
                customer.getEmailAddress(),customer.getNationalCode(),customer.getPassword(),customer.getCredit(),
                customer.getBalance(),UserStatus.NEW,UserType.CUSTOMER);
        return customerRepository.save(toSaveCustomer);
    }

    @Override
    public Customer getById(Long id) {
        return customerRepository.getById(id);
    }

    @Override
    public Customer changePassword(Customer customer) {
        Customer foundedCustomer = customerRepository.getById(customer.getId());
        if(foundedCustomer == null)
            throw new NotFoundCustomerException();
        foundedCustomer.setPassword(customer.getPassword());
        return customerRepository.save(foundedCustomer);
    }

    @Override
    public String payment(Customer customer) {
        Order foundedOrder= customer.getOrders().stream().findFirst().get();
        Order order = orderService.getById(foundedOrder.getId());
        if(order == null)
            throw new NotFoundOrderException();
        Customer foundedCustomer = customerRepository.getById(customer.getId());
        if(foundedCustomer == null)
            throw new NotFoundCustomerException();
        Double cost = order.getBidPriceOrder() - foundedCustomer.getBalance();
        foundedCustomer.setBalance(cost);
        customerRepository.save(foundedCustomer);
        order.setOrderStatus(OrderStatus.PAID);
        Order returnedOrder = orderService.save(order);
        return "Order ID : " + returnedOrder.getId() + "paid successfully";
    }

    @Override
    public String rating(Customer customer,Long expertId) {
        Expert expert = expertService.getById(expertId);
        if(expert == null)
            throw new NotFoundExpertException();
        Integer previousCredit = expert.getCredit();
        expert.setCredit(customer.getCredit() + previousCredit);
        expertService.saveExpertObject(expert);
        return "You gave " + customer.getCredit() + " point to " + expert.getFirstName()
                + expert.getLastName();
    }
}
