package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.CommentDTO;
import com.openclassrooms.mddapi.services.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles/{articleId}/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

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