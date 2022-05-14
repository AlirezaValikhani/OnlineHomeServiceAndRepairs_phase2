package org.maktab.OnlineServicesAndRepairsPhase2.service.impl;

import org.dozer.DozerBeanMapper;
import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.ExpertDto;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Expert;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Specialty;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.enums.UserStatus;
import org.maktab.OnlineServicesAndRepairsPhase2.repository.ExpertRepository;
import org.maktab.OnlineServicesAndRepairsPhase2.service.interfaces.ExpertService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class ExpertServiceImpl implements ExpertService {
    private final ExpertRepository expertRepository;
    private final SpecialtyServiceImpl specialtyService;
    private final DozerBeanMapper mapper;
    private final ModelMapper modelMapper;
    private Integer credit = 10;

    public ExpertServiceImpl(ExpertRepository expertRepository, SpecialtyServiceImpl specialtyService) {
        this.expertRepository = expertRepository;
        this.specialtyService = specialtyService;
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
    public ResponseEntity<ExpertDto> save(ExpertDto expertDto) {
        Set<Specialty> servicesSet = new HashSet<>();
        for (Long serviceId : expertDto.getServicesId()) {
            servicesSet.add(specialtyService.getById(serviceId));
        }
        Expert expert = mapper.map(expertDto, Expert.class);
        expert.setServices(servicesSet);
        Timestamp registrationDate = new Timestamp(System.currentTimeMillis());
        expert.setRegistrationDate(registrationDate);
        expert.setUserStatus(UserStatus.WAITING_APPROVAL);
        Expert returnedExpert = expertRepository.save(expert);
        ExpertDto returnedExpertDto = modelMapper.map(returnedExpert, ExpertDto.class);
        return ResponseEntity.ok(returnedExpertDto);
    }

    @Override
    public ResponseEntity<ExpertDto> changePassword(ExpertDto expertDto) {
        Expert expert = mapper.map(expertDto, Expert.class);
        Expert foundedExpert = expertRepository.getById(expert.getId());
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


    /*public byte[] getImage(byte[] image) {
            try {
                BufferedImage bufferedImage= ImageIO.read(image);
                ByteArrayOutputStream byteOutStream=new ByteArrayOutputStream();
                ImageIO.write(bufferedImage, "png", byteOutStream);
                return byteOutStream.toByteArray();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        return null;
    }*/
}
