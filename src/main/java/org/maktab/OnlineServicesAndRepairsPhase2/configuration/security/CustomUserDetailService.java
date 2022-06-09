package org.maktab.OnlineServicesAndRepairsPhase2.configuration.security;

import org.maktab.OnlineServicesAndRepairsPhase2.entity.base.User;
import org.maktab.OnlineServicesAndRepairsPhase2.exceptions.WrongCredentialsException;
import org.maktab.OnlineServicesAndRepairsPhase2.service.impl.UserServiceImpl;
import org.maktab.OnlineServicesAndRepairsPhase2.service.interfaces.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class CustomUserDetailService implements UserDetailsService {
    private final UserService userService;
    private final HttpServletRequest request;

    public CustomUserDetailService(UserService userService, HttpServletRequest request) {
        this.userService = userService;
        this.request = request;
    }

    @Override
    public UserDetails loadUserByUsername(String nationalCode) throws UsernameNotFoundException {
        User user = userService.findByNationalCodeReturnObj(nationalCode);
        if (user != null) {
            return new CustomUserDetails(user);
        } else {
            throw new WrongCredentialsException("Username or password is incorrect!");
        }
    }
}
