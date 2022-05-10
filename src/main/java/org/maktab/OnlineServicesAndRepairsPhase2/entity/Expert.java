package org.maktab.OnlineServicesAndRepairsPhase2.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.base.User;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.enums.UserStatus;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@DiscriminatorValue("expert")
public class Expert extends User {
    @Column(name = "image")
    @Lob
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
    private Set<Service> services;
    @OneToMany(mappedBy = "expert")
    private Set<Comment> comments;

    public Expert(String password) {
        super(password);
    }

    public Expert(String firstName, String lastName, String emailAddress, String nationalCode, String password, Timestamp registrationDate, Integer credit, Double balance, UserStatus userStatus, byte[] image, String city, Set<Service> services) {
        super(firstName, lastName, emailAddress, nationalCode, password, registrationDate, credit, balance, userStatus);
        this.image = image;
        this.city = city;
        this.services = services;
    }

    @Override
    public String toString() {
        return "Expert{" + super.toString() +
                "city='" + city + '\'' +
                ", services=" + services +
                '}';
    }
}
