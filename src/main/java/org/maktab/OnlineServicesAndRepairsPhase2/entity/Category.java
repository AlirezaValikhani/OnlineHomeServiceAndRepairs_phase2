package org.maktab.OnlineServicesAndRepairsPhase2.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.base.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Category extends BaseEntity<Long> {
    @Column(unique = true)
    @NotNull
    private String name;
    @OneToOne
    private Category superCategory;
    @OneToMany(mappedBy = "category",fetch = FetchType.EAGER)
    private Set<Specialty> specialties;

    public Category(String name, Category superCategory) {
        this.name = name;
        this.superCategory = superCategory;
    }

    public Category(Long aLong) {
        super(aLong);
    }

    @Override
    public String toString() {
        return "Category { " + super.toString() +
                ", name = '" + name + '\'' +
                ", superCategory = " + superCategory +
                ", services = " + specialties +
                '}';
    }
}
