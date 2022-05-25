package org.maktab.OnlineServicesAndRepairsPhase2.service.interfaces;

import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.CustomerDto;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Customer;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Expert;
import org.springframework.http.ResponseEntity;

public interface CustomerService {
    Customer findByNationalCode(String nationalCode);
    Customer findByEmailAddress(String email);
    Customer save(Customer customer);
    Customer getById(Long id);
    Customer changePassword(Customer customer);
    String payment(Customer customer);
    String rating(Customer customer,Long expertId);
    Customer login(Customer customer);
}
