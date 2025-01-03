package org.shinaikessokuband.anontalk.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自增主键
    private Integer id;

    @Column(name="userid")
    private Long author;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "is_deleted",nullable = false)
    private boolean isDeleted;

    @Column(name="title")
    private String title;

    @Column(name="content", columnDefinition = "TEXT")
    private String content;

    @Column(name="content_pre")
    private String contentPre;

    @Column(name="likes")
    private int likes;

    public Post(String title, String content, Long userid) {
        this.author = userid;
        this.title = title;
        this.content = content;
    }

    public Post(){
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
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

    public String getContentPre() {
        return contentPre;
    }

    public void setContentPre(String contentPre) {
        this.contentPre = contentPre;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
}
