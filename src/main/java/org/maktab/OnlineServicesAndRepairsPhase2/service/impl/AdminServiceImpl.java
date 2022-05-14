package org.maktab.OnlineServicesAndRepairsPhase2.service.impl;

import org.dozer.DozerBeanMapper;
import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.AdminDto;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Admin;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.base.User;
import org.maktab.OnlineServicesAndRepairsPhase2.repository.AdminRepository;
import org.maktab.OnlineServicesAndRepairsPhase2.service.interfaces.AdminService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;
    private final DozerBeanMapper mapper;
    private final ModelMapper modelMapper;
    private String firstName,lastName,nationalCode,password;

    public AdminServiceImpl(AdminRepository userRepository) {
        this.adminRepository = userRepository;
        this.mapper = new DozerBeanMapper();
        this.modelMapper = new ModelMapper();
    }

    @Override
    public Admin findByNationalCode(String userName) {
        return adminRepository.findByNationalCode(userName);
    }

    @Override
    public AdminDto addAdminByDefault() {
        firstName = "admin";
        lastName = "admin";
        nationalCode = "admin";
        password = "admin";
        Admin admin = new Admin(firstName,lastName,nationalCode,password);
        Admin returnedAdmin = adminRepository.save(admin);
        return modelMapper.map(returnedAdmin, AdminDto.class);
    }

    @Override
    public ResponseEntity<AdminDto> changeAdminPassword(AdminDto adminDto) {
        Admin admin = mapper.map(adminDto, Admin.class);
        Admin foundedAdmin = adminRepository.getById(admin.getId());
        foundedAdmin.setPassword(adminDto.getPassword());
        Admin returnedAdmin = adminRepository.save(foundedAdmin);
        AdminDto returnedAdminDto = modelMapper.map(returnedAdmin, AdminDto.class);
        if (returnedAdmin.getPassword().equals(admin.getPassword()))
            return ResponseEntity.ok(returnedAdminDto);
        else return ResponseEntity.notFound().build();
    }
}
