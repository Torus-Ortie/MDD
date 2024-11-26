package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.models.Theme;
import com.openclassrooms.mddapi.repositories.ThemeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThemeService {

    @Autowired
    private ThemeRepository themeRepository;

    /**
     * Gat all the themes
     *
     * @return A list of theme mapped as Theme
     * 
     */
    public List<Theme> getThemes() {
        return themeRepository.findAll();
    }
}
