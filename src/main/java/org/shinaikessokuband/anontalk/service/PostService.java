package org.shinaikessokuband.anontalk.service;

import org.shinaikessokuband.anontalk.dto.PostDto;
import org.shinaikessokuband.anontalk.entity.Post;

import java.util.List;

public interface PostService {
    Post createPost(String title, String content, String username);
    List<PostDto> getAllPosts();
    Post getPostById(Integer postId);
//    void likePost(Post post);
//    Post commentPost(Post post);
}
