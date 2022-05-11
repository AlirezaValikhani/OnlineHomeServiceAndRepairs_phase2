package org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.enums.OrderStatus;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDto {
    private Double bidPriceOrder;
    private String jobDescription;
    private String address;
    private Timestamp orderRegistrationDate;
    private Timestamp orderExecutionDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    private Long expertId;
    private Long customerId;
    private Long specialtyId;
}
