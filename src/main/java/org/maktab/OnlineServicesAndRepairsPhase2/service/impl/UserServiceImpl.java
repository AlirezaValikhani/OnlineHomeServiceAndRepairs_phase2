package org.maktab.OnlineServicesAndRepairsPhase2.service.impl;

import org.maktab.OnlineServicesAndRepairsPhase2.configuration.security.CustomUserDetailService;
import org.maktab.OnlineServicesAndRepairsPhase2.configuration.security.CustomUserDetails;
import org.maktab.OnlineServicesAndRepairsPhase2.configuration.security.jwt.JwtUtil;
import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.LoginRequestDto;
import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.LoginResponseDto;
import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.UserDto;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.base.User;
import org.maktab.OnlineServicesAndRepairsPhase2.repository.UserRepository;
import org.maktab.OnlineServicesAndRepairsPhase2.service.interfaces.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final CustomUserDetailService customUserDetailService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public UserServiceImpl(UserRepository userRepository, @Lazy CustomUserDetailService customUserDetailService, JwtUtil jwtUtil, @Lazy AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.customUserDetailService = customUserDetailService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Optional<User> findByNationalCode(String nationalCode) {
        return userRepository.findByNationalCode(nationalCode);
    }

    @Override
    public User findByNationalCodeReturnObj(String nationalCode) {
        return userRepository.findByNationalCodeReturnObject(nationalCode);
    }

    @Override
    public LoginResponseDto loginRequest(LoginRequestDto loginRequestDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getNationalCode(), loginRequestDto.getPassword()));
        UserDetails userDetails = customUserDetailService.loadUserByUsername(loginRequestDto.getNationalCode());
        String token = jwtUtil.generateToken(userDetails);
        CustomUserDetails customUserDetails = (CustomUserDetails) userDetails;
        return new LoginResponseDto(
                new UserDto(customUserDetails.getUser().getFirstName(),customUserDetails.getUser().getLastName(),customUserDetails.getUser().getCredit()),token);
    }
}
