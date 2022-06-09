package org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.enums.OrderStatus;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderBasedOnTimePeriodDto {
    private Timestamp firstDate;
    private Timestamp secondDate;
    private OrderStatus orderStatus;
    private String specialtyName;
}
