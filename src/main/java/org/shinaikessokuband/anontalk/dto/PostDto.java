package org.shinaikessokuband.anontalk.dto;

import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDateTime;


public class PostDto {

    private Integer id;

    private Long author;

    private String title;

    private String content;

    public PostDto(Integer id, String title, String content, Long author) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.content = content;
    }

    public PostDto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getAuthor() {
        return author;
    }

    public void setAuthor(Long author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
