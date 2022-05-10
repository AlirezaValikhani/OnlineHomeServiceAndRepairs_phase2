package org.maktab.OnlineServicesAndRepairsPhase2.service.impl;

import org.maktab.OnlineServicesAndRepairsPhase2.entity.Admin;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.base.User;
import org.maktab.OnlineServicesAndRepairsPhase2.repository.AdminRepository;
import org.maktab.OnlineServicesAndRepairsPhase2.service.interfaces.AdminService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;
    private String firstName,lastName,nationalCode,password;

    public AdminServiceImpl(AdminRepository userRepository) {
        this.adminRepository = userRepository;
    }

    @Override
    public User findByNationalCode(String userName) {
        return adminRepository.findByNationalCode(userName);
    }

    @Override
    public void addAdminByDefault() {
        firstName = "admin";
        lastName = "admin";
        nationalCode = "admin";
        password = "admin";
        Admin admin = new Admin(firstName,lastName,nationalCode,password);
        Admin returnedAdmin = adminRepository.save(admin);
    }

    @Override
    public Admin changeAdminPassword(String password) {
        Admin admin = new Admin(password);
        return adminRepository.save(admin);
    }
}
