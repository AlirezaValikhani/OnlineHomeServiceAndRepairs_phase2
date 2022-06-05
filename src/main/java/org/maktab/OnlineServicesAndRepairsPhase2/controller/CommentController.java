package org.maktab.OnlineServicesAndRepairsPhase2.controller;

import org.dozer.DozerBeanMapper;
import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.CommentDto;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Comment;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Customer;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Expert;
import org.maktab.OnlineServicesAndRepairsPhase2.exceptions.NotFoundCustomerException;
import org.maktab.OnlineServicesAndRepairsPhase2.exceptions.NotFoundExpertException;
import org.maktab.OnlineServicesAndRepairsPhase2.service.impl.CommentServiceImpl;
import org.maktab.OnlineServicesAndRepairsPhase2.service.impl.CustomerServiceImpl;
import org.maktab.OnlineServicesAndRepairsPhase2.service.impl.ExpertServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comment")
public class CommentController {
    private final CommentServiceImpl commentService;
    private final CustomerServiceImpl customerService;
    private final ExpertServiceImpl expertService;
    private final DozerBeanMapper mapper;
    private final ModelMapper modelMapper;

    public CommentController(CommentServiceImpl commentService, CustomerServiceImpl customerService, ExpertServiceImpl expertService) {
        this.commentService = commentService;
        this.customerService = customerService;
        this.expertService = expertService;
        this.mapper = new DozerBeanMapper();
        this.modelMapper = new ModelMapper();
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody CommentDto commentDto) {
        Customer customer = customerService.getById(commentDto.getCustomerId());
        if(customer == null)
            throw new NotFoundCustomerException();
        Expert expert = expertService.getById(commentDto.getExpertId());
        if(expert == null)
            throw new NotFoundExpertException();
        Comment toSaveComment = new Comment(commentDto.getComment(),customer,expert);
        String message = commentService.addComment(toSaveComment);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }
}
