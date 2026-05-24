package com.myhomeweb.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDTO {

    private String id;
    private String name;
    private String icon;
    private int displayOrder;
    private boolean builtin;
    private List<LinkDTO> links;

    public static CategoryDTO fromEntity(com.myhomeweb.model.Category cat) {
        CategoryDTO dto = new CategoryDTO();
        dto.id = cat.getId();
        dto.name = cat.getName();
        dto.icon = cat.getIcon();
        dto.displayOrder = cat.getDisplayOrder();
        dto.builtin = cat.getIsBuiltin();
        if (cat.getLinks() != null) {
            dto.links = cat.getLinks().stream().map(LinkDTO::fromEntity).toList();
        }
        return dto;
    }

}
