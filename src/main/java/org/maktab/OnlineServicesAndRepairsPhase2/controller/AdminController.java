package org.maktab.OnlineServicesAndRepairsPhase2.controller;

import org.dozer.DozerBeanMapper;
import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.AdminDto;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Admin;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.base.User;
import org.maktab.OnlineServicesAndRepairsPhase2.service.impl.AdminServiceImpl;
import org.maktab.OnlineServicesAndRepairsPhase2.util.CustomPasswordEncoder;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/updatePassword")
    public ResponseEntity<String> updateAdmin(@RequestBody AdminDto adminDto) {
        Admin admin = new Admin(CustomPasswordEncoder.hashPassword(adminDto.getPassword()));
        Admin returnedAdmin = adminService.changeAdminPassword(admin);
        if(returnedAdmin != null) return ResponseEntity.ok("Admin password updated successfully");
        else return ResponseEntity.notFound().build();
    }
}