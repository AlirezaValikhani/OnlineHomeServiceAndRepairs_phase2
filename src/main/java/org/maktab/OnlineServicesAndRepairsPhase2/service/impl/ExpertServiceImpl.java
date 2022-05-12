package org.maktab.OnlineServicesAndRepairsPhase2.service.impl;

import org.maktab.OnlineServicesAndRepairsPhase2.entity.Expert;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.enums.UserStatus;
import org.maktab.OnlineServicesAndRepairsPhase2.repository.ExpertRepository;
import org.maktab.OnlineServicesAndRepairsPhase2.service.interfaces.ExpertService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

@Service
@Transactional
public class ExpertServiceImpl implements ExpertService {
    private final ExpertRepository expertRepository;
    private Integer credit = 10;

    public ExpertServiceImpl(ExpertRepository expertRepository) {
        this.expertRepository = expertRepository;
    }

    @Override
    public Expert findByNationalCode(String userName) {
        return expertRepository.findByNationalCode(userName);
    }

    @Override
    public Expert findByEmailAddress(String email) {
        return expertRepository.findByEmailAddress(email);
    }

    @Override
    public List<Expert> findAcceptedExperts() {
        return expertRepository.findAcceptedExperts();
    }

    @Override
    public List<Expert> findWaitingApprovalExperts() {
        return expertRepository.findWaitingApprovalExperts();
    }

    @Override
    public Expert findExpertServices(Long expertId) {
        return expertRepository.findExpertServices(expertId);
    }

    @Override
    public Expert save(Expert expert) {
        Timestamp registrationDate = new Timestamp(System.currentTimeMillis());
        expert.setRegistrationDate(registrationDate);
        expert.setUserStatus(UserStatus.WAITING_APPROVAL);
        return expertRepository.save(expert);
    }

    @Override
    public Expert changePassword(Expert expert) {
        Expert returnedExpert = expertRepository.getById(expert.getId());
        returnedExpert.setPassword(expert.getPassword());
            return expertRepository.save(returnedExpert);
    }

    @Override
    public List<Expert> waitingApprovalExperts() {
        return expertRepository.waitingApprovalExperts();
    }

    @Override
    public Expert expertApproval(Long id) {
        Expert expert = expertRepository.getById(id);
        expert.setUserStatus(UserStatus.ACCEPTED);
        return expert;
    }

    @Override
    public Expert getById(Long id) {
        return expertRepository.getById(id);
    }


    /*public byte[] getImage(byte[] image) {
            try {
                BufferedImage bufferedImage= ImageIO.read(image);
                ByteArrayOutputStream byteOutStream=new ByteArrayOutputStream();
                ImageIO.write(bufferedImage, "png", byteOutStream);
                return byteOutStream.toByteArray();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        return null;
    }*/
}
