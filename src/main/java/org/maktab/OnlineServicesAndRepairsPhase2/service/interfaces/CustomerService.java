package org.maktab.OnlineServicesAndRepairsPhase2.service.interfaces;

import org.maktab.OnlineServicesAndRepairsPhase2.entity.*;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.base.User;

import java.sql.Timestamp;

public interface CustomerService {
    User findByNationalCode(String nationalCode);
    Customer findByEmailAddress(String email);
    Customer save(Customer customer);
    Customer getById(Long id);
    Customer changePassword(Customer customer);
    String offlinePayment(Customer customer, Offer offer, Order order);
    String onlinePayment(Long offerId,Long orderId,String cardNumber, String Cvv2, Timestamp expirationDate, String secondPassword);
    String rating(Customer customer,Long expertId);
    String showCustomerBalance(Long id);
}
