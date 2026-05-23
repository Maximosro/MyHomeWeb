package com.myhomeweb.dto;

import java.util.List;

public class ExportDTO {

    private List<CategoryExport> categories;
    private List<LinkExport> links;

    public List<CategoryExport> getCategories() { return categories; }
    public void setCategories(List<CategoryExport> categories) { this.categories = categories; }
    public List<LinkExport> getLinks() { return links; }
    public void setLinks(List<LinkExport> links) { this.links = links; }

    public static class CategoryExport {
        private String name;
        private String icon;
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getIcon() { return icon; }
        public void setIcon(String icon) { this.icon = icon; }
    }

    public static class LinkExport {
        private String name;
        private String url;
        private String category;
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getUrl() { return url; }
        public void setUrl(String url) { this.url = url; }
        public String getCategory() { return category; }
        public void setCategory(String category) { this.category = category; }
    }
}
