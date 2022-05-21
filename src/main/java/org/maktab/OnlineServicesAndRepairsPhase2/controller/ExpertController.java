package org.maktab.OnlineServicesAndRepairsPhase2.controller;

import org.dozer.DozerBeanMapper;
import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.ExpertDto;
import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.OrderDto;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Expert;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Order;
import org.maktab.OnlineServicesAndRepairsPhase2.service.impl.ExpertServiceImpl;
import org.maktab.OnlineServicesAndRepairsPhase2.service.impl.SpecialtyServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
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

    @PostMapping(path = "/save" , consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<ExpertDto> save(@Valid @ModelAttribute ExpertDto expertDto) throws IOException {
        Expert expert = mapper.map(expertDto, Expert.class);
        Expert returnedExpert = expertService.save(expert);
        ExpertDto returnedExpertDto = modelMapper.map(returnedExpert, ExpertDto.class);
        return new ResponseEntity<>(returnedExpertDto, HttpStatus.CREATED);
    }


    @PutMapping("/updatePassword")
    public ResponseEntity<ExpertDto> changePassword(@RequestBody ExpertDto expertDto) {
        Expert expert = mapper.map(expertDto, Expert.class);
        Expert returnedExpert = expertService.changePassword(expert);
        ExpertDto returnedExpertDto = modelMapper.map(returnedExpert, ExpertDto.class);
        if (returnedExpertDto != null) {
            return ResponseEntity.ok(returnedExpertDto);
        } else return ResponseEntity.notFound().build();
    }

    @PutMapping("/updateUserName")
    public ResponseEntity<ExpertDto> changeUserName(@RequestBody ExpertDto expertDto) {
        return null;
    }

    @GetMapping("/waitingApprovalExperts")
    private ResponseEntity<List<ExpertDto>> waitingApprovalExperts() {
        List<Expert> expertList = expertService.waitingApprovalExperts();
        List<ExpertDto> expertDtoList = new ArrayList<>();
        for (Expert e : expertList) {
            ExpertDto returnedExpertDto = modelMapper.map(e, ExpertDto.class);
            expertDtoList.add(returnedExpertDto);
        }
        return ResponseEntity.ok(expertDtoList);

    }

    @PostMapping("/expertApproval")
    public void expertApproval(@RequestBody ExpertDto expertDto) {
        expertService.updateProfessionalStatus(expertDto.getId());
    }

    @PostMapping("/startOfWork")
    public ResponseEntity<String> startOfWork(@RequestBody OrderDto orderDto) {
        Order order = mapper.map(orderDto, Order.class);
        String message = expertService.startOfWork(order);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/done")
    public ResponseEntity<String> done(@RequestBody OrderDto orderDto) {
        Order order = mapper.map(orderDto, Order.class);
        String message = expertService.done(order);
        return ResponseEntity.ok(message);
    }
}








/*
{
        "firstName": "mmd",
        "lastName": "mmdi",
        "emailAddress": "a@email.com",
        "nationalCode": "1234",
        "password": "12345678",
        "registrationDate": "2022-05-20T07:34:16.171+00:00",
        "credit": 10,
        "balance": 100000,
        "city": "Zanjan",
        "image": null,
        "servicesId": 1
        }*/
