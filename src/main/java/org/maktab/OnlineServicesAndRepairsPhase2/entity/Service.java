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
public class Service extends BaseEntity<Long> {
    @Column(nullable = false,unique = true)
    private String name;
    private Double basePrice;
    private String description;
    @OneToMany(mappedBy = "service")////mistake
    private Set<Order> orders;
    @ManyToOne(fetch = FetchType.EAGER)
    private Category category;
    @ManyToMany(mappedBy = "services")
    private Set<Expert> experts;

    public Service(String name, Double basePrice, String description) {
        this.name = name;
        this.basePrice = basePrice;
        this.description = description;
    }

    public Service(String name) {
        this.name = name;
    }

    public Service(Long aLong) {
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