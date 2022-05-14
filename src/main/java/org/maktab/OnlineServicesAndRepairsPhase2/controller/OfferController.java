package org.maktab.OnlineServicesAndRepairsPhase2.controller;

import org.dozer.DozerBeanMapper;
import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.OfferDto;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Expert;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Offer;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Order;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.enums.OrderStatus;
import org.maktab.OnlineServicesAndRepairsPhase2.service.impl.ExpertServiceImpl;
import org.maktab.OnlineServicesAndRepairsPhase2.service.impl.OfferServiceImpl;
import org.maktab.OnlineServicesAndRepairsPhase2.service.impl.OrderServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/offer")
public class OfferController {
    private final OfferServiceImpl offerService;


    public OfferController(OfferServiceImpl offerService, OrderServiceImpl orderService, ExpertServiceImpl expertService) {
        this.offerService = offerService;
    }

    @PostMapping("/save")
    public ResponseEntity<OfferDto> save(@RequestBody OfferDto offerDto) {
        return offerService.save(offerDto);
    }

    @GetMapping("/findOfferListByOrderId")
    public ResponseEntity<List<OfferDto>> findOfferList(OfferDto offerDto) {
        return offerService.findOfferListByOrderId(offerDto);
    }

    @GetMapping("/showOfferListByCreditAndBidPriceOffer")
    public ResponseEntity<List<OfferDto>> showOfferList(@RequestBody OfferDto offerDto) {
        return offerService.showOfferList(offerDto);
    }
}
