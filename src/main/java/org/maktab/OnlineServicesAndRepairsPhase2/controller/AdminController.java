package org.maktab.OnlineServicesAndRepairsPhase2.controller;

import org.dozer.DozerBeanMapper;
import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.AdminDto;
import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.CategoryDto;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Admin;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Category;
import org.maktab.OnlineServicesAndRepairsPhase2.service.impl.AdminServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminServiceImpl adminService;


    public AdminController(AdminServiceImpl adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/save")
    public ResponseEntity<AdminDto> save() {
        return ResponseEntity.ok(adminService.addAdminByDefault());
    }

    @PutMapping("/updatePassword")
    public ResponseEntity<AdminDto> updateAdmin(@RequestBody AdminDto adminDto) {
        return adminService.changeAdminPassword(adminDto);
    }
}