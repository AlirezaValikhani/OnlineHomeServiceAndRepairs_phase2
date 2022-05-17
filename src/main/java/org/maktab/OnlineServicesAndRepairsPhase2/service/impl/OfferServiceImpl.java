package org.maktab.OnlineServicesAndRepairsPhase2.service.impl;

import org.dozer.DozerBeanMapper;
import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.OfferDto;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Expert;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Offer;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Order;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.enums.OrderStatus;
import org.maktab.OnlineServicesAndRepairsPhase2.exceptions.NotFoundExpertException;
import org.maktab.OnlineServicesAndRepairsPhase2.exceptions.NotFoundOfferException;
import org.maktab.OnlineServicesAndRepairsPhase2.exceptions.NotFoundOrderException;
import org.maktab.OnlineServicesAndRepairsPhase2.repository.OfferRepository;
import org.maktab.OnlineServicesAndRepairsPhase2.service.interfaces.OfferService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
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
        Order foundedOrder = orderService.getById(offerDto.getOrderId());
        if(foundedOrder == null)
            throw new NotFoundOrderException();
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
        Order foundedOrder = orderService.getById(offerDto.getOrderId());
        if(foundedOrder == null)
            throw new NotFoundOrderException();
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
        Offer foundedOffer = offerRepository.getById(id);
        if(foundedOffer == null)
            throw new NotFoundOfferException();
        return foundedOffer;
    }

    @Override
    public ResponseEntity<OfferDto> save(OfferDto offerDto) {
        Order order = orderService.getById(offerDto.getOrderId());
        if(order == null)
            throw new NotFoundOrderException();
        Expert expert = expertService.getById(offerDto.getExpertId());
        if(expert == null)
            throw new NotFoundExpertException();
        Offer offer = mapper.map(offerDto, Offer.class);
        if (offer != null) {
            order.setOrderStatus(OrderStatus.WAITING_FOR_EXPERT_SELECTION);
            Offer toSaveOffer = new Offer(offer.getDateAndTimeOfBidSubmission(),offer.getBidPriceOffer()
                    ,offer.getDurationOfWork(),offer.getStartTime(),order,expert);
            Offer returnedOffer = offerRepository.save(toSaveOffer);
            OfferDto returnedOfferDto = modelMapper.map(returnedOffer, OfferDto.class);
            return new ResponseEntity<>(returnedOfferDto, HttpStatus.CREATED);
        } else return ResponseEntity.notFound().build();
    }
}
