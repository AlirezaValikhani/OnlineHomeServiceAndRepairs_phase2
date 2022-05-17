package org.maktab.OnlineServicesAndRepairsPhase2.service.interfaces;

import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.AdminDto;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Admin;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.base.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AdminService {
    Admin findByNationalCode(String userName);
    AdminDto addAdminByDefault();
    ResponseEntity<AdminDto> changeAdminPassword(AdminDto adminDto);
    Specification<User> userSpecification(User user);
    List<User> findAllByFilter(Specification<User> userSpecification);
}
