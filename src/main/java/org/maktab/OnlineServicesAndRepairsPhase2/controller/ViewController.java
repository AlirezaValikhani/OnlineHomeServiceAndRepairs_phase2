package org.maktab.OnlineServicesAndRepairsPhase2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {

    @GetMapping("")
    public String home(){
        return "index";
    }
}