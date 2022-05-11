package org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SpecialtyDto {
    private String name;
    private Double basePrice;
    private String description;
    private Long categoryId;
}
