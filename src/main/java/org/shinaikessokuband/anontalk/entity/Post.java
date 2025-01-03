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
}
