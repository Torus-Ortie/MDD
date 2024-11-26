package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.ArticleDTO;
import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.Theme;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repositories.ThemeRepository;
import com.openclassrooms.mddapi.repositories.UserRepository;
import com.openclassrooms.mddapi.services.ArticleService;
import com.openclassrooms.mddapi.services.UserService;

import jakarta.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private  ThemeRepository themeRepository;

    @GetMapping
    public List<ArticleDTO> getArticlesForSubscribedThemes(Authentication authentication) {
        String emailOrName = authentication.getName();
        User user = userService.getCurrentUser(emailOrName);
        List<Long> themeIds = user.getSubscribedThemeIds();
        
        return articleService.getArticlesForSubscribedThemes(themeIds).stream()
        .map(article -> {
            User author = userRepository.findById(article.getUserId())
            .orElseThrow(() -> new EntityNotFoundException("User not found with id " + article.getUserId()));
        Theme theme = themeRepository.findById(article.getThemeId())
            .orElseThrow(() -> new EntityNotFoundException("Theme not found with id " + article.getThemeId()));

        ArticleDTO articleDTO = modelMapper.map(article, ArticleDTO.class);
        articleDTO.setName(author.getName());
        articleDTO.setThemeTitle(theme.getTitle());

        return articleDTO;
        })
        .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public Optional<ArticleDTO> getArticleById(@PathVariable Long id) {
        return articleService.getArticleById(id).map(article -> {
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

    @PostMapping
    public ArticleDTO createArticle(@RequestBody ArticleDTO articleDTO) {
        Article article = modelMapper.map(articleDTO, Article.class);

        ArticleDTO savedArticleDTO = modelMapper.map(articleService.createArticle(article), ArticleDTO.class);
        User author = userRepository.findById(article.getUserId())
            .orElseThrow(() -> new EntityNotFoundException("User not found with id " + article.getUserId()));
        Theme theme = themeRepository.findById(article.getThemeId())
            .orElseThrow(() -> new EntityNotFoundException("Theme not found with id " + article.getThemeId()));
        savedArticleDTO.setName(author.getName());
        savedArticleDTO.setThemeTitle(theme.getTitle());

        return savedArticleDTO;
    }
}
