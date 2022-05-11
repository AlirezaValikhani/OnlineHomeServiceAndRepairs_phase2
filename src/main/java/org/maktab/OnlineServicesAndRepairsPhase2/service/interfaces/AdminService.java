package org.maktab.OnlineServicesAndRepairsPhase2.service.interfaces;

import org.maktab.OnlineServicesAndRepairsPhase2.entity.Admin;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.base.User;

public interface AdminService {
    Admin findByNationalCode(String userName);
    Admin addAdminByDefault();
    Admin changeAdminPassword(String nationalCode,String password);
}
