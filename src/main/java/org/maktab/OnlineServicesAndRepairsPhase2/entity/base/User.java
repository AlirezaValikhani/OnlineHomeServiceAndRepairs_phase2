package org.maktab.OnlineServicesAndRepairsPhase2.entity.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.enums.UserStatus;

import javax.persistence.*;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type",discriminatorType = DiscriminatorType.STRING)
@Entity
@Table(name = "users")
public class User extends BaseEntity<Long> {
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(unique = true)
    private String emailAddress;
    @Column(nullable = false,unique = true)
    private String nationalCode;
    @Column(nullable = false,length = 8)
    private String password;
    private Timestamp registrationDate;
    private Integer credit;
    private Double balance;
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;
    @Transient
    public String getDiscriminatorValue(){
        DiscriminatorValue val = this.getClass().getAnnotation( DiscriminatorValue.class );

        return val == null ? null : val.value();
    }

    public User(String password) {
        this.password = password;
    }

    public User(Long aLong, String firstName, String lastName, String emailAddress, String nationalCode, Timestamp registrationDate, Integer credit, Double balance, UserStatus userStatus) {
        super(aLong);
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.nationalCode = nationalCode;
        this.registrationDate = registrationDate;
        this.credit = credit;
        this.balance = balance;
        this.userStatus = userStatus;
    }

    public User(String firstName, String lastName, String nationalCode, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationalCode = nationalCode;
        this.password = password;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", nationalCode='" + nationalCode + '\'' +
                ", password='" + password + '\'' +
                ", registrationDate=" + registrationDate +
                ", credit=" + credit +
                ", balance=" + balance +
                ", userStatus=" + userStatus;
    }
}
