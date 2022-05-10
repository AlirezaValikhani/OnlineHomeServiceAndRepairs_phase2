package org.maktab.OnlineServicesAndRepairsPhase2.service.interfaces;

import org.maktab.OnlineServicesAndRepairsPhase2.entity.Admin;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.base.User;

public interface AdminService {
    User findByNationalCode(String userName);
    void addAdminByDefault();
    Admin changeAdminPassword(String password);
}
