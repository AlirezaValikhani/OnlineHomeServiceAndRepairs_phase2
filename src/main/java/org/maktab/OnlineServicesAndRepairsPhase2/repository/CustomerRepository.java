package org.maktab.OnlineServicesAndRepairsPhase2.repository;

import lombok.NonNull;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Customer;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {
    Customer findByNationalCode(String nationalCode);

    Customer findByEmailAddress(String email);

    Customer getById(Long id);

    @NonNull
    List<Customer> findAll(Specification<Customer> specification);

    @Query("select c.balance from Customer c where c.id = :id")
    Double getCustomerWallet(@Param("id") Long id);
}
