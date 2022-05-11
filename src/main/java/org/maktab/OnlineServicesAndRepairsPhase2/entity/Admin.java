package org.maktab.OnlineServicesAndRepairsPhase2.entity;

import org.maktab.OnlineServicesAndRepairsPhase2.entity.base.User;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("admin")
public class Admin extends User {
    public Admin(String firstName, String lastName, String nationalCode, String password) {
        super(firstName, lastName, nationalCode, password);
    }

    public Admin(String password) {
        super(password);
    }

    public Admin() {
    }

}
