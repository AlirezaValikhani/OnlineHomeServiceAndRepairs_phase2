package org.maktab.OnlineServicesAndRepairsPhase2.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.base.User;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.enums.UserStatus;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.sql.Timestamp;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@DiscriminatorValue("customer")
public class Customer extends User {
    @OneToMany(mappedBy = "customer")
    private Set<Order> orders;
    @OneToMany(mappedBy = "customer")
    private Set<Comment> comments;

    public Customer(String password) {
        super(password);
    }

    public Customer(String firstName, String lastName, String emailAddress, String nationalCode, String password, Timestamp registrationDate, Integer credit, Double balance, UserStatus userStatus) {
        super(firstName, lastName, emailAddress, nationalCode, password, registrationDate, credit, balance, userStatus);
    }
}
