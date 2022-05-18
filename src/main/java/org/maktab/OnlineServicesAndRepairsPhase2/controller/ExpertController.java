package org.maktab.OnlineServicesAndRepairsPhase2.controller;

import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.ExpertDto;
import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.OrderDto;
import org.maktab.OnlineServicesAndRepairsPhase2.service.impl.ExpertServiceImpl;
import org.maktab.OnlineServicesAndRepairsPhase2.service.impl.SpecialtyServiceImpl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/expert")
public class ExpertController {
    private final ExpertServiceImpl expertService;

    public ExpertController(ExpertServiceImpl expertService) {
        this.expertService = expertService;
    }

    @PostMapping(path = "/save" , consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<ExpertDto> save(@Valid @ModelAttribute ExpertDto expertDto) throws IOException {
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

    @PostMapping("/startOfWork")
    public ResponseEntity<String> startOfWork(@RequestBody OrderDto orderDto) {
        return expertService.startOfWork(orderDto);
    }

    @PostMapping("/done")
    public ResponseEntity<String> done(@RequestBody OrderDto orderDto) {
        return expertService.done(orderDto);
    }
}
