package org.maktab.OnlineServicesAndRepairsPhase2.controller;

import org.dozer.DozerBeanMapper;
import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.SpecialtyDto;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Category;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Specialty;
import org.maktab.OnlineServicesAndRepairsPhase2.service.impl.CategoryServiceImpl;
import org.maktab.OnlineServicesAndRepairsPhase2.service.impl.SpecialtyServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/specialty")
public class SpecialtyController {
    private final SpecialtyServiceImpl specialtyService;

    public SpecialtyController(SpecialtyServiceImpl specialtyService, CategoryServiceImpl categoryService) {
        this.specialtyService = specialtyService;
    }

    @PostMapping("/save")
    public ResponseEntity<SpecialtyDto> save(@Valid @RequestBody SpecialtyDto specialtyDto) {
        return specialtyService.save(specialtyDto);
    }

    @PostMapping("/addCategoryToSpecialty")
    public ResponseEntity<SpecialtyDto> addCategoryToSpecialty(@RequestBody SpecialtyDto specialtyDto) {
       return specialtyService.addCategoryToSpecialty(specialtyDto);
    }
}
