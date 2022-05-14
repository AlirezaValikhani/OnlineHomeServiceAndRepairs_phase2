package org.maktab.OnlineServicesAndRepairsPhase2.service.interfaces;

import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.CategoryDto;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Category;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CategoryService {
    Category findByName(String name);
    ResponseEntity<CategoryDto> addCategory(CategoryDto categoryDto);
    ResponseEntity<List<CategoryDto>> findAll();
    Category getById(Long id);
}
