package org.maktab.OnlineServicesAndRepairsPhase2.service.impl;

import org.dozer.DozerBeanMapper;
import org.maktab.OnlineServicesAndRepairsPhase2.configuration.security.SecurityUtil;
import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.DynamicSearch;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.*;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.base.User;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.enums.OrderStatus;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.enums.Role;
import org.maktab.OnlineServicesAndRepairsPhase2.exceptions.*;
import org.maktab.OnlineServicesAndRepairsPhase2.repository.CustomerRepository;
import org.maktab.OnlineServicesAndRepairsPhase2.repository.UserRepository;
import org.maktab.OnlineServicesAndRepairsPhase2.service.interfaces.CustomerService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final OfferServiceImpl offerService;
    private final ExpertServiceImpl expertService;
    private final OrderServiceImpl orderService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final DozerBeanMapper mapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, UserRepository userRepository, OfferServiceImpl offerService, ExpertServiceImpl expertService, OrderServiceImpl orderService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
        this.offerService = offerService;
        this.expertService = expertService;
        this.orderService = orderService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.mapper = new DozerBeanMapper();
    }

    @Override
    public User findByNationalCode(String nationalCode) {
        return userRepository.findByNationalCodeReturnObject(nationalCode);
    }

    @Override
    public Customer findByEmailAddress(String email) {
        return customerRepository.findByEmailAddress(email);
    }

    @Override
    public Customer save(Customer customer) {
        User foundedUser = userRepository.findByNationalCodeReturnObject(customer.getNationalCode());
        if (foundedUser != null)
            throw new DuplicateNationalCodeException();
        User user = userRepository.findByEmailAddress(customer.getEmailAddress());
        if (user != null)
            throw new DuplicateEmailException();
        return customerRepository.save(customer);
    }

    @Override
    public Customer getById(Long id) {
        return customerRepository.getById(id);
    }

    @Override
    public Customer changePassword(Customer customer) {
        User user = SecurityUtil.getCurrentUser();
        Customer foundedCustomer = customerRepository.getById(user.getId());
        if (foundedCustomer == null)
            throw new NotFoundCustomerException();
        foundedCustomer.setPassword(customer.getPassword());
        return customerRepository.save(foundedCustomer);
    }

    @Override
    public String offlinePayment(Customer customer, Offer offer, Order order) {
        if (customer.getBalance() >= offer.getBidPriceOffer()) {
            Double cost = customer.getBalance() - offer.getBidPriceOffer();
            customer.setBalance(cost);
            customerRepository.save(customer);
            Double expertFee = cost * 0.7;
            Expert expert = order.getExpert();
            expert.setBalance(expertFee);
            expertService.saveExpertObject(expert);
            order.setOrderStatus(OrderStatus.PAID);
            Order returnedOrder = orderService.save(order);
            return "Order ID : " + returnedOrder.getId() + " paid successfully";
        } else throw new NotEnoughBalanceException();
    }

    @Override
    public String onlinePayment(Long offerId,Long orderId, String cardNumber, String cvv2, Timestamp expirationDate, String secondPassword) {
        Order order = orderService.getById(orderId);
        if (!order.getOrderStatus().equals(OrderStatus.DONE))
            throw new WrongOrderException();
        Offer offer = offerService.getById(offerId);
        Double priceOrder = offer.getBidPriceOffer();
        Expert expert = expertService.getById(order.getExpert().getId());
        Double amount = expert.getBalance() + priceOrder;
        expert.setBalance(amount);
        expertService.saveExpertObject(expert);
        return "The payment was successful for the expert";
    }

    @Override
    public String rating(Customer customer, Long expertId) {
        Expert expert = expertService.getById(expertId);
        if (expert == null)
            throw new NotFoundExpertException();
        Integer previousCredit = expert.getCredit();
        expert.setCredit(customer.getCredit() + previousCredit);
        expertService.saveExpertObject(expert);
        return "You gave " + customer.getCredit() + " point to " + expert.getFirstName() + " "
                + expert.getLastName();
    }

    @Override
    public String showCustomerBalance(Long id) {
        Customer foundedCustomer = customerRepository.getById(id);
        return "Your balance : " + foundedCustomer.getBalance();
    }

    /*public List<Customer> filterCustomer(DynamicSearch dynamicSearch) {
        Customer customer = mapper.map(dynamicSearch, Customer.class);
        return customerRepository.findAll(userSpecification(customer));
    }*/


    public List<Customer> filterCustomer(DynamicSearch dynamicSearch) {
        Customer customer = new Customer(dynamicSearch.getFirstName(), dynamicSearch.getLastName(),
                dynamicSearch.getEmail(), dynamicSearch.getNationalCode(), null, null,
                dynamicSearch.getCredit(), null, Role.ROLE_CUSTOMER);
        return customerRepository.findAll(userSpecification(customer));
    }

    private Specification<Customer> userSpecification(Customer customer) {
        return (userRoot, query, criteriaBuilder)
                -> {
            CriteriaQuery<Customer> criteriaQuery = criteriaBuilder.createQuery(Customer.class);
            criteriaQuery.select(userRoot);

            List<Predicate> predicates = new ArrayList<>();
            if (customer.getRole() != null)
                predicates.add(criteriaBuilder.equal(userRoot.get("role"), customer.getRole()));
            if (customer.getFirstName() != null && !customer.getFirstName().isEmpty())
                predicates.add(criteriaBuilder.equal(userRoot.get("firstName"), customer.getFirstName()));
            if (customer.getLastName() != null && !customer.getLastName().isEmpty())
                predicates.add(criteriaBuilder.equal(userRoot.get("lastName"), customer.getLastName()));
            if (customer.getNationalCode() != null && !customer.getNationalCode().isEmpty())
                predicates.add(criteriaBuilder.equal(userRoot.get("nationalCode"), customer.getNationalCode()));
            if (customer.getEmailAddress() != null && !customer.getEmailAddress().isEmpty())
                predicates.add(criteriaBuilder.equal(userRoot.get("emailAddress"), customer.getEmailAddress()));

            criteriaQuery.where(predicates.toArray(new Predicate[0]));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }


}
