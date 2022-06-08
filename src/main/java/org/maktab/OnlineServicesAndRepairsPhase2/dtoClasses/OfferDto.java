package org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OfferDto {
    private Long id;
    private Timestamp dateAndTimeOfBidSubmission;
    private Double bidPriceOffer;
    private String durationOfWork;
    private Timestamp startTime;
    private Long orderId;
}
