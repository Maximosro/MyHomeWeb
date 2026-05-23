package com.myhomeweb.repository;

import com.myhomeweb.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
    List<Category> findAllByOrderByDisplayOrderAsc();
    boolean existsByNameIgnoreCase(String name);
    List<Category> findByIsBuiltinFalseOrderByDisplayOrderAsc();
}
