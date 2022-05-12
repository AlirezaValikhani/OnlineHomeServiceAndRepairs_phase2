package org.maktab.OnlineServicesAndRepairsPhase2.service.impl;

import org.maktab.OnlineServicesAndRepairsPhase2.entity.Category;
import org.maktab.OnlineServicesAndRepairsPhase2.repository.CategoryRepository;
import org.maktab.OnlineServicesAndRepairsPhase2.service.interfaces.CategoryService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService{
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category findByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public void addCategory(Category category){
        categoryRepository.save(category);
    }

    @Override
    public List<Category> findAll() {
        Iterable<Category> iterable = categoryRepository.findAll();
        List<Category> result = new ArrayList<>();
        for (Category c:iterable) {
            result.add(c);
        }
        return result;
    }

    @Override
    public Category getById(Long id) {
        return categoryRepository.getById(id);
    }
}
