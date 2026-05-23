package com.myhomeweb.dto;

import java.util.List;

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

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }
    public int getDisplayOrder() { return displayOrder; }
    public void setDisplayOrder(int displayOrder) { this.displayOrder = displayOrder; }
    public boolean isBuiltin() { return builtin; }
    public void setBuiltin(boolean builtin) { this.builtin = builtin; }
    public List<LinkDTO> getLinks() { return links; }
    public void setLinks(List<LinkDTO> links) { this.links = links; }
}
