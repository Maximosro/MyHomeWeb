package com.myhomeweb.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ExportDTO {

    private List<CategoryExport> categories;
    private List<LinkExport> links;

    @Getter
    @Setter
    public static class CategoryExport {
        private String name;
        private String icon;
    }

    @Getter
    @Setter
    public static class LinkExport {
        private String name;
        private String url;
        private String category;
    }
}
