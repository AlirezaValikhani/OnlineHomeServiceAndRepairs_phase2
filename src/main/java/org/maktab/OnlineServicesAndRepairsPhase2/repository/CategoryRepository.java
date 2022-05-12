package org.maktab.OnlineServicesAndRepairsPhase2.repository;

import com.sun.xml.bind.v2.model.core.ID;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    List<Category> findAllById(Long id);
    Category findByName(String name);

}