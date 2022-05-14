package org.maktab.OnlineServicesAndRepairsPhase2.service.interfaces;

import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.AdminDto;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Admin;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.base.User;
import org.springframework.http.ResponseEntity;

public interface AdminService {
    Admin findByNationalCode(String userName);
    AdminDto addAdminByDefault();
    ResponseEntity<AdminDto> changeAdminPassword(AdminDto adminDto);
}
