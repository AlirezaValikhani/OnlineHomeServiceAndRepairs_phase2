package org.maktab.OnlineServicesAndRepairsPhase2.service.impl;

import org.maktab.OnlineServicesAndRepairsPhase2.configuration.security.SecurityUtil;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Admin;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.base.User;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.enums.Role;
import org.maktab.OnlineServicesAndRepairsPhase2.repository.AdminRepository;
import org.maktab.OnlineServicesAndRepairsPhase2.service.interfaces.AdminService;
import org.maktab.OnlineServicesAndRepairsPhase2.util.CustomPasswordEncoder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;


    public AdminServiceImpl(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Admin findByNationalCode(String userName) {
        return adminRepository.findByNationalCode(userName);
    }

    @Override
    public Admin addAdminByDefault() {
        String firstName = "admin";
        String lastName = "admin";
        String nationalCode = "adminadmin";
        String password = CustomPasswordEncoder.hashPassword("admin1234");
        Admin admin = new Admin(firstName, lastName, nationalCode, password, Role.ROLE_ADMIN);
        return adminRepository.save(admin);
    }

    @Override
    public Admin changeAdminPassword(Admin admin) {
        User user = SecurityUtil.getCurrentUser();
        Admin foundedAdmin = adminRepository.getById(user.getId());
        foundedAdmin.setPassword(admin.getPassword());
        return adminRepository.save(foundedAdmin);
    }

    @Override
    public Specification<User> userSpecification(User user){
        return (userRoot, query, criteriaBuilder)
                -> {
            CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
            criteriaQuery.select(userRoot);

            List<Predicate> predicates = new ArrayList<>();
            if(user.getRole() != null )
                predicates.add(criteriaBuilder.equal(userRoot.get("userType"),user.getRole()));
            if(user.getFirstName() != null && !user.getFirstName().isEmpty())
                predicates.add(criteriaBuilder.equal(userRoot.get("firstName"),user.getFirstName()));
            if(user.getLastName() != null && !user.getLastName().isEmpty())
                predicates.add(criteriaBuilder.equal(userRoot.get("lastName"),user.getLastName()));
            if(user.getNationalCode() != null && !user.getNationalCode().isEmpty())
                predicates.add(criteriaBuilder.equal(userRoot.get("nationalCode"),user.getNationalCode()));
            if(user.getEmailAddress() != null && !user.getEmailAddress().isEmpty())
                predicates.add(criteriaBuilder.equal(userRoot.get("email"),user.getEmailAddress()));

            criteriaQuery.where(predicates.toArray(new Predicate[0]));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    @Override
    public List<User> findAllByFilter(Specification<User> userSpecification){
        return adminRepository.findAll(userSpecification);
    }
}
