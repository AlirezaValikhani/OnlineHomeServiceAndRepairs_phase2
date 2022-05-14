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

    public ExpertController(ExpertServiceImpl expertService, SpecialtyServiceImpl specialtyService) {
        this.expertService = expertService;
        this.specialtyService = specialtyService;
    }

    @PostMapping("/save")
    public ResponseEntity<ExpertDto> save(@RequestBody ExpertDto expertDto) {
        return expertService.save(expertDto);
    }


    @PutMapping("/updatePassword")
    public ResponseEntity<ExpertDto> changePassword(@RequestBody ExpertDto expertDto) {
        return expertService.changePassword(expertDto);
    }

    @PutMapping("/updateUserName")
    public ResponseEntity<ExpertDto> changeUserName(@RequestBody ExpertDto expertDto) {
        return null;
    }

    @GetMapping("/waitingApprovalExperts")
    private ResponseEntity<List<ExpertDto>> waitingApprovalExperts() {
        return expertService.waitingApprovalExperts();
    }

    @PostMapping("/expertApproval")
    public void expertApproval(@RequestBody ExpertDto expertDto) {
        expertService.updateProfessionalStatus(expertDto.getId());
    }
}
