package org.maktab.OnlineServicesAndRepairsPhase2.service.impl;

import org.maktab.OnlineServicesAndRepairsPhase2.controller.ExpertController;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Expert;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Order;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Specialty;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.enums.OrderStatus;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.enums.UserStatus;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.enums.UserType;
import org.maktab.OnlineServicesAndRepairsPhase2.exceptions.NotFoundExpertException;
import org.maktab.OnlineServicesAndRepairsPhase2.exceptions.NotFoundOrderException;
import org.maktab.OnlineServicesAndRepairsPhase2.exceptions.NotFoundSpecialtyException;
import org.maktab.OnlineServicesAndRepairsPhase2.repository.ExpertRepository;
import org.maktab.OnlineServicesAndRepairsPhase2.service.interfaces.ExpertService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class ExpertServiceImpl implements ExpertService {
    private final ExpertRepository expertRepository;
    private final SpecialtyServiceImpl specialtyService;
    private final OrderServiceImpl orderService;
    private final ExpertController expertController;
    private final Integer credit = 10;

    public ExpertServiceImpl(ExpertRepository expertRepository, SpecialtyServiceImpl specialtyService, OrderServiceImpl orderService, ExpertController expertController) {
        this.expertRepository = expertRepository;
        this.specialtyService = specialtyService;
        this.orderService = orderService;
        this.expertController = expertController;
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
        Set<Specialty> servicesSet = new HashSet<>();
        for (Specialty specialty : expert.getServices()) {
            Specialty foundedSpecialty = specialtyService.getById(specialty.getId());
            if(foundedSpecialty == null)
                throw new NotFoundSpecialtyException();
            servicesSet.add(foundedSpecialty);
        }
        expert.setServices(servicesSet);
        expert.setUserType(UserType.EXPERT);
        expert.setUserStatus(UserStatus.WAITING_APPROVAL);
        return expertRepository.save(expert);
    }

    @Override
    public Expert changePassword(Expert expert) {
        Expert foundedExpert = expertRepository.getById(expert.getId());
        if(foundedExpert.getNationalCode() == null)
            throw new NotFoundExpertException();
        foundedExpert.setPassword(expert.getPassword());
        return expertRepository.save(foundedExpert);
    }

    @Override
    public List<Expert> waitingApprovalExperts() {
        return expertRepository.waitingApprovalExperts();
    }

    @Override
    public Expert expertApproval(Long id) {
        Expert expert = expertRepository.getById(id);
        if(expert.getNationalCode() == null)
            throw new NotFoundExpertException();
        expert.setUserStatus(UserStatus.ACCEPTED);
        return expert;
    }

    @Override
    public Expert getById(Long id) {
        return expertRepository.getById(id);
    }

    @Override
    public void updateProfessionalStatus(Long id) {
        expertRepository.updateProfessionalStatus(id);
    }

    @Override
    public String startOfWork(Order order) {
        Order foundedOrder = orderService.getById(order.getId());
        if(foundedOrder == null)
            throw new NotFoundOrderException();
        foundedOrder.setOrderStatus(OrderStatus.STARTED);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        if(foundedOrder.getOrderExecutionDate().equals(timestamp)){
            Long currentTime = timestamp.getTime();
            Long executionTime = foundedOrder.getOrderExecutionDate().getTime();
            long result = currentTime - executionTime;
            Expert expert = getById(order.getExpert().getId());
            if(result == 30) {
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
        if(foundedOrder == null)
            throw new NotFoundOrderException();
        order.setOrderStatus(OrderStatus.DONE);
        orderService.save(order);
        return "Work done";
    }

    @Override
    public Expert saveExpertObject(Expert expert) {
        return expertRepository.save(expert);
    }
}
