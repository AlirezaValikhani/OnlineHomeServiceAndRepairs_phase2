package org.maktab.OnlineServicesAndRepairsPhase2.service.impl;

import org.maktab.OnlineServicesAndRepairsPhase2.entity.Category;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Specialty;
import org.maktab.OnlineServicesAndRepairsPhase2.exceptions.NotFoundCategoryException;
import org.maktab.OnlineServicesAndRepairsPhase2.exceptions.NotFoundSpecialtyException;
import org.maktab.OnlineServicesAndRepairsPhase2.repository.SpecialtyRepository;
import org.maktab.OnlineServicesAndRepairsPhase2.service.interfaces.SpecialtyService;

import javax.transaction.Transactional;

@org.springframework.stereotype.Service
@Transactional
public class SpecialtyServiceImpl implements SpecialtyService {
    private final SpecialtyRepository specialtyRepository;
    private final CategoryServiceImpl categoryService;

    public SpecialtyServiceImpl(SpecialtyRepository serviceRepository, CategoryServiceImpl categoryService) {
        this.specialtyRepository = serviceRepository;
        this.categoryService = categoryService;
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
    public Specialty addSpecialty(Specialty specialty,Long categoryId) {
        Category category = categoryService.getById(categoryId);
        if(category == null)
            throw new NotFoundCategoryException();
        specialty.setCategory(category);
        return save(specialty);
    }

    @Override
    public Specialty addCategoryToSpecialty(Specialty specialty) {
        Category category = categoryService.getById(specialty.getCategory().getId());
        Specialty foundedSpecialty = getById(specialty.getId());
        if(foundedSpecialty == null)
            throw new NotFoundSpecialtyException();
        specialty.setCategory(category);
        return save(specialty);
    }
}