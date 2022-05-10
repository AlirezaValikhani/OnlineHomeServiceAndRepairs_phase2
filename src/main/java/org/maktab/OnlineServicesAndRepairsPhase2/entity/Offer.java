package org.maktab.OnlineServicesAndRepairsPhase2.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Offer extends BaseEntity<Long> {
    @Column(nullable = false)
    private Timestamp dateAndTimeOfBidSubmission;
    @Column(nullable = false)
    private Double bidPriceOffer;
    @Column(nullable = false)
    private String durationOfWork;
    @Column(nullable = false)
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
