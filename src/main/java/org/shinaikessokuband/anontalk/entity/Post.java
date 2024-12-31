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

    @Column(name="user_name")
    private String author;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "is_deleted",nullable = false)
    private boolean isDeleted;

    @Column(name="title")
    private String title;

    @Column(name="content", columnDefinition = "TEXT")
    private String content;

}
