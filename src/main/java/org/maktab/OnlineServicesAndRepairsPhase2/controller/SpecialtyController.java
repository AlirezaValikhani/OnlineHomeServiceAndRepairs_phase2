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

@RestController
@RequestMapping("/specialty")
public class SpecialtyController {
    private final SpecialtyServiceImpl specialtyService;
    private final CategoryServiceImpl categoryService;
    private final DozerBeanMapper mapper;
    private final ModelMapper modelMapper;

    public SpecialtyController(SpecialtyServiceImpl specialtyService,CategoryServiceImpl categoryService) {
        this.specialtyService = specialtyService;
        this.categoryService = categoryService;
        this.mapper = new DozerBeanMapper();
        this.modelMapper = new ModelMapper();
    }

    @PostMapping("/save")
    public ResponseEntity<SpecialtyDto> save(@RequestBody SpecialtyDto specialtyDto) {
        Specialty specialty = mapper.map(specialtyDto, Specialty.class);
        Specialty returnedSpecialty = specialtyService.save(specialty);
        SpecialtyDto returnedSpecialtyDto = modelMapper.map(returnedSpecialty, SpecialtyDto.class);
        return ResponseEntity.ok(returnedSpecialtyDto);
    }

    @PostMapping("/addCategoryToSpecialty")
    public ResponseEntity<SpecialtyDto> addCategoryToSpecialty(@RequestBody SpecialtyDto specialtyDto) {
        Category category = categoryService.getById(specialtyDto.getCategoryId());
        Specialty specialty = specialtyService.getById(specialtyDto.getId());
        specialty.setCategory(category);
        Specialty returnedSpecialty = specialtyService.save(specialty);
        SpecialtyDto returnedSpecialtyDto = modelMapper.map(returnedSpecialty, SpecialtyDto.class);
        return ResponseEntity.ok(returnedSpecialtyDto);
    }
}
