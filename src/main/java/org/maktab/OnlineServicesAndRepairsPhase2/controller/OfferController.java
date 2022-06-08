package org.maktab.OnlineServicesAndRepairsPhase2.controller;

import org.dozer.DozerBeanMapper;
import org.maktab.OnlineServicesAndRepairsPhase2.configuration.security.SecurityUtil;
import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.OfferDto;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Expert;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Offer;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Order;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.base.User;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.enums.OrderStatus;
import org.maktab.OnlineServicesAndRepairsPhase2.exceptions.NotFoundExpertException;
import org.maktab.OnlineServicesAndRepairsPhase2.exceptions.NotFoundOrderException;
import org.maktab.OnlineServicesAndRepairsPhase2.service.impl.ExpertServiceImpl;
import org.maktab.OnlineServicesAndRepairsPhase2.service.impl.OfferServiceImpl;
import org.maktab.OnlineServicesAndRepairsPhase2.service.impl.OrderServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/offer")
public class OfferController {
    private final OfferServiceImpl offerService;
    private final OrderServiceImpl orderService;
    private final ExpertServiceImpl expertService;
    private final DozerBeanMapper mapper;
    private final ModelMapper modelMapper;


    public OfferController(OfferServiceImpl offerService, OrderServiceImpl orderService, ExpertServiceImpl expertService, OrderServiceImpl orderService1, ExpertServiceImpl expertService1) {
        this.offerService = offerService;
        this.orderService = orderService1;
        this.expertService = expertService1;
        this.mapper = new DozerBeanMapper();
        this.modelMapper = new ModelMapper();
    }

    @PreAuthorize("hasRole('EXPERT')")
    @PostMapping("/save")
    public ResponseEntity<OfferDto> save(@RequestBody OfferDto offerDto) {
        Order order = orderService.getById(offerDto.getOrderId());
        if(order == null)
            throw new NotFoundOrderException();
        order.setOrderStatus(OrderStatus.WAITING_FOR_EXPERT_SELECTION);
        User user = SecurityUtil.getCurrentUser();
        Expert expert = expertService.getById(user.getId());
        if(expert == null)
            throw new NotFoundExpertException();
        Offer offer = new Offer(offerDto.getDateAndTimeOfBidSubmission(),
                offerDto.getBidPriceOffer(),offerDto.getDurationOfWork(),
                offerDto.getStartTime(),order,expert);
        Offer returnedOffer = offerService.save(offer);
        OfferDto returnedOfferDto = modelMapper.map(returnedOffer, OfferDto.class);
        return new ResponseEntity<>(returnedOfferDto, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
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

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/showOfferListByCreditAndBidPriceOffer")
    public ResponseEntity<List<OfferDto>> showOfferList(@RequestBody OfferDto offerDto) {
        Order foundedOrder = orderService.getById(offerDto.getOrderId());
        if(foundedOrder == null)
            throw new NotFoundOrderException();
        List<Offer> offerList = offerService.showOfferList(foundedOrder);
        List<OfferDto> returnedOffers = new ArrayList<>();
        for (Offer o : offerList) {
            OfferDto returnedOfferDto = modelMapper.map(o, OfferDto.class);
            returnedOffers.add(returnedOfferDto);
        }
        return ResponseEntity.ok(returnedOffers);
    }
}
