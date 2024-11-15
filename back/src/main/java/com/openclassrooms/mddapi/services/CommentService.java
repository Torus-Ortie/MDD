package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dto.CommentDTO;
import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repositories.CommentRepository;
import com.openclassrooms.mddapi.repositories.UserRepository;
import com.openclassrooms.mddapi.repositories.ArticleRepository;
import jakarta.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private  CommentRepository commentRepository;
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private  ArticleRepository articleRepository;
    @Autowired
    private ModelMapper modelMapper; 

    public List<CommentDTO> getCommentsByArticleId(Long articleId) {
        return commentRepository.findByArticleId(articleId).stream()
                .map(comment -> {
                    User user = userRepository.findById(comment.getUserId())
                            .orElseThrow(() -> new EntityNotFoundException("User not found with id " + comment.getUserId()));
                    CommentDTO commentDTO = modelMapper.map(comment, CommentDTO.class);
                    commentDTO.setName(user.getName()); // Set the authorName
                    return commentDTO;
                })
                .collect(Collectors.toList());
    }

    public CommentDTO createComment(CommentDTO commentDTO) {
        if (!userRepository.existsById(commentDTO.getUserId()) || !articleRepository.existsById(commentDTO.getArticleId())) {
            throw new EntityNotFoundException();
        }

        Comment comment = modelMapper.map(commentDTO, Comment.class);
        Comment savedComment = commentRepository.save(comment);

        Article article = articleRepository.findById(commentDTO.getArticleId())
                .orElseThrow(() -> new EntityNotFoundException("Article not found with id " + commentDTO.getArticleId()));

        article.getCommentIds().add(savedComment.getId());
        articleRepository.save(article);

        User user = userRepository.findById(commentDTO.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + commentDTO.getUserId()));
        CommentDTO savedCommentDTO = modelMapper.map(savedComment, CommentDTO.class);
        savedCommentDTO.setName(user.getName());

        return savedCommentDTO;
    }
}