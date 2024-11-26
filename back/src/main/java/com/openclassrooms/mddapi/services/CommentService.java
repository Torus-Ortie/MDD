package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.repositories.CommentRepository;
import com.openclassrooms.mddapi.repositories.UserRepository;
import com.openclassrooms.mddapi.repositories.ArticleRepository;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private  CommentRepository commentRepository;
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private  ArticleRepository articleRepository;

    /**
     * Get all the comment of a specific article
     *
     * @param articleId - The unique id of an article
     * @return A list of comment mapped as Comment
     * 
     */
    public List<Comment> getCommentsByArticleId(Long articleId) {
        return commentRepository.findByArticleId(articleId);
    }

    /**
     * Create a new comment to an article
     *
     * @param comment - The comment to create mapped as Comment
     * @return The comment to create mapped as Comment
     * 
     */
    public Comment createComment(Comment comment) {
        if (!userRepository.existsById(comment.getUserId()) || !articleRepository.existsById(comment.getArticleId())) {
            throw new EntityNotFoundException();
        }
        Comment savedComment = commentRepository.save(comment);

        Article article = articleRepository.findById(comment.getArticleId())
            .orElseThrow(() -> new EntityNotFoundException("Article not found with id " + comment.getArticleId()));

        article.getCommentIds().add(savedComment.getId());
        articleRepository.save(article);

        return savedComment;
    }
}