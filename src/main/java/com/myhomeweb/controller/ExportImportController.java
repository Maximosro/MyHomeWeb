package com.myhomeweb.controller;

import com.myhomeweb.dto.ExportDTO;
import com.myhomeweb.model.Category;
import com.myhomeweb.model.Link;
import com.myhomeweb.repository.CategoryRepository;
import com.myhomeweb.repository.LinkRepository;
import com.myhomeweb.service.CategoryService;
import com.myhomeweb.service.LinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ExportImportController {

    private final CategoryService categoryService;
    private final LinkService linkService;
    private final CategoryRepository categoryRepository;
    private final LinkRepository linkRepository;

    @GetMapping("/export")
    public ExportDTO exportData() {
        ExportDTO dto = new ExportDTO();
        List<ExportDTO.CategoryExport> cats = categoryService.getCustomCategories().stream()
                .map(c -> {
                    ExportDTO.CategoryExport ce = new ExportDTO.CategoryExport();
                    ce.setName(c.getName());
                    ce.setIcon(c.getIcon());
                    return ce;
                }).toList();
        List<ExportDTO.LinkExport> links = linkService.getCustomLinks().stream()
                .map(l -> {
                    ExportDTO.LinkExport le = new ExportDTO.LinkExport();
                    le.setName(l.getName());
                    le.setUrl(l.getUrl());
                    le.setCategory(l.getCategory().getName());
                    return le;
                }).toList();
        dto.setCategories(cats);
        dto.setLinks(links);
        return dto;
    }

    @PostMapping("/import")
    public ResponseEntity<Map<String, Object>> importData(@RequestBody ExportDTO importData) {
        int catsImported = 0;
        int linksImported = 0;

        // Map category names to existing or new Category objects
        Map<String, Category> catMap = new HashMap<>();
        if (importData.getCategories() != null) {
            for (ExportDTO.CategoryExport ce : importData.getCategories()) {
                boolean exists = categoryRepository.existsByNameIgnoreCase(ce.getName());
                if (!exists) {
                    Category cat = categoryService.create(ce.getName(),
                            ce.getIcon() != null ? ce.getIcon() : "📁");
                    catMap.put(ce.getName().toLowerCase(), cat);
                    catsImported++;
                } else {
                    // Find existing to map links
                    categoryRepository.findAll().stream()
                            .filter(c -> c.getName().equalsIgnoreCase(ce.getName()))
                            .findFirst().ifPresent(c -> catMap.put(ce.getName().toLowerCase(), c));
                }
            }
        }

        if (importData.getLinks() != null) {
            for (ExportDTO.LinkExport le : importData.getLinks()) {
                Category cat = catMap.get(le.getCategory().toLowerCase());
                if (cat == null) {
                    // Look up in existing categories
                    cat = categoryRepository.findAll().stream()
                            .filter(c -> c.getName().equalsIgnoreCase(le.getCategory()))
                            .findFirst().orElse(null);
                }
                if (cat == null) {
                    cat = categoryService.create(le.getCategory(), "📁");
                    catMap.put(le.getCategory().toLowerCase(), cat);
                    catsImported++;
                }

                // Check if link URL already exists (avoid duplicates)
                final Category finalCat = cat;
                boolean exists = linkRepository.findAll().stream()
                        .anyMatch(l -> l.getUrl().equals(le.getUrl())
                                && l.getCategory().getId().equals(finalCat.getId()));
                if (!exists) {
                    linkService.create(le.getName(), le.getUrl(), cat.getId());
                    linksImported++;
                }
            }
        }

        return ResponseEntity.ok(Map.of(
                "categoriesImported", catsImported,
                "linksImported", linksImported
        ));
    }
}
