package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.repositories.ArticleRepository;
import com.openclassrooms.mddapi.repositories.UserRepository;
import com.openclassrooms.mddapi.repositories.ThemeRepository;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {


    @Autowired
    private  ThemeRepository themeRepository;
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private  ArticleRepository articleRepository;

    /**
     * Get all the articles attached to the theme subscribe by the user
     *
     * @param themeIds - The list of theme subscribe by the user
     * @return A list of article mapped as Article
     * 
     */
    public List<Article> getArticlesForSubscribedThemes(List<Long> themeIds) {
        List<Article> articles = new ArrayList<>();
        for (Long themeId : themeIds) {
            List<Article> themeArticles = articleRepository.findByThemeId(themeId);
            for (Article article : themeArticles) {
                articles.add(article);
            }
        }
        return articles;
    }

    /**
     * Create a new article
     *
     * @param article - The article to create mapped as Article
     * @return The article created mapped as Article
     * 
     */
    public Article createArticle(Article article) {
        if (!userRepository.existsById(article.getUserId()) || !themeRepository.existsById(article.getThemeId())) {
            throw new EntityNotFoundException();
        }

        article.setUserId(article.getUserId());
        article.setThemeId(article.getThemeId());

        article.setCommentIds(new ArrayList<>());

        Article savedArticle = articleRepository.save(article);

        return savedArticle;
    }

    /**
     * Get a specific article by id
     *
     * @param id - The unique id of an article
     * @return The article mapped as Article
     * 
     */
    public Optional<Article> getArticleById(Long id) {
        return articleRepository.findById(id);
}
}