package org.maktab.OnlineServicesAndRepairsPhase2.service.interfaces;

import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.CommentDto;
import org.springframework.http.ResponseEntity;

public interface CommentService {
    ResponseEntity<CommentDto> addCommentAndRating(CommentDto commentDto);
}
