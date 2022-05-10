package org.maktab.OnlineServicesAndRepairsPhase2.service.interfaces;

import org.maktab.OnlineServicesAndRepairsPhase2.entity.Category;

public interface CategoryService {
    Category save(Category category);
    Category findByName(String name);
    void addCategory(Category category);
}
