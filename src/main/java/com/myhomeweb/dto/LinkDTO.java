package com.myhomeweb.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LinkDTO {

    private String id;
    private String name;
    private String url;
    private String domain;
    private String categoryId;
    private int displayOrder;
    private boolean builtin;

    public static LinkDTO fromEntity(com.myhomeweb.model.Link link) {
        LinkDTO dto = new LinkDTO();
        dto.id = link.getId();
        dto.name = link.getName();
        dto.url = link.getUrl();
        dto.domain = link.getDomain();
        dto.categoryId = link.getCategory().getId();
        dto.displayOrder = link.getDisplayOrder();
        dto.builtin = link.getIsBuiltin();
        return dto;
    }

}
