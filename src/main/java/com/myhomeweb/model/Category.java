package com.myhomeweb.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @Column(length = 36, nullable = false, updatable = false)
    private String id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 10)
    private String icon;

    @Column(name = "display_order", nullable = false)
    private Integer displayOrder;

    @Column(name = "is_builtin", nullable = false)
    private Boolean isBuiltin;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("displayOrder ASC")
    private List<Link> links = new ArrayList<>();

    public Category() {}

    public Category(String name, String icon, Integer displayOrder, Boolean isBuiltin) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.icon = icon;
        this.displayOrder = displayOrder;
        this.isBuiltin = isBuiltin;
    }

    public Category(String id, String name, String icon, Integer displayOrder, Boolean isBuiltin) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.displayOrder = displayOrder;
        this.isBuiltin = isBuiltin;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }
    public Integer getDisplayOrder() { return displayOrder; }
    public void setDisplayOrder(Integer displayOrder) { this.displayOrder = displayOrder; }
    public Boolean getIsBuiltin() { return isBuiltin; }
    public void setIsBuiltin(Boolean isBuiltin) { this.isBuiltin = isBuiltin; }
    public List<Link> getLinks() { return links; }
    public void setLinks(List<Link> links) { this.links = links; }
}
