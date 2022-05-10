package org.maktab.OnlineServicesAndRepairsPhase2.service.interfaces;

import org.maktab.OnlineServicesAndRepairsPhase2.entity.Offer;

import java.util.List;

public interface OfferService {
    List<Offer> findByOrderId(Long orderId);
}
