package org.maktab.OnlineServicesAndRepairsPhase2.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.base.BaseEntity;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.enums.OrderStatus;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order extends BaseEntity<Long> {
    private Double bidPriceOrder;
    private String jobDescription;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private Timestamp orderRegistrationDate;
    @Column(nullable = false)
    private Timestamp orderExecutionDate;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    @ManyToOne
    private Expert expert;
    @ManyToOne
    private Customer customer;
    @ManyToOne
    private Specialty service;
    @OneToMany(mappedBy = "order")
    private Set<Offer> offers;

    public Order(Double bidPriceOrder, String jobDescription, String address, Timestamp orderRegistrationDate, Timestamp orderExecutionDate, OrderStatus orderStatus, Customer customer, Specialty service) {
        this.bidPriceOrder = bidPriceOrder;
        this.jobDescription = jobDescription;
        this.address = address;
        this.orderRegistrationDate = orderRegistrationDate;
        this.orderExecutionDate = orderExecutionDate;
        this.orderStatus = orderStatus;
        this.customer = customer;
        this.service = service;
    }

    @Override
    public String toString() {
        return "Order{" + super.toString() +
                ", bidPriceOrder=" + bidPriceOrder +
                ", jobDescription='" + jobDescription + '\'' +
                ", address='" + address + '\'' +
                ", orderRegistrationDate=" + orderRegistrationDate +
                ", orderExecutionDate=" + orderExecutionDate +
                ", orderStatus=" + orderStatus +
                '}';
    }
}
