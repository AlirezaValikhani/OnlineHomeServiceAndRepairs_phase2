package org.maktab.OnlineServicesAndRepairsPhase2.repository;

import org.maktab.OnlineServicesAndRepairsPhase2.entity.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer,Long> {
    Customer findByNationalCode(String nationalCode);
    Customer findByEmailAddress(String email);
    Customer getById(Long id);
    /*List<Customer> gridSearch(
            String firstName,
            String lastName,
            String email,
            String nationalCode
    );*/
}
