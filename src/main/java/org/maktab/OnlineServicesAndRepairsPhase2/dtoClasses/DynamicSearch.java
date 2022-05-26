package org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DynamicSearch {
    private String firstName;
    private String lastName;
    private String email;
    private String nationalCode;
    private String userType;
    private String service;
    private Integer credit;
}
