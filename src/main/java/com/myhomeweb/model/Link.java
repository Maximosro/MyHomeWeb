package com.myhomeweb.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.net.URI;
import java.util.UUID;

@Entity
@Table(name = "links")
@Getter
@Setter
@NoArgsConstructor
public class Link {

    @Id
    @Column(length = 36, nullable = false, updatable = false)
    private String id;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(nullable = false, length = 500)
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "display_order", nullable = false)
    private Integer displayOrder;

    @Column(name = "is_builtin", nullable = false)
    private Boolean isBuiltin;

    public Link(String name, String url, Category category, Integer displayOrder, Boolean isBuiltin) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.url = url;
        this.category = category;
        this.displayOrder = displayOrder;
        this.isBuiltin = isBuiltin;
    }

    public Link(String id, String name, String url, Category category, Integer displayOrder, Boolean isBuiltin) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.category = category;
        this.displayOrder = displayOrder;
        this.isBuiltin = isBuiltin;
    }

    public String getDomain() {
        try {
            return new URI(url).getHost();
        } catch (Exception e) {
            return "";
        }
    }

}
