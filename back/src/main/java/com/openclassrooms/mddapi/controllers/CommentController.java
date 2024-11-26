package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.CommentDTO;
import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repositories.UserRepository;
import com.openclassrooms.mddapi.services.CommentService;

import jakarta.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/articles/{articleId}/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public List<CommentDTO> getCommentsByArticleId(@PathVariable Long articleId) {
        return commentService.getCommentsByArticleId(articleId).stream()
            .map(comment -> {
                User user = userRepository.findById(comment.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException("User not found with id " + comment.getUserId()));
                CommentDTO commentDTO = modelMapper.map(comment, CommentDTO.class);
                commentDTO.setName(user.getName());
                return commentDTO;
            })
            .collect(Collectors.toList());
    }

    @PostMapping
    public CommentDTO createComment(@PathVariable Long articleId, @RequestBody CommentDTO commentDTO) {
        commentDTO.setArticleId(articleId);

        Comment comment = modelMapper.map(commentDTO, Comment.class);
        CommentDTO savedCommentDTO = modelMapper.map(commentService.createComment(comment), CommentDTO.class);
        
        User user = userRepository.findById(comment.getUserId())
            .orElseThrow(() -> new EntityNotFoundException("User not found with id " + comment.getUserId()));
        savedCommentDTO.setName(user.getName());

        return savedCommentDTO;
    }
}