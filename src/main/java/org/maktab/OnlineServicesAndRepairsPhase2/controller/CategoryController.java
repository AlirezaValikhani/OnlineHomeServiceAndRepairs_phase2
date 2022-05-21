package org.maktab.OnlineServicesAndRepairsPhase2.controller;

import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.CategoryDto;
import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.CustomerDto;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Category;
import org.maktab.OnlineServicesAndRepairsPhase2.service.impl.CategoryServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.dozer.DozerBeanMapper;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryServiceImpl categoryService;
    private final DozerBeanMapper mapper;
    private final ModelMapper modelMapper;

    public CategoryController(CategoryServiceImpl categoryService) {
        this.categoryService = categoryService;
        this.mapper = new DozerBeanMapper();
        this.modelMapper = new ModelMapper();
    }

    @PostMapping("/save")
    public ResponseEntity<CategoryDto> save(@Valid @RequestBody CategoryDto categoryDto) {
        Category category = mapper.map(categoryDto, Category.class);
        Category returnedCategory = categoryService.addCategory(category);
        CategoryDto returnedCategoryDto = modelMapper.map(returnedCategory, CategoryDto.class);
        return new ResponseEntity<>(returnedCategoryDto, HttpStatus.CREATED);
    }

    @GetMapping("/showAllCategories")
    public ResponseEntity<List<CategoryDto>> showAllCategories() {
        List<Category> objectResult = new ArrayList<>();
        List<CategoryDto> result = new ArrayList<>();
        Iterable<Category> iterable = categoryService.findAll();
        iterable.forEach(objectResult::add);
        objectResult.forEach(category -> result.add(modelMapper.map(category, CategoryDto.class)));
        return ResponseEntity.ok(result);
    }
}
