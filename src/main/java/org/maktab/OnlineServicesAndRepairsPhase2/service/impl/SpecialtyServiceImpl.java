package org.maktab.OnlineServicesAndRepairsPhase2.service.impl;

import org.maktab.OnlineServicesAndRepairsPhase2.entity.Specialty;
import org.maktab.OnlineServicesAndRepairsPhase2.repository.SpecialtyRepository;
import org.maktab.OnlineServicesAndRepairsPhase2.service.interfaces.SpecialtyService;

import javax.transaction.Transactional;

@org.springframework.stereotype.Service
@Transactional
public class SpecialtyServiceImpl implements SpecialtyService {
    private final SpecialtyRepository specialtyRepository;

    public SpecialtyServiceImpl(SpecialtyRepository serviceRepository) {
        this.specialtyRepository = serviceRepository;
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
}
