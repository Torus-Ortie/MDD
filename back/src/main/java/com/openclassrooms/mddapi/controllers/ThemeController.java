package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.ThemeDTO;
import com.openclassrooms.mddapi.services.ThemeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/themes")
public class ThemeController {

    @Autowired
    private ThemeService themeService;

    @GetMapping
    public List<ThemeDTO> getThemes() {
        return themeService.getThemes();
    }
}
