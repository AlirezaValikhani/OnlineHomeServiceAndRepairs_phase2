package org.maktab.OnlineServicesAndRepairsPhase2.controller;

import org.dozer.DozerBeanMapper;
import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.AdminDto;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Admin;
import org.maktab.OnlineServicesAndRepairsPhase2.service.impl.AdminServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminServiceImpl adminService;
    private final DozerBeanMapper mapper;
    private final ModelMapper modelMapper;


    public AdminController(AdminServiceImpl adminService) {
        this.adminService = adminService;
        this.mapper = new DozerBeanMapper();
        this.modelMapper = new ModelMapper();
    }

    @GetMapping("/save")
    public ResponseEntity<AdminDto> save() {
        Admin returnedAdmin = adminService.addAdminByDefault();
        AdminDto adminDto = modelMapper.map(returnedAdmin, AdminDto.class);
        return ResponseEntity.ok(adminDto);
    }

    @PutMapping("/updatePassword")
    public ResponseEntity<AdminDto> updateAdmin(@RequestBody AdminDto adminDto) {
        Admin admin = mapper.map(adminDto, Admin.class);
        Admin returnedAdmin = adminService.changeAdminPassword(admin);
        AdminDto returnedAdminDto = modelMapper.map(returnedAdmin, AdminDto.class);
        if(returnedAdmin == null) return ResponseEntity.ok(returnedAdminDto);
        else return ResponseEntity.notFound().build();
    }
}