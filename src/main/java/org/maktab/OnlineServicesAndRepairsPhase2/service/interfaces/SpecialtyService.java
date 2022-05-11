package org.maktab.OnlineServicesAndRepairsPhase2.service.interfaces;

import org.maktab.OnlineServicesAndRepairsPhase2.entity.Specialty;

public interface SpecialtyService {
    Specialty findByName(String serviceName);
    Specialty getById(Long id);
    Specialty save(Specialty specialty);
}
