package org.shinaikessokuband.anontalk.repository;

import org.shinaikessokuband.anontalk.dto.PostDto;
import org.shinaikessokuband.anontalk.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query("SELECT new org.shinaikessokuband.anontalk.dto.PostDto(p.id, p.title, p.contentPre, p.author) FROM Post p WHERE p.isDeleted = false")
    List<PostDto> findAllPost();
}
