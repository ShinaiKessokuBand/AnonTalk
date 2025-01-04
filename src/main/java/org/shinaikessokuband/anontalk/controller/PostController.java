package org.shinaikessokuband.anontalk.controller;

import org.shinaikessokuband.anontalk.Response;
import org.shinaikessokuband.anontalk.dto.PostDto;
import org.shinaikessokuband.anontalk.entity.Post;
import org.shinaikessokuband.anontalk.service.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    private final Map<Integer, Integer> commentLikes = new HashMap<>();
    /**
     * 创建一个新的帖子。
     *
     * 该方法接收包含帖子的标题、内容和用户 ID 的请求体，通过调用服务层的 `createPost` 方法来创建帖子。
     * 如果发生错误，返回一个错误的响应，包含错误消息。
     *
     * 请求的 URL：/api/posts
     * 请求方法：POST
     *
     * @param post 包含帖子标题、内容和用户 ID 的数据，作为请求体传入
     * @return 返回一个包含操作结果的响应，成功或失败
     */
    @PostMapping("/api/posts")
    public Response<Map<String, Object>> createPost(@RequestBody Map<String, Object> post) {
        Map<String, Object> response = new HashMap<>();
        try {
            String title = (String) post.get("title");
            String content = (String) post.get("content");
            Long userid = Long.parseLong((String) post.get("userid"));
            postService.createPost(title, content, userid);
        } catch (Exception e) {
            response.put(
                    "success",
                    false
            );
            response.put(
                    "errorMsg",
                    e.getMessage()
            );
            return Response.newError(response);
        }
        response.put(
                "success",
                true
        );
        return Response.newSuccess(response);
    }

    /**
     * 获取所有帖子列表。
     *
     * 该方法用于获取系统中所有的帖子列表。
     *
     * 请求的 URL：/api/posts
     * 请求方法：GET
     *
     * @return 返回所有帖子的列表，`PostDto` 对象
     */
    @GetMapping("/api/posts")
    public List<PostDto> getAllPosts() {
        return postService.getAllPosts();
    }

    /**
     * 根据帖子 ID 获取指定的帖子。
     *
     * 该方法通过帖子的 ID 获取指定的帖子数据。
     *
     * 请求的 URL：/api/posts/{postId}
     * 请求方法：GET
     *
     * @param postId 帖子的唯一标识符，作为路径参数传入
     * @return 返回指定帖子的详情数据
     */
    @GetMapping("/api/posts/{postId}")
    public Post getPostById(@PathVariable Integer postId) {
        return postService.getPostById(postId);
    }

    /**
     * 根据帖子 ID 删除指定的帖子。
     *
     * 该方法接收帖子的 ID，删除该帖子。如果发生错误，返回错误响应。
     *
     * 请求的 URL：/api/posts/{postId}
     * 请求方法：DELETE
     *
     * @param postId 帖子的唯一标识符，作为路径参数传入
     * @return 返回操作结果的响应，成功或失败
     */
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

    /**
     * 根据帖子 ID 设置喜爱帖子。
     *
     * 该方法接收帖子的 ID，喜爱该帖子。如果发生错误，返回错误响应。
     *
     * 请求的 URL：/api/posts/{postId}
     * 请求方法：PUT
     *
     * @param postId 帖子的唯一标识符，作为路径参数传入
     * @return 返回操作结果的响应，成功或失败
     */
    @PutMapping("/api/posts/like/{postId}")
    public Response<Map<String, Object>> likePost(@PathVariable Integer postId) {
        Map<String, Object> response = new HashMap<>();
        try {
            postService.likePost(postId);
        } catch (Exception e) {
            response.put("success", false);
            response.put("errorMsg", e.getMessage());
            return Response.newError(response);
        }
        response.put("success", true);
        return Response.newSuccess(response);
    }

    /**
     * 根据帖子 ID 设置不喜爱帖子。
     *
     * 该方法接收帖子的 ID，不喜爱该帖子。如果发生错误，返回错误响应。
     *
     * 请求的 URL：/api/posts/{postId}
     * 请求方法：PUT
     *
     * @param postId 帖子的唯一标识符，作为路径参数传入
     * @return 返回操作结果的响应，成功或失败
     */
    @PutMapping("/api/posts/unlike/{postId}")
    public Response<Map<String, Object>> unlikePost(@PathVariable Integer postId) {
        Map<String, Object> response = new HashMap<>();
        try {
            postService.unlikePost(postId);
        } catch (Exception e) {
            response.put(
                    "success",
                    false
            );
            response.put(
                    "errorMsg",
                    e.getMessage()
            );
            return Response.newError(response);
        }
        response.put(
                "success",
                true);
        return Response.newSuccess(response);
    }
    private final Map<Integer, List<String>> comments = new HashMap<>();

    /**
     * 添加评论到指定帖子。
     *
     * 该方法接收帖子的 ID 和评论内容，添加评论到指定帖子。
     *
     * 请求的 URL：/api/posts/{postId}/comments
     * 请求方法：POST
     *
     * @param postId 帖子的唯一标识符，作为路径参数传入
     * @param comment 包含评论内容的数据，作为请求体传入
     * @return 返回操作结果的响应，成功或失败
     */
    @PostMapping("/api/posts/{postId}/comments")
    public Response<Map<String, Object>> addComment(
            @PathVariable Integer postId,
            @RequestBody Map<String, Object> comment
    ) {
        Map<String, Object> response = new HashMap<>();
        try {
            String content = (String) comment.get("content");
            comments.computeIfAbsent(postId, k -> new ArrayList<>()).add(content);
        } catch (Exception e) {
            response.put(
                    "success",
                    false
            );
            response.put(
                    "errorMsg",
                    e.getMessage()
            );
            return Response.newError(response);
        }
        response.put("success", true);
        return Response.newSuccess(response);
    }
    /**
     * 获取指定帖子的所有评论。
     *
     * 该方法通过帖子的 ID 获取该帖子的所有评论。
     *
     * 请求的 URL：/api/posts/{postId}/comments
     * 请求方法：GET
     *
     * @param postId 帖子的唯一标识符，作为路径参数传入
     * @return 返回该帖子的所有评论列表
     */
    @GetMapping("/api/posts/{postId}/comments")
    public Response<List<String>> getComments(@PathVariable Integer postId) {
        List<String> postComments = comments.getOrDefault(postId, new ArrayList<>());
        return Response.newSuccess(postComments);
    }

    /**
     * 删除指定帖子的指定评论。
     *
     * 该方法接收帖子的 ID 和评论的索引，删除指定帖子的指定评论。
     *
     * 请求的 URL：/api/posts/{postId}/comments/{commentIndex}
     * 请求方法：DELETE
     *
     * @param postId 帖子的唯一标识符，作为路径参数传入
     * @param commentIndex 评论的索引，作为路径参数传入
     * @return 返回操作结果的响应，成功或失败
     */
    @DeleteMapping("/api/posts/{postId}/comments/{commentIndex}")
    public Response<Map<String, Object>> deleteComment(@PathVariable Integer postId, @PathVariable Integer commentIndex) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<String> postComments = comments.get(postId);
            if (postComments != null && commentIndex < postComments.size()) {
                postComments.remove((int) commentIndex);
            } else {
                throw new IndexOutOfBoundsException("Comment not found");
            }
        } catch (Exception e) {
            response.put(
                    "success",
                    false
            );
            response.put(
                    "errorMsg",
                    e.getMessage()
            );
            return Response.newError(response);
        }
        response.put(
                "success",
                true
        );
        return Response.newSuccess(response);
    }

    /**
     * 点赞指定评论。
     *
     * 该方法接收帖子的 ID 和评论的索引，点赞指定评论。
     *
     * 请求的 URL：/api/posts/{postId}/comments/{commentIndex}/like
     * 请求方法：PUT
     *
     * @param postId 帖子的唯一标识符，作为路径参数传入
     * @param commentIndex 评论的索引，作为路径参数传入
     * @return 返回操作结果的响应，成功或失败
     */
    @PutMapping("/api/posts/{postId}/comments/{commentIndex}/like")
    public Response<Map<String, Object>> likeComment(@PathVariable Integer postId, @PathVariable Integer commentIndex) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<String> postComments = comments.get(postId);
            if (postComments != null && commentIndex < postComments.size()) {
                int commentId = postId * 1000 + commentIndex; // Generate a unique ID for the comment
                commentLikes.put(commentId, commentLikes.getOrDefault(commentId, 0) + 1);
            } else {
                throw new IndexOutOfBoundsException("Comment not found");
            }
        } catch (Exception e) {
            response.put(
                    "success",
                    false
            );
            response.put(
                    "errorMsg",
                    e.getMessage()
            );
            return Response.newError(response);
        }
        response.put(
                "success",
                true
        );
        return Response.newSuccess(response);
    }

    /**
     * 获取指定评论的点赞数。
     *
     * 该方法接收帖子的 ID 和评论的索引，获取指定评论的点赞数。
     *
     * 请求的 URL：/api/posts/{postId}/comments/{commentIndex}/likes
     * 请求方法：GET
     *
     * @param postId 帖子的唯一标识符，作为路径参数传入
     * @param commentIndex 评论的索引，作为路径参数传入
     * @return 返回指定评论的点赞数
     */
    @GetMapping("/api/posts/{postId}/comments/{commentIndex}/likes")
    public Response<Map<String, Object>> getCommentLikes(@PathVariable Integer postId, @PathVariable Integer commentIndex) {
        Map<String, Object> response = new HashMap<>();
        try {
            int commentId = postId * 1000 + commentIndex; // Generate a unique ID for the comment
            int likes = commentLikes.getOrDefault(commentId, 0);
            response.put(
                    "likes",
                    likes
            );
        } catch (Exception e) {
            response.put(
                    "success",
                    false
            );
            response.put(
                    "errorMsg",
                    e.getMessage()
            );
            return Response.newError(response);
        }
        response.put(
                "success",
                true
        );
        return Response.newSuccess(response);
    }
}