package org.shinaikessokuband.anontalk.repository;

import org.shinaikessokuband.anontalk.entity.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Integer> {
}
