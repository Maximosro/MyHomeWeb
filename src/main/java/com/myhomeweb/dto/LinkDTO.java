package com.myhomeweb.dto;

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

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    public String getDomain() { return domain; }
    public void setDomain(String domain) { this.domain = domain; }
    public String getCategoryId() { return categoryId; }
    public void setCategoryId(String categoryId) { this.categoryId = categoryId; }
    public int getDisplayOrder() { return displayOrder; }
    public void setDisplayOrder(int displayOrder) { this.displayOrder = displayOrder; }
    public boolean isBuiltin() { return builtin; }
    public void setBuiltin(boolean builtin) { this.builtin = builtin; }
}
