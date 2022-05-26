package org.maktab.OnlineServicesAndRepairsPhase2.service.impl;

import org.dozer.DozerBeanMapper;
import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.DynamicSearch;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.*;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.enums.OrderStatus;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.enums.UserStatus;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.enums.UserType;
import org.maktab.OnlineServicesAndRepairsPhase2.exceptions.*;
import org.maktab.OnlineServicesAndRepairsPhase2.repository.CustomerRepository;
import org.maktab.OnlineServicesAndRepairsPhase2.service.interfaces.CustomerService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final WalletServiceImpl walletService;
    private final OfferServiceImpl offerService;
    private final ExpertServiceImpl expertService;
    private final OrderServiceImpl orderService;
    private final DozerBeanMapper mapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, WalletServiceImpl walletService, OfferServiceImpl offerService, ExpertServiceImpl expertService, OrderServiceImpl orderService) {
        this.customerRepository = customerRepository;
        this.walletService = walletService;
        this.offerService = offerService;
        this.expertService = expertService;
        this.orderService = orderService;
        this.mapper = new DozerBeanMapper();
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
        if (foundedCustomer != null)
            throw new DuplicateNationalCodeException();
        Customer toSaveCustomer = new Customer(customer.getFirstName(), customer.getLastName(),
                customer.getEmailAddress(), customer.getNationalCode(), customer.getPassword(), customer.getCredit(),
                UserStatus.NEW, UserType.CUSTOMER);
        Customer returnedCustomer = customerRepository.save(toSaveCustomer);
        Wallet wallet = new Wallet(0D);
        wallet.setCustomer(returnedCustomer);
        walletService.save(wallet);
        System.out.println(wallet);
        return returnedCustomer;
    }

    @Override
    public Customer getById(Long id) {
        return customerRepository.getById(id);
    }

    @Override
    public Customer changePassword(Customer customer) {
        Customer foundedCustomer = customerRepository.getById(customer.getId());
        if (foundedCustomer == null)
            throw new NotFoundCustomerException();
        foundedCustomer.setPassword(customer.getPassword());
        return customerRepository.save(foundedCustomer);
    }

    @Override
    public String payment(Customer customer, Offer offer, Order order) {
        /*Order foundedOrder= customer.getOrders().stream().findFirst().get();
        Order order = orderService.getById(foundedOrder.getId());
        if(order == null)
            throw new NotFoundOrderException();
        Set<Offer> offerSet = order.getOffers();
        Iterator<Offer> iter = offerSet.iterator();
        Offer first = iter.next();
        Offer foundedOffer = offerService.getById(first.getId());
        if(foundedOffer == null)
            throw new NotFoundOfferException();
        Customer foundedCustomer = customerRepository.getById(customer.getId());
        if(foundedCustomer == null)
            throw new NotFoundCustomerException();*/
        Wallet wallet = walletService.getById(customer.getWallet().getId());
        if (wallet == null)
            throw new NotFoundWalletException("This wallet doesn't exists!!!");
        Double cost = customer.getWallet().getBalance() - offer.getBidPriceOffer();
        Wallet customerWallet = customer.getWallet();
        customerWallet.setBalance(cost);
        walletService.save(customerWallet);
        customerRepository.save(customer);
        order.setOrderStatus(OrderStatus.PAID);
        Order returnedOrder = orderService.save(order);
        return "Order ID : " + returnedOrder.getId() + " paid successfully";
    }

    @Override
    public String rating(Customer customer, Long expertId) {
        Expert expert = expertService.getById(expertId);
        if (expert == null)
            throw new NotFoundExpertException();
        Integer previousCredit = expert.getCredit();
        expert.setCredit(customer.getCredit() + previousCredit);
        expertService.saveExpertObject(expert);
        return "You gave " + customer.getCredit() + " point to " + expert.getFirstName()
                + expert.getLastName();
    }

    @Override
    public Customer login(Customer customer) {
        Customer foundedCustomer = customerRepository.findByNationalCode(customer.getNationalCode());
        if (foundedCustomer == null)
            throw new NotFoundCustomerException();
        if (!foundedCustomer.getPassword().equals(customer.getPassword()))
            throw new InvalidPasswordException();
        return foundedCustomer;
    }

    @Override
    public String showCustomerBalance(Long id) {
        Customer foundedCustomer = customerRepository.getById(id);
        return "Your balance : " + foundedCustomer.getWallet().getBalance();
    }

    public List<Customer> filterCustomer(DynamicSearch dynamicSearch) {
        Customer customer = mapper.map(dynamicSearch, Customer.class);
        return customerRepository.findAll(userSpecification(customer));
    }

    private Specification<Customer> userSpecification(Customer customer) {
        return (userRoot, query, criteriaBuilder)
                -> {
            CriteriaQuery<Customer> criteriaQuery = criteriaBuilder.createQuery(Customer.class);
            criteriaQuery.select(userRoot);

            List<Predicate> predicates = new ArrayList<>();
            if (customer.getUserType() != null)
                predicates.add(criteriaBuilder.equal(userRoot.get("userType"), customer.getUserType()));
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
