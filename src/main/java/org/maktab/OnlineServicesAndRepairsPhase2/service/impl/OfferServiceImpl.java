package org.maktab.OnlineServicesAndRepairsPhase2.service.impl;

import org.maktab.OnlineServicesAndRepairsPhase2.entity.Offer;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Order;
import org.maktab.OnlineServicesAndRepairsPhase2.exceptions.NotFoundOfferException;
import org.maktab.OnlineServicesAndRepairsPhase2.exceptions.NotFoundOrderException;
import org.maktab.OnlineServicesAndRepairsPhase2.repository.OfferRepository;
import org.maktab.OnlineServicesAndRepairsPhase2.service.interfaces.OfferService;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class OfferServiceImpl implements OfferService {
    private final OfferRepository offerRepository;
    private final OrderServiceImpl orderService;

    public OfferServiceImpl(OfferRepository offerRepository, OrderServiceImpl orderService) {
        this.offerRepository = offerRepository;
        this.orderService = orderService;
    }

    @Override
    public List<Offer> findOfferListByOrderId(Offer offer) {
        Order foundedOrder = orderService.getById(offer.getOrder().getId());
        if(foundedOrder == null)
            throw new NotFoundOrderException();
        return offerRepository.getOrderOffers(offer.getOrder().getId());

    }

    @Override
    public List<Offer> showOfferList(Order order)  {
        return offerRepository.getOrderOffers(order.getId());
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
            return offerRepository.save(offer);
    }
}
