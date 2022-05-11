package org.maktab.OnlineServicesAndRepairsPhase2.controller;

import org.dozer.DozerBeanMapper;
import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.SpecialtyDto;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Customer;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Specialty;
import org.maktab.OnlineServicesAndRepairsPhase2.service.impl.SpecialtyServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/specialty")
public class SpecialtyController {
    private final SpecialtyServiceImpl specialtyService;

    public SpecialtyController(SpecialtyServiceImpl specialtyService) {
        this.specialtyService = specialtyService;
    }

    @PostMapping("/save")
    public ResponseEntity<Specialty> save(@RequestBody SpecialtyDto specialtyDto) {
        DozerBeanMapper mapper = new DozerBeanMapper();
        Specialty specialty = mapper.map(specialtyDto, Specialty.class);
        return ResponseEntity.ok(specialtyService.save(specialty));
    }


}
