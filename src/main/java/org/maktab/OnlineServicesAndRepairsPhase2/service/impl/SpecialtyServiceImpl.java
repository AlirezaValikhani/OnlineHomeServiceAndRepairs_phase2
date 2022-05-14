package org.maktab.OnlineServicesAndRepairsPhase2.service.impl;

import org.dozer.DozerBeanMapper;
import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.SpecialtyDto;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Category;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Specialty;
import org.maktab.OnlineServicesAndRepairsPhase2.repository.SpecialtyRepository;
import org.maktab.OnlineServicesAndRepairsPhase2.service.interfaces.SpecialtyService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;

import javax.transaction.Transactional;

@org.springframework.stereotype.Service
@Transactional
public class SpecialtyServiceImpl implements SpecialtyService {
    private final SpecialtyRepository specialtyRepository;
    private final CategoryServiceImpl categoryService;
    private final DozerBeanMapper mapper;
    private final ModelMapper modelMapper;

    public SpecialtyServiceImpl(SpecialtyRepository serviceRepository, CategoryServiceImpl categoryService) {
        this.specialtyRepository = serviceRepository;
        this.categoryService = categoryService;
        this.mapper = new DozerBeanMapper();
        this.modelMapper = new ModelMapper();
    }

    @Override
    public Specialty findByName(String serviceName) {
        return specialtyRepository.findByName(serviceName);
    }

    @Override
    public Specialty getById(Long id) {
        return specialtyRepository.getById(id);
    }

    @Override
    public Specialty save(Specialty specialty) {
        return specialtyRepository.save(specialty);
    }

    @Override
    public ResponseEntity<SpecialtyDto> save(SpecialtyDto specialtyDto) {
        Category category = categoryService.getById(specialtyDto.getCategoryId());
        Specialty specialty = getById(specialtyDto.getId());
        specialty.setCategory(category);
        Specialty returnedSpecialty = save(specialty);
        SpecialtyDto returnedSpecialtyDto = modelMapper.map(returnedSpecialty, SpecialtyDto.class);
        return ResponseEntity.ok(returnedSpecialtyDto);
    }

    @Override
    public ResponseEntity<SpecialtyDto> addCategoryToSpecialty(SpecialtyDto specialtyDto) {
        Category category = categoryService.getById(specialtyDto.getCategoryId());
        Specialty specialty = getById(specialtyDto.getId());
        specialty.setCategory(category);
        Specialty returnedSpecialty = save(specialty);
        SpecialtyDto returnedSpecialtyDto = modelMapper.map(returnedSpecialty, SpecialtyDto.class);
        return ResponseEntity.ok(returnedSpecialtyDto);
    }
}
