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
    private final DozerBeanMapper mapper;
    private final ModelMapper modelMapper;


    public CategoryController(CategoryServiceImpl categoryService) {
        this.categoryService = categoryService;
        this.mapper = new DozerBeanMapper();
        this.modelMapper = new ModelMapper();
    }

    @PostMapping("/save")
    public ResponseEntity<CategoryDto> save(@RequestBody CategoryDto categoryDto){
        Category category = mapper.map(categoryDto, Category.class);
        Category returnedCategory = categoryService.save(category);
        CategoryDto returnedCategoryDto = modelMapper.map(returnedCategory, CategoryDto.class);
       return ResponseEntity.ok(returnedCategoryDto);
    }

    @GetMapping("/showAllCategories")
    public ResponseEntity<List<CategoryDto>> showAllCategories(){
        List<Category> categoryList = categoryService.findAll();
        List<CategoryDto> result = new ArrayList<>();
        for (Category c:categoryList) {
            CategoryDto returnedCategoryDto = modelMapper.map(c, CategoryDto.class);
            result.add(returnedCategoryDto);
        }
        return ResponseEntity.ok(result);
    }
}
