package com.myhomeweb.model;

import jakarta.persistence.*;
import java.net.URI;
import java.util.UUID;

@Entity
@Table(name = "links")
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

    public Link() {}

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

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
    public Integer getDisplayOrder() { return displayOrder; }
    public void setDisplayOrder(Integer displayOrder) { this.displayOrder = displayOrder; }
    public Boolean getIsBuiltin() { return isBuiltin; }
    public void setIsBuiltin(Boolean isBuiltin) { this.isBuiltin = isBuiltin; }
}
