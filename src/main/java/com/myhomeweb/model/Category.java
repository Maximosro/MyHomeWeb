package com.myhomeweb.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
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

}
