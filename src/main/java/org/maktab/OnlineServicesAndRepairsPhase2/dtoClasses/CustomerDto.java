package org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.enums.UserStatus;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String nationalCode;
    @Size(min = 8, message = "Password should have at least 8 characters!!!")
    private String password;
    private Timestamp registrationDate;
    private Double balance;
    private Integer credit;

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;
    private Long[] orderId;
    private Long commentId;
    private Long expertId;
    private Long walletId;
}
