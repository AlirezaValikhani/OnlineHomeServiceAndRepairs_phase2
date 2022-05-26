package org.maktab.OnlineServicesAndRepairsPhase2.service.interfaces;

import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.OfferDto;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Offer;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Order;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OfferService {
    Offer getById(Long id);
    Offer save(Offer offer);
    List<Offer> findOfferListByOrderId(Offer offer);
    List<Offer> showOfferList(Order order);
}
