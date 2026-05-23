package com.myhomeweb.repository;

import com.myhomeweb.model.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LinkRepository extends JpaRepository<Link, String> {
    List<Link> findByCategoryIdOrderByDisplayOrderAsc(String categoryId);
    List<Link> findByIsBuiltinFalse();
}
