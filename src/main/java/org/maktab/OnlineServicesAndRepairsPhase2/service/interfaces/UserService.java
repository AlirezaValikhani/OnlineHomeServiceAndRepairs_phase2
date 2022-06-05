package org.maktab.OnlineServicesAndRepairsPhase2.service.interfaces;

import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.LoginRequestDto;
import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.LoginResponseDto;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.base.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByNationalCode(String nationalCode);
    User findByNationalCodeReturnObj(String nationalCode);
    LoginResponseDto loginRequest(LoginRequestDto loginRequestDto);
}
