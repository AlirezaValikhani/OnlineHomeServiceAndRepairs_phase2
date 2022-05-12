package org.maktab.OnlineServicesAndRepairsPhase2.controller;

import org.dozer.DozerBeanMapper;
import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.ExpertDto;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Expert;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Specialty;
import org.maktab.OnlineServicesAndRepairsPhase2.service.impl.ExpertServiceImpl;
import org.maktab.OnlineServicesAndRepairsPhase2.service.impl.SpecialtyServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/expert")
public class ExpertController {
    private final ExpertServiceImpl expertService;
    private final SpecialtyServiceImpl specialtyService;
    private final DozerBeanMapper mapper;
    private final ModelMapper modelMapper;

    public ExpertController(ExpertServiceImpl expertService, SpecialtyServiceImpl specialtyService) {
        this.expertService = expertService;
        this.specialtyService = specialtyService;
        this.mapper = new DozerBeanMapper();
        this.modelMapper = new ModelMapper();
    }

    @PostMapping("/save")
    public ResponseEntity<ExpertDto> save(@RequestBody ExpertDto expertDto) {
        Set<Specialty> servicesSet = new HashSet<>();
        for (Long serviceId : expertDto.getServicesId()) {
            servicesSet.add(specialtyService.getById(serviceId));
        }
        Expert expert = mapper.map(expertDto, Expert.class);
        expert.setServices(servicesSet);
        Expert returnedExpert = expertService.save(expert);
        ExpertDto returnedExpertDto = modelMapper.map(returnedExpert, ExpertDto.class);
        return ResponseEntity.ok(returnedExpertDto);
    }


    @PutMapping("/updatePassword")
    public ResponseEntity<ExpertDto> changePassword(@RequestBody ExpertDto expertDto){
        Expert expert = mapper.map(expertDto, Expert.class);
        Expert returnedExpert = expertService.changePassword(expert);
        ExpertDto returnedExpertDto = modelMapper.map(returnedExpert, ExpertDto.class);
        if (returnedExpertDto != null){
            return ResponseEntity.ok(returnedExpertDto);
        }else return ResponseEntity.notFound().build();
    }

    @PutMapping("/updateUserName")
    public ResponseEntity<ExpertDto> changeUserName(@RequestBody ExpertDto expertDto){
        return null;
    }

    @GetMapping("/waitingApprovalExperts")
    private ResponseEntity<List<ExpertDto>> waitingApprovalExperts(){
        List<Expert> experts = expertService.waitingApprovalExperts();
        List<ExpertDto> expertDtoList = new ArrayList<>();
        for (Expert e:experts) {
            ExpertDto returnedExpertDto = modelMapper.map(e, ExpertDto.class);
            expertDtoList.add(returnedExpertDto);
        }
        return ResponseEntity.ok(expertDtoList);
    }

    @PostMapping("/expertApproval")
    public void expertApproval(@RequestBody ExpertDto expertDto){
        expertService.updateProfessionalStatus(expertDto.getId());
    }
}
