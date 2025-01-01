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
    @Autowired
    private PostRepository postRepository;

    @Override
    public Post createPost(String title, String content, String username) {
        Post post = new Post(title, content, username);
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
}
