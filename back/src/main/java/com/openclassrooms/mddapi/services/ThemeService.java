package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dto.ThemeDTO;
import com.openclassrooms.mddapi.repositories.ThemeRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ThemeService {

    @Autowired
    private ThemeRepository themeRepository;
    @Autowired
    private ModelMapper modelMapper;

    public List<ThemeDTO> getThemes() {
        return themeRepository.findAll().stream()
            .map(theme -> modelMapper.map(theme, ThemeDTO.class))
            .collect(Collectors.toList());
    }
}
