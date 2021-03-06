package org.maktab.OnlineServicesAndRepairsPhase2.controller;

import org.dozer.DozerBeanMapper;
import org.maktab.OnlineServicesAndRepairsPhase2.configuration.security.SecurityUtil;
import org.maktab.OnlineServicesAndRepairsPhase2.configuration.security.jwt.PasswordEncoderConfiguration;
import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.DynamicSearch;
import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.ExpertDto;
import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.OrderDto;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Expert;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Order;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Specialty;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.base.User;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.enums.UserStatus;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.enums.Role;
import org.maktab.OnlineServicesAndRepairsPhase2.service.impl.ExpertServiceImpl;
import org.maktab.OnlineServicesAndRepairsPhase2.service.impl.SpecialtyServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/expert")
public class ExpertController {
    private final SpecialtyServiceImpl specialtyService;
    private final PasswordEncoderConfiguration passwordEncoderConfiguration;
    private final ExpertServiceImpl expertService;
    private final DozerBeanMapper mapper;
    private final ModelMapper modelMapper;

    public ExpertController(SpecialtyServiceImpl specialtyService, PasswordEncoderConfiguration passwordEncoderConfiguration, ExpertServiceImpl expertService) {
        this.specialtyService = specialtyService;
        this.passwordEncoderConfiguration = passwordEncoderConfiguration;
        this.expertService = expertService;
        this.mapper = new DozerBeanMapper();
        this.modelMapper = new ModelMapper();
    }

    @PostMapping(path = "/save" , consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<String> save(@Valid @ModelAttribute ExpertDto expertDto) throws IOException {
        Set<Specialty> specialties = new HashSet<>();
        for (Long sId : expertDto.getServicesId()) {
            Specialty foundedSpecialty = specialtyService.getById(sId);
            specialties.add(foundedSpecialty);
        }
        Expert expert = new Expert(expertDto.getFirstName(),expertDto.getLastName(),expertDto.getEmailAddress(),
                expertDto.getNationalCode(), passwordEncoderConfiguration.passwordEncoder().encode(expertDto.getPassword()),expertDto.getBalance(),expertDto.getCredit(), UserStatus.WAITING_APPROVAL,
                Role.ROLE_EXPERT,null,expertDto.getCity(),specialties);
        expert.setImage(expertDto.getImage().getBytes());
        expertService.save(expert);
        return new ResponseEntity<>("Expert saved ",HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('EXPERT')")
    @PutMapping("/updatePassword")
    public ResponseEntity<ExpertDto> changePassword(@RequestBody ExpertDto expertDto) {
        Expert expert = mapper.map(expertDto, Expert.class);
        Expert returnedExpert = expertService.changePassword(expert);
        ExpertDto returnedExpertDto = modelMapper.map(returnedExpert, ExpertDto.class);
        if (returnedExpertDto != null) {
            return ResponseEntity.ok(returnedExpertDto);
        } else return ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasRole('EXPERT')")
    @PutMapping("/updateUserName")
    public ResponseEntity<ExpertDto> changeUserName(@RequestBody ExpertDto expertDto) {
        return null;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/waitingApprovalExperts")
    public ResponseEntity<List<ExpertDto>> waitingApprovalExperts() {
        List<Expert> expertList = expertService.waitingApprovalExperts();
        List<ExpertDto> expertDtoList = new ArrayList<>();
        for (Expert e : expertList) {
            ExpertDto returnedExpertDto = modelMapper.map(e, ExpertDto.class);
            expertDtoList.add(returnedExpertDto);
        }
        return ResponseEntity.ok(expertDtoList);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/expertApproval")
    public ResponseEntity<String> expertApproval(@RequestBody ExpertDto expertDto) throws Exception {
        Expert returnedExpert = expertService.expertApproval(expertDto.getId());
        if(returnedExpert != null)
            return ResponseEntity.ok("Expert accepted");
        else return null;
    }

    @PreAuthorize("hasRole('EXPERT')")
    @PostMapping("/startOfWork")
    public ResponseEntity<String> startOfWork(@RequestBody OrderDto orderDto) {
        Order order = mapper.map(orderDto, Order.class);
        String message = expertService.startOfWork(order);
        return ResponseEntity.ok(message);
    }

    @PreAuthorize("hasRole('EXPERT')")
    @PostMapping("/done")
    public ResponseEntity<String> done(@RequestBody OrderDto orderDto) {
        Order order = mapper.map(orderDto, Order.class);
        String message = expertService.done(order);
        return ResponseEntity.ok(message);
    }

    @PreAuthorize("hasRole('EXPERT')")
    @GetMapping("/showExpertBalance")
    public ResponseEntity<String> showExpertBalance() {
        User user = SecurityUtil.getCurrentUser();
        String balance = expertService.showExpertBalance(user.getId());
        return ResponseEntity.ok(balance);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/gridSearch")
    public ResponseEntity<List<ExpertDto>> gridSearch(@ModelAttribute @RequestBody DynamicSearch dynamicSearch) {
        List<Expert> expertList = expertService.filterExpert(dynamicSearch);
        List<ExpertDto> dtoList = new ArrayList<>();
        for (Expert e:expertList) {
            ExpertDto returnedExpertDto = modelMapper.map(e, ExpertDto.class);
            dtoList.add(returnedExpertDto);
        }
        return ResponseEntity.ok(dtoList);
    }
}