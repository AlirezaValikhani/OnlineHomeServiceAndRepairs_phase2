package org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.enums.UserStatus;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExpertDto {
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String nationalCode;
    private String password;
    private Timestamp registrationDate;
    private Integer credit;
    private Double balance;

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;
    private byte[] image;
    private String city;
}
