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

    public OfferServiceImpl(OfferRepository offerRepository, OrderServiceImpl orderService, ExpertServiceImpl expertService) {
        this.offerRepository = offerRepository;
        this.orderService = orderService;
        this.expertService = expertService;
    }

    @Override
    public List<Offer> findOfferListByOrderId(Offer offer) {
        Order foundedOrder = orderService.getById(offer.getOrder().getId());
        if(foundedOrder == null)
            throw new NotFoundOrderException();
        return offerRepository.getOrderOffers(offer.getOrder().getId());

    }

    @Override
    public List<Offer> showOfferList(Offer offer)  {
        Order foundedOrder = orderService.getById(offer.getOrder().getId());
        if(foundedOrder == null)
            throw new NotFoundOrderException();
        return offerRepository.getOrderOffers(offer.getOrder().getId());
    }

    @Override
    public Offer getById(Long id) {
        Offer foundedOffer = offerRepository.getById(id);
        if(foundedOffer == null)
            throw new NotFoundOfferException();
        return foundedOffer;
    }

    @Override
    public Offer save(Offer offer) {
        Order order = orderService.getById(offer.getOrder().getId());
        if(order == null)
            throw new NotFoundOrderException();
        Expert expert = expertService.getById(offer.getOrder().getId());
        if(expert == null)
            throw new NotFoundExpertException();
            order.setOrderStatus(OrderStatus.WAITING_FOR_EXPERT_SELECTION);
            Offer toSaveOffer = new Offer(offer.getDateAndTimeOfBidSubmission(),offer.getBidPriceOffer()
                    ,offer.getDurationOfWork(),offer.getStartTime(),order,expert);
            return offerRepository.save(toSaveOffer);
    }
}
