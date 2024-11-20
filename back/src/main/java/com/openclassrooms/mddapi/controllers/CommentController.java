package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.CommentDTO;
import com.openclassrooms.mddapi.services.CommentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles/{articleId}/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping
    public List<CommentDTO> getCommentsByArticleId(@PathVariable Long articleId) {
        return commentService.getCommentsByArticleId(articleId);
    }

    @PostMapping
    public CommentDTO createComment(@PathVariable Long articleId, @RequestBody CommentDTO commentDTO) {
        commentDTO.setArticleId(articleId);
        return commentService.createComment(commentDTO);
    }
}