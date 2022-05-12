package org.maktab.OnlineServicesAndRepairsPhase2.controller;

import org.dozer.DozerBeanMapper;
import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.ExpertDto;
import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.OfferDto;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Customer;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Expert;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.enums.UserStatus;
import org.maktab.OnlineServicesAndRepairsPhase2.service.impl.ExpertServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/expert")
public class ExpertController {
    private final ExpertServiceImpl expertService;
    private final DozerBeanMapper mapper;
    private final ModelMapper modelMapper;

    public ExpertController(ExpertServiceImpl expertService) {
        this.expertService = expertService;
        this.mapper = new DozerBeanMapper();
        this.modelMapper = new ModelMapper();
    }

    @PostMapping("/save")
    public ResponseEntity<ExpertDto> save(@RequestBody ExpertDto expertDto){
        Expert expert = mapper.map(expertDto, Expert.class);
        Expert returnedExpert = expertService.save(expert);
        ExpertDto returnedExpertDto = modelMapper.map(returnedExpert, ExpertDto.class);
        if (returnedExpertDto != null)
            return ResponseEntity.ok(returnedExpertDto);
        else return ResponseEntity.notFound().build();
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
