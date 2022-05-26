package org.maktab.OnlineServicesAndRepairsPhase2.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.base.User;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.enums.UserStatus;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.enums.UserType;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
    @OneToOne(mappedBy = "customer")
    private Wallet wallet;

    public Customer(String password) {
        super(password);
    }

    public Customer(String firstName, String lastName, String emailAddress, String nationalCode, String password, Integer credit, UserStatus userStatus, UserType userType) {
        super(firstName, lastName, emailAddress, nationalCode, password, credit, userStatus,userType);
    }
}
