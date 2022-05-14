package org.maktab.OnlineServicesAndRepairsPhase2.service.interfaces;

import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.OfferDto;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Offer;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OfferService {
    Offer getById(Long id);
    ResponseEntity<OfferDto> save(OfferDto offerDto);
    ResponseEntity<List<OfferDto>> findOfferListByOrderId(OfferDto offerDto);
    ResponseEntity<List<OfferDto>> showOfferList(OfferDto offerDto);
}
