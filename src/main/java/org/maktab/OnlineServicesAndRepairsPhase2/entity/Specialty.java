package org.maktab.OnlineServicesAndRepairsPhase2.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.base.BaseEntity;

import javax.persistence.*;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Specialty extends BaseEntity<Long> {
    @Column(unique = true)
    private String name;
    private Double basePrice;
    private String description;
    @OneToMany(mappedBy = "service")
    private Set<Order> orders;
    @ManyToOne(fetch = FetchType.EAGER)
    private Category category;
    @ManyToMany(mappedBy = "services")
    private Set<Expert> experts;

    public Specialty(String name, Double basePrice, String description) {
        this.name = name;
        this.basePrice = basePrice;
        this.description = description;
    }

    public Specialty(String name) {
        this.name = name;
    }

    public Specialty(Long aLong) {
        super(aLong);
    }

    @Override
    public String toString() {
        return "Service{" + super.toString() +
                ", name='" + name + '\'' +
                ", basePrice=" + basePrice +
                ", description='" + description + '\'' +
                '}';
    }
}