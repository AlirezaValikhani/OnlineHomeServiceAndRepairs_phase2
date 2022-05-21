package org.maktab.OnlineServicesAndRepairsPhase2.service.impl;

import org.dozer.DozerBeanMapper;
import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.CommentDto;
import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.CustomerDto;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Comment;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Customer;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Expert;
import org.maktab.OnlineServicesAndRepairsPhase2.exceptions.NotFoundCustomerException;
import org.maktab.OnlineServicesAndRepairsPhase2.exceptions.NotFoundExpertException;
import org.maktab.OnlineServicesAndRepairsPhase2.repository.CommentRepository;
import org.maktab.OnlineServicesAndRepairsPhase2.service.interfaces.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final CustomerServiceImpl customerService;
    private final ExpertServiceImpl expertService;

    public CommentServiceImpl(CommentRepository commentRepository, CustomerServiceImpl customerService, ExpertServiceImpl expertService) {
        this.commentRepository = commentRepository;
        this.customerService = customerService;
        this.expertService = expertService;
    }


    @Override
    public Comment addComment(Comment comment) {
        Customer customer = customerService.getById(comment.getCustomer().getId());
        if(customer == null)
            throw new NotFoundCustomerException();
        Expert expert = expertService.getById(comment.getExpert().getId());
        if(expert == null)
            throw new NotFoundExpertException();
        Comment toSaveComment = new Comment(comment.getComment(),customer,expert);
        return commentRepository.save(toSaveComment);
    }
}
