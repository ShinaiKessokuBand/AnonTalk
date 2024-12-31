package org.shinaikessokuband.anontalk.dto;

import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostDto {

    private String author;

    private LocalDateTime createdAt;

    private boolean isDeleted;

    private String title;

    private String content;

}
