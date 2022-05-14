package org.maktab.OnlineServicesAndRepairsPhase2.service.interfaces;

import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.SpecialtyDto;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Specialty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface SpecialtyService {
    Specialty findByName(String serviceName);
    Specialty getById(Long id);
    Specialty save(Specialty specialty);
    ResponseEntity<SpecialtyDto> save(SpecialtyDto specialtyDto);
    ResponseEntity<SpecialtyDto> addCategoryToSpecialty(SpecialtyDto specialtyDto);
}
