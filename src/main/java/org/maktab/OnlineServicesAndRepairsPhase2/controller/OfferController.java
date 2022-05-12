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
    private final OrderServiceImpl orderService;
    private final ExpertServiceImpl expertService;

    public OfferController(OfferServiceImpl offerService, OrderServiceImpl orderService,ExpertServiceImpl expertService) {
        this.offerService = offerService;
        this.orderService = orderService;
        this.expertService = expertService;
    }

    @PostMapping("/save")
    public ResponseEntity<OfferDto> save(@RequestBody OfferDto offerDto){
        Order order = orderService.getById(offerDto.getOrderId());
        Expert expert = expertService.getById(offerDto.getExpertId());
        DozerBeanMapper mapper = new DozerBeanMapper();
        Offer offer = mapper.map(offerDto, Offer.class);
        if (offer != null) {
            order.setOrderStatus(OrderStatus.WAITING_FOR_EXPERT_SELECTION);
            offer.setOrder(order);
            offer.setExpert(expert);
            Offer returnedOffer = offerService.save(offer);
            ModelMapper modelMapper = new ModelMapper();
            OfferDto returnedOfferDto = modelMapper.map(returnedOffer, OfferDto.class);
            return ResponseEntity.ok(returnedOfferDto);
        } else return ResponseEntity.notFound().build();
    }

    @GetMapping("/findOfferListByOrderId")
    public ResponseEntity<List<OfferDto>> findOfferList(OfferDto offerDto){
        List<Offer> offers = offerService.findByOrderId(offerDto.getOrderId());
        ModelMapper modelMapper = new ModelMapper();
        List<OfferDto> returnedOffers = new ArrayList<>();
        System.out.println(offers.size());
        for (Offer o:offers) {
            OfferDto returnedOfferDto = modelMapper.map(o, OfferDto.class);
            returnedOffers.add(returnedOfferDto);
        }
        return ResponseEntity.ok(returnedOffers);
    }
}
