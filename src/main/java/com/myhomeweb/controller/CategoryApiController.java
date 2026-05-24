package com.myhomeweb.controller;

import com.myhomeweb.dto.CategoryDTO;
import com.myhomeweb.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryApiController {

    private final CategoryService categoryService;

    @GetMapping
    @Transactional(readOnly = true)
    public List<CategoryDTO> listCategories() {
        return categoryService.findAllOrdered().stream()
                .map(CategoryDTO::fromEntity)
                .toList();
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody Map<String, String> body) {
        String name = body.get("name");
        String icon = body.getOrDefault("icon", "📁");
        if (name == null || name.isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        var category = categoryService.create(name.trim(), icon.trim());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CategoryDTO.fromEntity(category));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable String id) {
        try {
            categoryService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
