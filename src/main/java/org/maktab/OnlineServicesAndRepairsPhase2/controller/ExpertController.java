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

    public ExpertController(ExpertServiceImpl expertService) {
        this.expertService = expertService;
    }

    @PostMapping("/save")
    public ResponseEntity<ExpertDto> save(@RequestBody ExpertDto expertDto){
        DozerBeanMapper mapper = new DozerBeanMapper();
        Expert expert = mapper.map(expertDto, Expert.class);
        Expert returnedExpert = expertService.save(expert);
        ModelMapper modelMapper = new ModelMapper();
        ExpertDto returnedExpertDto = modelMapper.map(returnedExpert, ExpertDto.class);
        if (returnedExpertDto != null)
            return ResponseEntity.ok(returnedExpertDto);
        else return ResponseEntity.notFound().build();
    }

    @PutMapping("/updatePassword")
    public ResponseEntity<ExpertDto> changePassword(@RequestBody ExpertDto expertDto){
        DozerBeanMapper mapper = new DozerBeanMapper();
        Expert expert = mapper.map(expertDto, Expert.class);
        Expert returnedExpert = expertService.changePassword(expert);
        ModelMapper modelMapper = new ModelMapper();
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
        ModelMapper modelMapper = new ModelMapper();
        List<ExpertDto> expertDtoList = new ArrayList<>();
        for (Expert e:experts) {
            ExpertDto returnedExpertDto = modelMapper.map(e, ExpertDto.class);
            expertDtoList.add(returnedExpertDto);
        }
        return ResponseEntity.ok(expertDtoList);
    }

    @PostMapping("/expertApproval")
    public ResponseEntity<ExpertDto> expertApproval(@RequestBody ExpertDto expertDto){
        Expert returnedExpert = expertService.getById(expertDto.getId());
        returnedExpert.setUserStatus(UserStatus.ACCEPTED);
        System.out.println(returnedExpert);
        ModelMapper modelMapper = new ModelMapper();
        ExpertDto returnedExpertDto = modelMapper.map(returnedExpert, ExpertDto.class);
            return ResponseEntity.ok(returnedExpertDto);
    }
}
