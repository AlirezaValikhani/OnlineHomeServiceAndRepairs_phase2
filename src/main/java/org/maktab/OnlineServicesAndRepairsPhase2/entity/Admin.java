package org.maktab.OnlineServicesAndRepairsPhase2.entity;

import org.maktab.OnlineServicesAndRepairsPhase2.entity.base.User;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.enums.UserType;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("admin")
public class Admin extends User {
    public Admin(String firstName, String lastName, String nationalCode, String password, UserType userType) {
        super(firstName, lastName, nationalCode, password,userType);
    }

    public Admin(String password) {
        super(password);
    }

    public Admin() {
    }
}
