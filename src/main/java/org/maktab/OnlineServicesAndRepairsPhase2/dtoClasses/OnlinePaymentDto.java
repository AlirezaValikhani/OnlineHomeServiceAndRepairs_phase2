package org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.maktab.OnlineServicesAndRepairsPhase2.validation.cardNumber.CardNumber;
import org.maktab.OnlineServicesAndRepairsPhase2.validation.secondPassword.SecondPassword;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OnlinePaymentDto {
    private Long orderId;
    private Long offerId;
    @CardNumber
    private String cardNumber;
    @org.maktab.OnlineServicesAndRepairsPhase2.validation.cvv2.Cvv2
    private String Cvv2;
    private Timestamp expirationDate;
    @SecondPassword
    private String secondPassword;
}
