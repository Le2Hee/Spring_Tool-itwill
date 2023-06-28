package com.itwill.spring3.repository.post;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class PostSearchTest {
    
    @Autowired
    private PostRepository postRepository;
    
    @Test
    public void testSeacrh() {
        log.info("testSeacrh()");
        
//        List<Post> list = postRepository.findByTitleContainsIgnoreCaseOrderByIdDesc("L"); // 제목으로 검색하기
//        List<Post> list = postRepository.findByContentContainsIgnoreCaseOrderByIdDesc("L"); // 내용으로 검색하기
//        List<Post> list = postRepository.findByTitleContainsIgnoreCaseOrContentContainsIgnoreCaseOrderByIdDesc("L", "L"); 
                                            // -> 제목과 내용으로 검색하기
//        List<Post> list = postRepository.findByAuthorContainsIgnoreCaseOrderByIdDesc("L"); // 작성자로 검색하기
        List<Post> list = postRepository.searchByKeyword("L"); // JPQL 제목과 내용으로 검색하기
        
        for (Post p : list) {
            
            log.info(p.toString());
            
        }
        
    }
    
}
