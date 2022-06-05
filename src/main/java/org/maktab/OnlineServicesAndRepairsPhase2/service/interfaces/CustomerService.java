package org.maktab.OnlineServicesAndRepairsPhase2.service.interfaces;

import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.CustomerDto;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.*;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.base.User;
import org.springframework.http.ResponseEntity;

public interface CustomerService {
    User findByNationalCode(String nationalCode);
    Customer findByEmailAddress(String email);
    Customer save(Customer customer);
    Customer getById(Long id);
    Customer changePassword(Customer customer);
    String payment(Customer customer, Offer offer, Order order);
    String rating(Customer customer,Long expertId);
    String login(Customer customer);
    String showCustomerBalance(Long id);
}
