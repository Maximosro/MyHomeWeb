package com.myhomeweb.service;

import com.myhomeweb.model.Category;
import com.myhomeweb.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public List<Category> findAllOrdered() {
        return categoryRepository.findAllByOrderByDisplayOrderAsc();
    }

    @Transactional(readOnly = true)
    public Category findById(String id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found: " + id));
    }

    public Category create(String name, String icon) {
        int maxOrder = (int) categoryRepository.count();
        Category category = new Category(name, icon, maxOrder + 1, false);
        return categoryRepository.save(category);
    }

    public void delete(String id) {
        Category category = findById(id);
        if (category.getIsBuiltin()) {
            throw new IllegalStateException("Cannot delete built-in category");
        }
        categoryRepository.delete(category);
    }

    @Transactional(readOnly = true)
    public List<Category> getCustomCategories() {
        return categoryRepository.findByIsBuiltinFalseOrderByDisplayOrderAsc();
    }
}
