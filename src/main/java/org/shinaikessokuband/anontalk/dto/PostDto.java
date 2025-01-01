package org.shinaikessokuband.anontalk.dto;

import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostDto {

    private Integer id;

    private String author;

    private String title;

    private String content;

    public PostDto(Integer id, String author, String title, String content) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.content = content;
    }
}
