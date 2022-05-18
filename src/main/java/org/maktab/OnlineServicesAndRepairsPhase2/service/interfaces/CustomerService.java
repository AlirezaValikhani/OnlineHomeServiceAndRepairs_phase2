package org.maktab.OnlineServicesAndRepairsPhase2.service.interfaces;

import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.CustomerDto;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Customer;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Expert;
import org.springframework.http.ResponseEntity;

public interface CustomerService {
    Customer findByNationalCode(String nationalCode);
    Customer findByEmailAddress(String email);
    ResponseEntity<CustomerDto> save(CustomerDto customerDto);
    Customer getById(Long id);
    ResponseEntity<CustomerDto> changePassword(CustomerDto customerDto);
    void payment();
}
