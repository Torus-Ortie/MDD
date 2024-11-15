package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dto.ArticleDTO;
import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.Theme;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repositories.ArticleRepository;
import com.openclassrooms.mddapi.repositories.UserRepository;
import com.openclassrooms.mddapi.repositories.ThemeRepository;
import jakarta.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
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
    @Autowired
    private ModelMapper modelMapper; 

    public List<ArticleDTO> getArticlesForSubscribedThemes(List<Long> themeIds) {
        List<ArticleDTO> articles = new ArrayList<>();
        for (Long themeId : themeIds) {
            List<Article> themeArticles = articleRepository.findByThemeId(themeId);
            for (Article article : themeArticles) {
                User author = userRepository.findById(article.getUserId())
                        .orElseThrow(() -> new EntityNotFoundException("User not found with id " + article.getUserId()));
                Theme theme = themeRepository.findById(article.getThemeId())
                        .orElseThrow(() -> new EntityNotFoundException("Theme not found with id " + article.getThemeId()));

                ArticleDTO articleDTO = modelMapper.map(article, ArticleDTO.class);
                articleDTO.setName(author.getName());
                articleDTO.setThemeTitle(theme.getTitle());

                articles.add(articleDTO);
            }
        }
        return articles;
    }

    public ArticleDTO createArticle(ArticleDTO articleDTO) {
        if (!userRepository.existsById(articleDTO.getUserId()) || !themeRepository.existsById(articleDTO.getThemeId())) {
            throw new EntityNotFoundException();
        }

        Article article = modelMapper.map(articleDTO, Article.class);
        article.setUserId(articleDTO.getUserId());
        article.setThemeId(articleDTO.getThemeId());

        article.setCommentIds(new ArrayList<>());

        Article savedArticle = articleRepository.save(article);

        User author = userRepository.findById(articleDTO.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + articleDTO.getUserId()));
        Theme theme = themeRepository.findById(articleDTO.getThemeId())
                .orElseThrow(() -> new EntityNotFoundException("Theme not found with id " + articleDTO.getThemeId()));

        ArticleDTO savedArticleDTO = modelMapper.map(savedArticle, ArticleDTO.class);
        savedArticleDTO.setName(author.getName());
        savedArticleDTO.setThemeTitle(theme.getTitle());

        return savedArticleDTO;
    }

    public Optional<ArticleDTO> getArticleById(Long id) {
        return articleRepository.findById(id)
                .map(article -> {
                    User author = userRepository.findById(article.getUserId())
                            .orElseThrow(() -> new EntityNotFoundException("User not found with id " + article.getUserId()));
                    Theme theme = themeRepository.findById(article.getThemeId())
                            .orElseThrow(() -> new EntityNotFoundException("Theme not found with id " + article.getThemeId()));

                    ArticleDTO articleDTO = modelMapper.map(article, ArticleDTO.class);
                    articleDTO.setName(author.getName());
                    articleDTO.setThemeTitle(theme.getTitle());

                    return articleDTO;
                });
    }
}