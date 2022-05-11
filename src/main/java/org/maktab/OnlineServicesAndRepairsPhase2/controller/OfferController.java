package org.maktab.OnlineServicesAndRepairsPhase2.controller;

import org.dozer.DozerBeanMapper;
import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.OfferDto;
import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.OrderDto;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Expert;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Offer;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Order;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.enums.OrderStatus;
import org.maktab.OnlineServicesAndRepairsPhase2.service.impl.ExpertServiceImpl;
import org.maktab.OnlineServicesAndRepairsPhase2.service.impl.OfferServiceImpl;
import org.maktab.OnlineServicesAndRepairsPhase2.service.impl.OrderServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<Offer> save(@RequestBody OfferDto offerDto){
        Order order = orderService.getById(offerDto.getOrderId());
        Expert expert = expertService.getById(offerDto.getExpertId());
        DozerBeanMapper mapper = new DozerBeanMapper();
        Offer offer = mapper.map(offerDto, Offer.class);
        if (offer != null) {
            order.setOrderStatus(OrderStatus.WAITING_FOR_EXPERT_SELECTION);
            offer.setOrder(order);
            offer.setExpert(expert);
            return ResponseEntity.ok(offerService.save(offer));
        } else return ResponseEntity.notFound().build();
    }
}
