package com.itwill.spring3.repository.post;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest // Test 클래스는 @SpringBootTest 필수
public class PostRepositoryTest {
    
    @Autowired
    private PostRepository postRepository; 
    
    @Test
    public void testFindAll() {
        log.info("testFindAll()");
        
//        List<Post> list = postRepository.findAll();
//        for (Post p : list) {
//            log.info(p.toString());
//        }
        
        List<Post> list = postRepository.findByOrderByIdDesc();
        for (Post p : list) {
            log.info(p.toString());
        }
        
        
    }
    
//    @Test
    
}
