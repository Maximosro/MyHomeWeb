package com.myhomeweb.controller;

import com.myhomeweb.dto.LinkDTO;
import com.myhomeweb.service.LinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/links")
@RequiredArgsConstructor
public class LinkApiController {

    private final LinkService linkService;

    @PostMapping
    public ResponseEntity<LinkDTO> createLink(@RequestBody Map<String, Object> body) {
        String name = (String) body.get("name");
        String url = (String) body.get("url");
        String categoryId = (String) body.get("categoryId");
        if (name == null || name.isBlank() || url == null || url.isBlank() || categoryId == null) {
            return ResponseEntity.badRequest().build();
        }
        try {
            new java.net.URI(url);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        var link = linkService.create(name.trim(), url.trim(), categoryId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(LinkDTO.fromEntity(link));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLink(@PathVariable String id) {
        try {
            linkService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
