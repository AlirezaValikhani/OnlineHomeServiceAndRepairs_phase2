package org.maktab.OnlineServicesAndRepairsPhase2.entity.base;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.enums.UserStatus;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.enums.UserType;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_role",discriminatorType = DiscriminatorType.STRING)
@Entity
@Table(name = "users")
public class User extends BaseEntity<Long> {
    private String firstName;
    private String lastName;
    @Column(unique = true)
    @Email
    private String emailAddress;
    @Column(unique = true)
    private String nationalCode;
    @Size(min = 8,message = "Password should have at least 8 characters!!!")
    private String password;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date registrationDate;
    private Integer credit;
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;
    @Enumerated(EnumType.STRING)
    private UserType userType;
    @Transient
    public String getDiscriminatorValue(){
        DiscriminatorValue val = this.getClass().getAnnotation( DiscriminatorValue.class );

        return val == null ? null : val.value();
    }

    public User(String password) {
        this.password = password;
    }

    public User(String firstName, String lastName, String nationalCode, String password,UserType userType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationalCode = nationalCode;
        this.password = password;
        this.userType = userType;
    }

    public User(Long aLong, String password) {
        super(aLong);
        this.password = password;
    }

    public User(String firstName, String lastName, String emailAddress, String nationalCode, String password, Integer credit, UserStatus userStatus, UserType userType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.nationalCode = nationalCode;
        this.password = password;
        this.credit = credit;
        this.userStatus = userStatus;
        this.userType = userType;
    }

    public User(Long aLong) {
        super(aLong);
    }

    @Override
    public String toString() {
        return super.toString() +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", nationalCode='" + nationalCode + '\'' +
                ", password='" + password + '\'' +
                ", credit=" + credit +
                ", userStatus=" + userStatus;
    }
}
