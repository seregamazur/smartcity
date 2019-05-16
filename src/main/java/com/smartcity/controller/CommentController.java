package com.smartcity.controller;

import com.smartcity.dto.CommentDto;
import com.smartcity.service.CommentService;
import com.smartcity.service.CommentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private CommentService commentService;

    @Autowired
    public CommentController(CommentServiceImpl commentService) {
        this.commentService = commentService;
    }

    @GetMapping(value = "/{id}")
    public CommentDto findById(@PathVariable("id") Long id) {
        return commentService.findById(id);
    }

    @PutMapping(value = "/{id}")
    public CommentDto updateComment(
            @PathVariable("id") Long id,
            @RequestBody CommentDto commentDto)  {
        commentDto.setId(id);
        return commentService.update(commentDto);
    }

    @DeleteMapping(value = "/{id}")
    public boolean deleteComment(@PathVariable("id") Long id) {
        return commentService.delete(id);
    }

    @GetMapping("/findAllByUserId = {id}")
    public List<CommentDto> findByAllByTaskId(@RequestParam("id") Long id) {
        return commentService.findAllByTaskId(id);
    }

    @GetMapping("/findAllByTaskId = {id}")
    public List<CommentDto> findByAllByUserId(@RequestParam("id") Long id) {
        return commentService.findAllByUserId(id);
    }

    @PostMapping("")
    public CommentDto createComment(@RequestBody CommentDto commentDto) {
        return commentService.create(commentDto);
    }

}
