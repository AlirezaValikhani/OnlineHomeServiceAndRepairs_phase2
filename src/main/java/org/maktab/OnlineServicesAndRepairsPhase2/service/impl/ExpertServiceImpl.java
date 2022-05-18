package org.maktab.OnlineServicesAndRepairsPhase2.service.impl;

import org.dozer.DozerBeanMapper;
import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.ExpertDto;
import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.OrderDto;
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
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class ExpertServiceImpl implements ExpertService {
    private final ExpertRepository expertRepository;
    private final SpecialtyServiceImpl specialtyService;
    private final OrderServiceImpl orderService;
    private final DozerBeanMapper mapper;
    private final ModelMapper modelMapper;
    private final Integer credit = 10;

    public ExpertServiceImpl(ExpertRepository expertRepository, SpecialtyServiceImpl specialtyService, OrderServiceImpl orderService) {
        this.expertRepository = expertRepository;
        this.specialtyService = specialtyService;
        this.orderService = orderService;
        this.mapper = new DozerBeanMapper();
        this.modelMapper = new ModelMapper();
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
    public ResponseEntity<ExpertDto> save(ExpertDto expertDto) throws IOException {
        Set<Specialty> servicesSet = new HashSet<>();
        for (Long serviceId : expertDto.getServicesId()) {
            Specialty foundedSpecialty = specialtyService.getById(serviceId);
            if(foundedSpecialty == null)
                throw new NotFoundSpecialtyException();
            servicesSet.add(foundedSpecialty);
        }
        Expert expert = new Expert(expertDto.getFirstName(),expertDto.getLastName(),expertDto.getEmailAddress(),
                expertDto.getNationalCode(),expertDto.getPassword(),expertDto.getCredit(),expertDto.getBalance(),
                UserStatus.WAITING_APPROVAL,UserType.EXPERT,expertDto.getImage().getBytes(),
                expertDto.getCity(),servicesSet);
        Expert returnedExpert = expertRepository.save(expert);
        ExpertDto returnedExpertDto = modelMapper.map(returnedExpert, ExpertDto.class);
        return new ResponseEntity<>(returnedExpertDto, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ExpertDto> changePassword(ExpertDto expertDto) {
        Expert expert = mapper.map(expertDto, Expert.class);
        Expert foundedExpert = expertRepository.getById(expert.getId());
        if(foundedExpert.getNationalCode() == null)
            throw new NotFoundExpertException();
        foundedExpert.setPassword(expert.getPassword());
        Expert returnedExpert = expertRepository.save(foundedExpert);
        ExpertDto returnedExpertDto = modelMapper.map(returnedExpert, ExpertDto.class);
        if (returnedExpertDto != null) {
            return ResponseEntity.ok(returnedExpertDto);
        } else return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<List<ExpertDto>> waitingApprovalExperts() {
        List<Expert> expertList = expertRepository.waitingApprovalExperts();
        List<ExpertDto> expertDtoList = new ArrayList<>();
        for (Expert e : expertList) {
            ExpertDto returnedExpertDto = modelMapper.map(e, ExpertDto.class);
            expertDtoList.add(returnedExpertDto);
        }
        return ResponseEntity.ok(expertDtoList);
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
    public void startOfWork(OrderDto orderDto) {
        Order order = orderService.getById(orderDto.getId());
        if(order == null)
            throw new NotFoundOrderException();
        order.setOrderStatus(OrderStatus.STARTED);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        if(order.getOrderExecutionDate().equals(timestamp)){
            Long currentTime = timestamp.getTime();
            Long executionTime = order.getOrderExecutionDate().getTime();
            Long result = currentTime - executionTime;
            Expert expert = getById(orderDto.getExpertId());
            if(result == 30) {
                Integer finalCredit = expert.getCredit() - 5;
                expert.setCredit(finalCredit);
            }
        }
    }

    @Override
    public void done(OrderDto orderDto) {
        Order order = orderService.getById(orderDto.getId());
        if(order == null)
            throw new NotFoundOrderException();
        order.setOrderStatus(OrderStatus.DONE);
    }
}
