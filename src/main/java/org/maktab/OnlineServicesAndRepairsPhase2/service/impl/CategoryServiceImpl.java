package org.maktab.OnlineServicesAndRepairsPhase2.service.impl;

import org.dozer.DozerBeanMapper;
import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.CategoryDto;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Category;
import org.maktab.OnlineServicesAndRepairsPhase2.repository.CategoryRepository;
import org.maktab.OnlineServicesAndRepairsPhase2.service.interfaces.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService{
    private final CategoryRepository categoryRepository;
    private final DozerBeanMapper mapper;
    private final ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
        this.mapper = new DozerBeanMapper();
        this.modelMapper = new ModelMapper();
    }

    @Override
    public Category findByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public ResponseEntity<CategoryDto> addCategory(CategoryDto categoryDto){
        Category category = mapper.map(categoryDto, Category.class);
        Category returnedCategory = categoryRepository.save(category);
        CategoryDto returnedCategoryDto = modelMapper.map(returnedCategory, CategoryDto.class);
        return ResponseEntity.ok(returnedCategoryDto);
    }

    @Override
    public ResponseEntity<List<CategoryDto>> findAll() {
        Iterable<Category> iterable = categoryRepository.findAll();
        List<Category> objectResult = new ArrayList<>();
        List<CategoryDto> result = new ArrayList<>();
        iterable.forEach(objectResult::add);
        objectResult.forEach(category -> result.add(modelMapper.map(category, CategoryDto.class)));
        return ResponseEntity.ok(result);
    }

    @Override
    public Category getById(Long id) {
        return categoryRepository.getById(id);
    }
}
