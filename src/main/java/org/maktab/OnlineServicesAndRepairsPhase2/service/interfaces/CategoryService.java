package org.maktab.OnlineServicesAndRepairsPhase2.service.interfaces;

import org.maktab.OnlineServicesAndRepairsPhase2.entity.Category;

public interface CategoryService {
    Category findByName(String name);
    Category addCategory(Category category);
    Iterable<Category> findAll();
    Category getById(Long id);
}
