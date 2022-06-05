package org.maktab.OnlineServicesAndRepairsPhase2.configuration.security;


import org.maktab.OnlineServicesAndRepairsPhase2.entity.base.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtil {

    public static User getCurrentUser() {
        return ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
    }
}