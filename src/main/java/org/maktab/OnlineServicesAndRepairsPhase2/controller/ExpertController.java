package org.maktab.OnlineServicesAndRepairsPhase2.controller;

import org.dozer.DozerBeanMapper;
import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.ExpertDto;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Customer;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Expert;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.enums.UserStatus;
import org.maktab.OnlineServicesAndRepairsPhase2.service.impl.ExpertServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expert")
public class ExpertController {
    private final ExpertServiceImpl expertService;

    public ExpertController(ExpertServiceImpl expertService) {
        this.expertService = expertService;
    }

    @PostMapping("/save")
    public ResponseEntity<Expert> save(@RequestBody ExpertDto expertDto){
        DozerBeanMapper mapper = new DozerBeanMapper();
        Expert expert = mapper.map(expertDto, Expert.class);
        Expert returnedExpert = expertService.save(expert);
        if (returnedExpert != null)
            return ResponseEntity.ok(returnedExpert);
        else return ResponseEntity.notFound().build();
    }

    @PutMapping("/updatePassword")
    public ResponseEntity<Expert> changePassword(@RequestBody ExpertDto expertDto){
        DozerBeanMapper mapper = new DozerBeanMapper();
        Expert expert = mapper.map(expertDto, Expert.class);
        Expert returnedExpert = expertService.changePassword(expert);
        if (returnedExpert != null){
            return ResponseEntity.ok(returnedExpert);
        }else return ResponseEntity.notFound().build();
    }

    @PutMapping("/updateUserName")
    public ResponseEntity<Expert> changeUserName(@RequestBody ExpertDto expertDto){
        return null;
    }

    @GetMapping("/waitingApprovalExperts")
    private ResponseEntity<List<Expert>> waitingApprovalExperts(){
        return ResponseEntity.ok(expertService.waitingApprovalExperts());
    }

    @PostMapping("/expertApproval")
    public ResponseEntity<Expert> expertApproval(@RequestBody ExpertDto expertDto){
        DozerBeanMapper mapper = new DozerBeanMapper();
        Expert expert = mapper.map(expertDto, Expert.class);
        Expert returnedExpert = expertService.getById(expert.getId());
        returnedExpert.setUserStatus(UserStatus.ACCEPTED);
        expertService.save(returnedExpert);
            return ResponseEntity.ok(returnedExpert);
    }
}
