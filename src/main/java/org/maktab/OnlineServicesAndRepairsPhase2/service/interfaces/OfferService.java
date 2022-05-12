package org.maktab.OnlineServicesAndRepairsPhase2.service.interfaces;

import org.maktab.OnlineServicesAndRepairsPhase2.entity.Offer;

import java.util.List;

public interface OfferService {
    Offer getById(Long id);
    Offer save(Offer offer);
    List<Offer> findByOrderId(Long id);
}
