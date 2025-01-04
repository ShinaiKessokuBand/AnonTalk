package org.shinaikessokuband.anontalk.service;

import org.shinaikessokuband.anontalk.dto.PostDto;
import org.shinaikessokuband.anontalk.entity.Post;
import org.shinaikessokuband.anontalk.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceIm implements PostService {
    private final PostRepository postRepository;

    public PostServiceIm(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    /*
    函数名：    createPost
    函数功能：  创建一个新的帖子
    参数：
            - title   String    帖子标题
            - content String    帖子内容
            - userid  Long      发帖用户ID
     */
    @Override
    public Post createPost(String title, String content, Long userid) {
        Post post = new Post(title, content, userid);
        post.setContentPre(content.substring(0, 10)+"...");
        return postRepository.save(post);
    }

    @Override
    public List<PostDto> getAllPosts() {
        return postRepository.findAllPost();
    }

    @Override
    public Post getPostById(Integer postId){
        Optional<Post> postOptional = postRepository.findById(postId);
        if(postOptional.isPresent()){
            return postOptional.get();
        }
        else{
            System.out.println("Post not found");
            return null;
        }
    }

    @Override
    public void deletePostById(Integer postId){
        postRepository.deleteById(postId);
    }

    @Override
    public void likePost(Integer postId) {
        Post post = getPostById(postId);
        post.setLikes(post.getLikes() + 1);
        postRepository.save(post);
    }

    @Override
    public void unlikePost(Integer postId) {
        Post post = getPostById(postId);
        post.setLikes(post.getLikes() + 1);
        postRepository.save(post);
    }
}
