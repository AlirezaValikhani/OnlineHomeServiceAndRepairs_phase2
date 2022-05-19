package org.maktab.OnlineServicesAndRepairsPhase2.controller;

import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.CommentDto;
import org.maktab.OnlineServicesAndRepairsPhase2.service.impl.CommentServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comment")
public class CommentController {
    private final CommentServiceImpl commentService;

    public CommentController(CommentServiceImpl commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/saveCommentAndRating")
    public ResponseEntity<CommentDto> save(@RequestBody CommentDto commentDto) {
        return commentService.addComment(commentDto);
    }
}
