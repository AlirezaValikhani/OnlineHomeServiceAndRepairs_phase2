package org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.enums.UserStatus;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExpertDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String nationalCode;
    private String password;
    private Integer credit;
    private Double balance;

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;
    private MultipartFile image;
    private String city;
    private Long[] servicesId;
}
