package org.maktab.OnlineServicesAndRepairsPhase2.controller;

import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.OfferDto;
import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.OrderDto;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Offer;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Order;
import org.maktab.OnlineServicesAndRepairsPhase2.service.impl.OfferServiceImpl;
import org.maktab.OnlineServicesAndRepairsPhase2.service.impl.OrderServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/offer")
public class OfferController {
    private final OfferServiceImpl offerService;

    public OfferController(OfferServiceImpl offerService, OrderServiceImpl orderService) {
        this.offerService = offerService;
    }

    @PostMapping("/save")
    public ResponseEntity<Offer> save(@RequestBody OfferDto offerDto){
        return null;
    }
}
