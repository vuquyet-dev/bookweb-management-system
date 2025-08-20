package com.example.bookweb_management.dto.bookdto;

import lombok.Data;

import java.util.List;

@Data
public class GoogleApiBooksResponse {
    private List<Item> items;

    @Data
    public static class Item {
        private VolumeInfo volumeInfo;
    }

    @Data
    public static class VolumeInfo {
        private String title;
        private List<String> authors;
        private String publisher;
        private Integer pageCount;
        private String printType;
        private String language;
        private String description;
    }
}
