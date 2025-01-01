package org.shinaikessokuband.anontalk.controller;

import org.shinaikessokuband.anontalk.dto.PostDto;
import org.shinaikessokuband.anontalk.entity.Post;
import org.shinaikessokuband.anontalk.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping("/api")
public class PostController {
    @Autowired
    PostService postService;

    @PostMapping("/api/posts")
    public String createPost(String title,String content,String username) {
        postService.createPost(title, content, username);
        return "Post created successfully!";
    }

    @GetMapping("/api/posts")
    public List<PostDto> getAllPosts(){
        return postService.getAllPosts();
    }

    @GetMapping("/api/posts/{postId}")
    public Post getPostById(@PathVariable Integer postId) {
        return postService.getPostById(postId);
    }
}
