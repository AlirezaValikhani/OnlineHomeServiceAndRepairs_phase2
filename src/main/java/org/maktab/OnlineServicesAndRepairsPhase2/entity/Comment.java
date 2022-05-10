package org.maktab.OnlineServicesAndRepairsPhase2.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Comment extends BaseEntity<Long> {
    @Column(nullable = false)
    private String comment;
    @ManyToOne
    private Customer customer;
    @ManyToOne
    private Expert expert;
}
