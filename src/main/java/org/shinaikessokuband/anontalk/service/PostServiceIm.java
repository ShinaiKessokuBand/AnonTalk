package org.shinaikessokuband.anontalk.service;

import org.shinaikessokuband.anontalk.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostServiceIm implements PostService {
    @Autowired
    private PostRepository postRepository;

}
