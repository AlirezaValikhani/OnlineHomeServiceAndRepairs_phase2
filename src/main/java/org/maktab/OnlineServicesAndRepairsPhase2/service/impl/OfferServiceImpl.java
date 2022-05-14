package org.maktab.OnlineServicesAndRepairsPhase2.service.impl;

import org.dozer.DozerBeanMapper;
import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.OfferDto;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Expert;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Offer;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Order;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.enums.OrderStatus;
import org.maktab.OnlineServicesAndRepairsPhase2.repository.OfferRepository;
import org.maktab.OnlineServicesAndRepairsPhase2.service.interfaces.OfferService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OfferServiceImpl implements OfferService {
    private final OfferRepository offerRepository;
    private final OrderServiceImpl orderService;
    private final ExpertServiceImpl expertService;
    private final DozerBeanMapper mapper;
    private final ModelMapper modelMapper;

    public OfferServiceImpl(OfferRepository offerRepository, OrderServiceImpl orderService, ExpertServiceImpl expertService) {
        this.offerRepository = offerRepository;
        this.orderService = orderService;
        this.expertService = expertService;
        this.mapper = new DozerBeanMapper();
        this.modelMapper = new ModelMapper();
    }

    @Override
    public ResponseEntity<List<OfferDto>> findOfferListByOrderId(OfferDto offerDto) {
        List<Offer> offers = offerRepository.getOrderOffers(offerDto.getOrderId());
        List<OfferDto> returnedOffers = new ArrayList<>();
        for (Offer o : offers) {
            OfferDto returnedOfferDto = modelMapper.map(o, OfferDto.class);
            returnedOffers.add(returnedOfferDto);
        }
        return ResponseEntity.ok(returnedOffers);
    }

    @Override
    public ResponseEntity<List<OfferDto>> showOfferList(OfferDto offerDto)  {
        List<Offer> offerList = offerRepository.getOrderOffers(offerDto.getOrderId());
        List<OfferDto> returnedOffers = new ArrayList<>();
        for (Offer o : offerList) {
            OfferDto returnedOfferDto = modelMapper.map(o, OfferDto.class);
            returnedOffers.add(returnedOfferDto);
        }
        return ResponseEntity.ok(returnedOffers);
    }

    @Override
    public Offer getById(Long id) {
        return offerRepository.getById(id);
    }

    @Override
    public ResponseEntity<OfferDto> save(OfferDto offerDto) {
        Order order = orderService.getById(offerDto.getOrderId());
        Expert expert = expertService.getById(offerDto.getExpertId());
        Offer offer = mapper.map(offerDto, Offer.class);
        if (offer != null) {
            order.setOrderStatus(OrderStatus.WAITING_FOR_EXPERT_SELECTION);
            offer.setOrder(order);
            offer.setExpert(expert);
            Offer returnedOffer = offerRepository.save(offer);
            OfferDto returnedOfferDto = modelMapper.map(returnedOffer, OfferDto.class);
            return ResponseEntity.ok(returnedOfferDto);
        } else return ResponseEntity.notFound().build();
    }
}
