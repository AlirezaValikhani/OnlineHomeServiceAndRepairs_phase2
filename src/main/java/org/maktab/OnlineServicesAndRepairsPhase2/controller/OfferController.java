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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/offer")
public class OfferController {
    private final OfferServiceImpl offerService;
    private final DozerBeanMapper mapper;
    private final ModelMapper modelMapper;


    public OfferController(OfferServiceImpl offerService, OrderServiceImpl orderService, ExpertServiceImpl expertService) {
        this.offerService = offerService;
        this.mapper = new DozerBeanMapper();
        this.modelMapper = new ModelMapper();
    }

    @PostMapping("/save")
    public ResponseEntity<OfferDto> save(@RequestBody OfferDto offerDto) {
        Offer offer = mapper.map(offerDto, Offer.class);
        Offer returnedOffer = offerService.save(offer);
        OfferDto returnedOfferDto = modelMapper.map(returnedOffer, OfferDto.class);
        return new ResponseEntity<>(returnedOfferDto, HttpStatus.CREATED);
    }

    @GetMapping("/findOfferListByOrderId")
    public ResponseEntity<List<OfferDto>> findOfferList(OfferDto offerDto) {
        Offer offer = mapper.map(offerDto, Offer.class);
        List<OfferDto> returnedOffers = new ArrayList<>();
        List<Offer> offers = offerService.findOfferListByOrderId(offer);
        for (Offer o : offers) {
            OfferDto returnedOfferDto = modelMapper.map(o, OfferDto.class);
            returnedOffers.add(returnedOfferDto);
        }
        return ResponseEntity.ok(returnedOffers);
    }

    @GetMapping("/showOfferListByCreditAndBidPriceOffer")
    public ResponseEntity<List<OfferDto>> showOfferList(@RequestBody OfferDto offerDto) {
        Offer offer = mapper.map(offerDto, Offer.class);
        List<Offer> offerList = offerService.showOfferList(offer);
        List<OfferDto> returnedOffers = new ArrayList<>();
        for (Offer o : offerList) {
            OfferDto returnedOfferDto = modelMapper.map(o, OfferDto.class);
            returnedOffers.add(returnedOfferDto);
        }
        return ResponseEntity.ok(returnedOffers);
    }
}
