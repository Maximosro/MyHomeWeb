package com.myhomeweb.service;

import com.myhomeweb.model.Category;
import com.myhomeweb.model.Link;
import com.myhomeweb.repository.LinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class LinkService {

    private final LinkRepository linkRepository;
    private final CategoryService categoryService;

    @Transactional(readOnly = true)
    public List<Link> findByCategoryId(String categoryId) {
        return linkRepository.findByCategoryIdOrderByDisplayOrderAsc(categoryId);
    }

    public Link create(String name, String url, String categoryId) {
        Category category = categoryService.findById(categoryId);
        List<Link> existing = linkRepository.findByCategoryIdOrderByDisplayOrderAsc(categoryId);
        int nextOrder = existing.isEmpty() ? 1 : existing.get(existing.size() - 1).getDisplayOrder() + 1;
        Link link = new Link(name, url, category, nextOrder, false);
        return linkRepository.save(link);
    }

    public void delete(String id) {
        Link link = linkRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Link not found: " + id));
        if (link.getIsBuiltin()) {
            throw new IllegalStateException("Cannot delete built-in link");
        }
        linkRepository.delete(link);
    }

    @Transactional(readOnly = true)
    public List<Link> getCustomLinks() {
        return linkRepository.findByIsBuiltinFalse();
    }
}
