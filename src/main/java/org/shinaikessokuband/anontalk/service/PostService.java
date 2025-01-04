package org.shinaikessokuband.anontalk.service;

import org.shinaikessokuband.anontalk.dto.PostDto;
import org.shinaikessokuband.anontalk.entity.Post;

import java.util.List;

public interface PostService {

    Post createPost(String title, String content, Long userid);

    List<PostDto> getAllPosts();

    Post getPostById(Integer postId);

    void deletePostById(Integer postId);
}
