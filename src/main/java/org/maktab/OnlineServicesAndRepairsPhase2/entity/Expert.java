package org.maktab.OnlineServicesAndRepairsPhase2.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.base.User;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.enums.UserStatus;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.enums.Role;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@DiscriminatorValue("expert")
public class Expert extends User {
    private byte[] image;
    private String city;
    @OneToMany(mappedBy = "expert")
    private Set<Order> orders;
    @OneToMany(mappedBy = "expert")
    private Set<Offer> offers;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "Expert_Service",
            joinColumns = { @JoinColumn(name = "expert_id")},
            inverseJoinColumns = { @JoinColumn(name = "service_id")}
    )
    private Set<Specialty> services;
    @OneToMany(mappedBy = "expert")
    private Set<Comment> comments;

    public Expert(String password) {
        super(password);
    }

    public Expert(String firstName, String lastName, String emailAddress, String nationalCode, String password,Double balance, Integer credit, UserStatus userStatus, Role role, byte[] image, String city, Set<Specialty> services) {
        super(firstName, lastName, emailAddress, nationalCode, password,balance ,credit, userStatus, role);
        this.image = image;
        this.city = city;
        this.services = services;
    }

    public Expert(Long aLong, String password) {
        super(aLong, password);
    }

    public Expert(Long aLong) {
        super(aLong);
    }

    @Override
    public String toString() {
        return "Expert{" + super.toString() +
                "city='" + city + '\'' +
                ", services=" + services +
                '}';
    }
}
