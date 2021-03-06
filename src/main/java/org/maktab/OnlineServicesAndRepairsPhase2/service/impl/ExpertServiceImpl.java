package org.maktab.OnlineServicesAndRepairsPhase2.service.impl;

import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.DynamicSearch;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Expert;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Order;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Specialty;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.enums.OrderStatus;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.enums.UserStatus;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.enums.Role;
import org.maktab.OnlineServicesAndRepairsPhase2.exceptions.*;
import org.maktab.OnlineServicesAndRepairsPhase2.repository.ExpertRepository;
import org.maktab.OnlineServicesAndRepairsPhase2.service.interfaces.ExpertService;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ExpertServiceImpl implements ExpertService {
    private final ExpertRepository expertRepository;
    private final SpecialtyServiceImpl specialtyService;
    private final OrderServiceImpl orderService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final Integer credit = 10;

    public ExpertServiceImpl(ExpertRepository expertRepository, SpecialtyServiceImpl specialtyService, OrderServiceImpl orderService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.expertRepository = expertRepository;
        this.specialtyService = specialtyService;
        this.orderService = orderService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public Expert findByNationalCode(String userName) {
        return expertRepository.findByNationalCode(userName);
    }

    @Override
    public Expert findByEmailAddress(String email) {
        return expertRepository.findByEmailAddress(email);
    }

    @Override
    public List<Expert> findAcceptedExperts() {
        return expertRepository.findAcceptedExperts();
    }

    @Override
    public Expert findExpertServices(Long expertId) {
        return expertRepository.findExpertServices(expertId);
    }

    @Override
    public Expert save(Expert expert) throws IOException {
        expert.setRole(Role.ROLE_EXPERT);
        expert.setUserStatus(UserStatus.WAITING_APPROVAL);
        return expertRepository.save(expert);
    }

    @Override
    public Expert changePassword(Expert expert) {
        Expert foundedExpert = expertRepository.getById(expert.getId());
        if (foundedExpert.getNationalCode() == null)
            throw new NotFoundExpertException();
        foundedExpert.setPassword(expert.getPassword());
        return expertRepository.save(foundedExpert);
    }

    @Override
    public List<Expert> waitingApprovalExperts() {
        List<Expert> experts = expertRepository.waitingApprovalExperts();
        if(experts.size() == 0)
            throw new NotFoundWaitingApprovalExpert();
        return experts;
    }

    /*@Override
    public List<Expert> waitingApprovalExperts() {
        List<Expert> expertList = expertRepository.waitingApprovalExperts();
        if (expertList.size() == 0)
            throw new NotFoundWaitingApprovalExpert();
        return expertList;
    }*/

    @Override
    public Expert expertApproval(Long id) {
        Expert expert = expertRepository.getById(id);
        if (expert.getNationalCode() == null)
            throw new NotFoundExpertException();
        expert.setUserStatus(UserStatus.ACCEPTED);
        return expertRepository.save(expert);
    }

    @Override
    public Expert getById(Long id) {
        return expertRepository.getById(id);
    }

    @Override
    public String updateExpertStatus(Long id) throws Exception {
        Expert foundedExpert = expertRepository.getById(id);
        if (foundedExpert == null)
            throw new NotFoundExpertException();
        if (foundedExpert.getUserStatus().equals(UserStatus.ACCEPTED))
            throw new AcceptedBeforeException();
        expertRepository.updateExpertStatus(id);
        return "Expert accepted";
    }

    @Override
    public String startOfWork(Order order) {
        Order foundedOrder = orderService.getById(order.getId());
        if (foundedOrder == null)
            throw new NotFoundOrderException();
        foundedOrder.setOrderStatus(OrderStatus.STARTED);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        if (foundedOrder.getOrderExecutionDate().equals(timestamp)) {
            Long currentTime = timestamp.getTime();
            Long executionTime = foundedOrder.getOrderExecutionDate().getTime();
            long result = currentTime - executionTime;
            Expert expert = getById(order.getExpert().getId());
            if (result == 30) {
                Integer finalCredit = expert.getCredit() - 5;
                expert.setCredit(finalCredit);
                saveExpertObject(expert);
            }
        }
        return "Work started";
    }

    @Override
    public String done(Order order) {
        Order foundedOrder = orderService.getById(order.getId());
        if (foundedOrder == null)
            throw new NotFoundOrderException();
        foundedOrder.setOrderStatus(OrderStatus.DONE);
        orderService.save(foundedOrder);
        return "Work done";
    }

    @Override
    public Expert saveExpertObject(Expert expert) {
        return expertRepository.save(expert);
    }

    @Override
    public String showExpertBalance(Long id) {
        Expert foundedExpert = expertRepository.getById(id);
        return "Your balance : " + foundedExpert.getBalance();
    }

    @Override
    public List<Expert> filterExpert(DynamicSearch dynamicSearch){
        Expert expert = new Expert(dynamicSearch.getFirstName(), dynamicSearch.getLastName(),
                dynamicSearch.getEmail(),dynamicSearch.getNationalCode(),null,null,
                dynamicSearch.getCredit(), null,Role.ROLE_EXPERT,null,null,null);

        List<Expert> experts = expertRepository.findAll(userSpecification(expert));
        Specialty specialty;
        if(dynamicSearch.getService() == null || dynamicSearch.getService().isEmpty()) {
            return experts;
        }
        else{
            specialty = specialtyService.findByName(dynamicSearch.getService());
            if(specialty == null) {
                experts.clear();
                return experts;
            }
            else
                return specialty.getExperts().stream().filter(experts::contains).collect(Collectors.toList());
        }
    }

    private Specification<Expert> userSpecification(Expert expert) {
        return (userRoot, query, criteriaBuilder)
                -> {
            CriteriaQuery<Expert> criteriaQuery = criteriaBuilder.createQuery(Expert.class);
            criteriaQuery.select(userRoot);

            List<Predicate> predicates = new ArrayList<>();
            if (expert.getRole() != null)
                predicates.add(criteriaBuilder.equal(userRoot.get("role"), expert.getRole()));
            if (expert.getFirstName() != null && !expert.getFirstName().isEmpty())
                predicates.add(criteriaBuilder.equal(userRoot.get("firstName"), expert.getFirstName()));
            if (expert.getLastName() != null && !expert.getLastName().isEmpty())
                predicates.add(criteriaBuilder.equal(userRoot.get("lastName"), expert.getLastName()));
            if (expert.getNationalCode() != null && !expert.getNationalCode().isEmpty())
                predicates.add(criteriaBuilder.equal(userRoot.get("nationalCode"), expert.getNationalCode()));
            if (expert.getCredit() != null)
                predicates.add(criteriaBuilder.equal(userRoot.get("credit"), expert.getCredit()));
            if (expert.getEmailAddress() != null && !expert.getEmailAddress().isEmpty())
                predicates.add(criteriaBuilder.equal(userRoot.get("emailAddress"), expert.getEmailAddress()));

            criteriaQuery.where(predicates.toArray(new Predicate[0]));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
