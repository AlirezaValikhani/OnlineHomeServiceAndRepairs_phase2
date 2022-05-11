package org.maktab.OnlineServicesAndRepairsPhase2.service.impl;

import org.maktab.OnlineServicesAndRepairsPhase2.entity.Offer;
import org.maktab.OnlineServicesAndRepairsPhase2.repository.OfferRepository;
import org.maktab.OnlineServicesAndRepairsPhase2.service.interfaces.OfferService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class OfferServiceImpl implements OfferService {
    private final OfferRepository offerRepository;

    public OfferServiceImpl(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    @Override
    public List<Offer> findByOrderId(Long orderId) {
        return findByOrderId(orderId);
    }

    @Override
    public Offer getById(Long id) {
        return offerRepository.getById(id);
    }
}
