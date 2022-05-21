package org.maktab.OnlineServicesAndRepairsPhase2.controller;

import org.dozer.DozerBeanMapper;
import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.SpecialtyDto;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Category;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Specialty;
import org.maktab.OnlineServicesAndRepairsPhase2.service.impl.CategoryServiceImpl;
import org.maktab.OnlineServicesAndRepairsPhase2.service.impl.SpecialtyServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
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
    private final DozerBeanMapper mapper;
    private final ModelMapper modelMapper;

    public SpecialtyController(SpecialtyServiceImpl specialtyService, CategoryServiceImpl categoryService) {
        this.specialtyService = specialtyService;
        this.mapper = new DozerBeanMapper();
        this.modelMapper = new ModelMapper();
    }

    @PostMapping("/save")
    public ResponseEntity<SpecialtyDto> save(@Valid @RequestBody SpecialtyDto specialtyDto) {
        Specialty specialty = mapper.map(specialtyDto, Specialty.class);
        Specialty returnedSpecialty = specialtyService.addSpecialty(specialty,specialtyDto.getCategoryId());
        SpecialtyDto returnedSpecialtyDto = modelMapper.map(returnedSpecialty, SpecialtyDto.class);
        return new ResponseEntity<>(returnedSpecialtyDto, HttpStatus.CREATED);
    }

    @PostMapping("/addCategoryToSpecialty")
    public ResponseEntity<SpecialtyDto> addCategoryToSpecialty(@RequestBody SpecialtyDto specialtyDto) {
        Specialty specialty = mapper.map(specialtyDto, Specialty.class);
        Specialty returnedSpecialty = specialtyService.addCategoryToSpecialty(specialty);
        SpecialtyDto returnedSpecialtyDto = modelMapper.map(returnedSpecialty, SpecialtyDto.class);
        return ResponseEntity.ok(returnedSpecialtyDto);
    }
}
