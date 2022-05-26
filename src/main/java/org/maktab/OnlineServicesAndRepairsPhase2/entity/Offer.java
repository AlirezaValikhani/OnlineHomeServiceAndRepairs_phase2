package org.maktab.OnlineServicesAndRepairsPhase2.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.base.BaseEntity;

import javax.persistence.*;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Offer extends BaseEntity<Long> {
    private Timestamp dateAndTimeOfBidSubmission;
    private Double bidPriceOffer;
    private String durationOfWork;
    private Timestamp startTime;
    @ManyToOne(fetch = FetchType.EAGER)
    private Order order;
    @ManyToOne
    private Expert expert;

    @Override
    public String toString() {
        return "Offer{" + super.toString() +
                ", dateAndTimeOfBidSubmission=" + dateAndTimeOfBidSubmission +
                ", bidPriceOffer=" + bidPriceOffer +
                ", durationOfWork='" + durationOfWork + '\'' +
                ", startTime=" + startTime +
                ", order=" + order +
                '}';
    }
}
