package org.maktab.OnlineServicesAndRepairsPhase2.controller;

import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.LoginRequestDto;
import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.LoginResponseDto;
import org.maktab.OnlineServicesAndRepairsPhase2.service.impl.UserServiceImpl;
import org.maktab.OnlineServicesAndRepairsPhase2.service.interfaces.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin
public class LoginController {
    private final UserServiceImpl userService;

    public LoginController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto){
        return new ResponseEntity<>(userService.loginRequest(loginRequestDto), HttpStatus.OK);
    }
}
