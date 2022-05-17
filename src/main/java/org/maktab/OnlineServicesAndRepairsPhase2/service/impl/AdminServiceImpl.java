package org.maktab.OnlineServicesAndRepairsPhase2.service.impl;

import org.dozer.DozerBeanMapper;
import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.AdminDto;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Admin;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.base.User;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.enums.UserType;
import org.maktab.OnlineServicesAndRepairsPhase2.repository.AdminRepository;
import org.maktab.OnlineServicesAndRepairsPhase2.service.interfaces.AdminService;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;
    private final DozerBeanMapper mapper;
    private final ModelMapper modelMapper;

    public AdminServiceImpl(AdminRepository userRepository) {
        this.adminRepository = userRepository;
        this.mapper = new DozerBeanMapper();
        this.modelMapper = new ModelMapper();
    }

    @Override
    public Admin findByNationalCode(String userName) {
        return adminRepository.findByNationalCode(userName);
    }

    @Override
    public AdminDto addAdminByDefault() {
        String firstName = "admin";
        String lastName = "admin";
        String nationalCode = "admin";
        String password = "admin";
        Admin admin = new Admin(firstName, lastName, nationalCode, password, UserType.ADMIN);
        Admin returnedAdmin = adminRepository.save(admin);
        return modelMapper.map(returnedAdmin, AdminDto.class);
    }

    @Override
    public ResponseEntity<AdminDto> changeAdminPassword(AdminDto adminDto) {
        Admin admin = mapper.map(adminDto, Admin.class);
        Admin foundedAdmin = adminRepository.getById(admin.getId());
        foundedAdmin.setPassword(adminDto.getPassword());
        Admin returnedAdmin = adminRepository.save(foundedAdmin);
        AdminDto returnedAdminDto = modelMapper.map(returnedAdmin, AdminDto.class);
        if (returnedAdmin.getPassword().equals(admin.getPassword()))
            return ResponseEntity.ok(returnedAdminDto);
        else return ResponseEntity.notFound().build();
    }

    @Override
    public Specification<User> userSpecification(User user){
        return (userRoot, query, criteriaBuilder)
                -> {
            CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
            criteriaQuery.select(userRoot);

            List<Predicate> predicates = new ArrayList<>();
            if(user.getUserType() != null )
                predicates.add(criteriaBuilder.equal(userRoot.get("userType"),user.getUserType()));
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
