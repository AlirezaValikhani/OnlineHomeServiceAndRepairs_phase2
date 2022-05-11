package org.maktab.OnlineServicesAndRepairsPhase2.service.interfaces;

import org.maktab.OnlineServicesAndRepairsPhase2.entity.Customer;

public interface CustomerService {
    Customer findByNationalCode(String nationalCode);
    Customer findByEmailAddress(String email);
    Customer save(Customer customer);
    Customer getById(Long id);
}
