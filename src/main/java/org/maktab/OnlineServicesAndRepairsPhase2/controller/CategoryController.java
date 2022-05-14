package org.maktab.OnlineServicesAndRepairsPhase2.controller;

import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.CategoryDto;
import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.CustomerDto;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Category;
import org.maktab.OnlineServicesAndRepairsPhase2.service.impl.CategoryServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.dozer.DozerBeanMapper;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryServiceImpl categoryService;

    public CategoryController(CategoryServiceImpl categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/save")
    public ResponseEntity<CategoryDto> save(@RequestBody CategoryDto categoryDto) {
        return categoryService.addCategory(categoryDto);
    }

    @GetMapping("/showAllCategories")
    public ResponseEntity<List<CategoryDto>> showAllCategories() {
        return categoryService.findAll();
    }
}
