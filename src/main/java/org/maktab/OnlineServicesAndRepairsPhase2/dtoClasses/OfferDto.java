package org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OfferDto {
    private Timestamp dateAndTimeOfBidSubmission;
    private Double bidPriceOffer;
    private String durationOfWork;
    private Timestamp startTime;
    private OrderDto orderDto;
    private ExpertDto expertDto;
}
