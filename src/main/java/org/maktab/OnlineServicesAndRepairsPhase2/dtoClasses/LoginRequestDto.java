package org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginRequestDto {
    private String nationalCode;
    private String password;
}
