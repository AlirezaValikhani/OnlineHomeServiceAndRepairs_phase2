package org.maktab.OnlineServicesAndRepairsPhase2.repository;

import org.maktab.OnlineServicesAndRepairsPhase2.entity.Customer;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.base.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.Predicate;

public interface CustomerRepository extends CrudRepository<Customer,Long> {
    Customer findByNationalCode(String nationalCode);
    Customer findByEmailAddress(String email);
    Customer getById(Long id);
}
