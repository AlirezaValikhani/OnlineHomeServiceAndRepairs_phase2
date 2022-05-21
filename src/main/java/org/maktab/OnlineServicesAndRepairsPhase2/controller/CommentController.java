package org.maktab.OnlineServicesAndRepairsPhase2.controller;

import org.dozer.DozerBeanMapper;
import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.CommentDto;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Comment;
import org.maktab.OnlineServicesAndRepairsPhase2.service.impl.CommentServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comment")
public class CommentController {
    private final CommentServiceImpl commentService;
    private final DozerBeanMapper mapper;
    private final ModelMapper modelMapper;

    public CommentController(CommentServiceImpl commentService) {
        this.commentService = commentService;
        this.mapper = new DozerBeanMapper();
        this.modelMapper = new ModelMapper();
    }

    @PostMapping("/saveCommentAndRating")
    public ResponseEntity<CommentDto> save(@RequestBody CommentDto commentDto) {
        Comment comment = mapper.map(commentDto, Comment.class);
        Comment returnedComment = commentService.addComment(comment);
        CommentDto returnedCommentDto = modelMapper.map(returnedComment, CommentDto.class);
        return new ResponseEntity<>(returnedCommentDto, HttpStatus.CREATED);
    }
}
