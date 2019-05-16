package com.smartcity.mapperDto;

import com.smartcity.domain.Comment;
import com.smartcity.dto.CommentDto;
import org.springframework.stereotype.Component;

@Component
public class CommentDtoMapper {

    public CommentDto commentToCommentDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setDescription(comment.getDescription());
        commentDto.setCreatedDate(comment.getCreatedDate());
        commentDto.setUpdatedDate(comment.getUpdatedDate());
        commentDto.setTaskId(comment.getTaskId());
        commentDto.setUserId(comment.getUserId());
        return commentDto;
    }

    public Comment commentDtoToComment(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setId(commentDto.getId());
        comment.setDescription(commentDto.getDescription());
        comment.setCreatedDate(commentDto.getCreatedDate());
        comment.setUpdatedDate(commentDto.getUpdatedDate());
        comment.setUserId(commentDto.getUserId());
        comment.setTaskId(commentDto.getTaskId());
        return comment;
    }
}
