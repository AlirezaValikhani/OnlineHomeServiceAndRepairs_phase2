package org.maktab.OnlineServicesAndRepairsPhase2.entity;

import org.maktab.OnlineServicesAndRepairsPhase2.entity.base.User;

import javax.persistence.Entity;

@Entity
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
