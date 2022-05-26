package org.maktab.OnlineServicesAndRepairsPhase2.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Wallet extends BaseEntity<Long> {
    private Double balance;
    private Timestamp depositDate;
    private Timestamp withdrawalDate;
    @OneToOne
    private Customer customer;
    @OneToOne
    private Expert expert;

    public Wallet(Double balance) {
        this.balance = balance;
    }
}
