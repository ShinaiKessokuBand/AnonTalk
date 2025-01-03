package org.shinaikessokuband.anontalk.controller;

import org.shinaikessokuband.anontalk.Response;
import org.shinaikessokuband.anontalk.dto.PostDto;
import org.shinaikessokuband.anontalk.entity.Post;
import org.shinaikessokuband.anontalk.service.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
//@RequestMapping("/api")
public class PostController {
    final
    PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/api/posts")
    public Response<Map<String, Object>> createPost(@RequestBody Map<String, Object> post) {
        Map<String, Object> response = new HashMap<>();
        try {
            String title = (String) post.get("title");
            String content = (String) post.get("content");
            Long userid = Long.parseLong((String) post.get("userid"));
            postService.createPost(title, content, userid);
        } catch (Exception e) {
            response.put("success", false);
            response.put("errorMsg", e.getMessage());
            return Response.newError(response);
        }
        response.put("success", true);
        return Response.newSuccess(response);
    }

    @GetMapping("/api/posts")
    public List<PostDto> getAllPosts(){
        return postService.getAllPosts();
    }

    @GetMapping("/api/posts/{postId}")
    public Post getPostById(@PathVariable Integer postId) {
        return postService.getPostById(postId);
    }

    @DeleteMapping("/api/posts/{postId}")
    public Response<Map<String, Object>> deletePostById(@PathVariable Integer postId) {
        Map<String, Object> response = new HashMap<>();
        try {
            postService.deletePostById(postId);
        } catch (Exception e) {
            response.put("success", false);
            response.put("errorMsg", e.getMessage());
            return Response.newError(response);
        }
        response.put("success", true);
        return Response.newSuccess(response);
    }
}
