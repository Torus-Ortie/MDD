package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.ArticleDTO;
import com.openclassrooms.mddapi.dto.UserDTO;
import com.openclassrooms.mddapi.services.ArticleService;
import com.openclassrooms.mddapi.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserService userService;

    @GetMapping
    public List<ArticleDTO> getArticlesForSubscribedThemes(Authentication authentication) {
        String emailOrName = authentication.getName();
        UserDTO userDTO = userService.getCurrentUser(emailOrName);
        List<Long> themeIds = userDTO.getSubscribedThemeIds();
        return articleService.getArticlesForSubscribedThemes(themeIds);
    }

    @GetMapping("/{id}")
    public Optional<ArticleDTO> getArticleById(@PathVariable Long id) {
        return articleService.getArticleById(id);
    }

    @PostMapping
    public ArticleDTO createArticle(@RequestBody ArticleDTO articleDTO) {
        return articleService.createArticle(articleDTO);
    }
}
