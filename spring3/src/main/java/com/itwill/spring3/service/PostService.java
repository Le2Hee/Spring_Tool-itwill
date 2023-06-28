package com.itwill.spring3.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itwill.spring3.dto.PostCreateDto;
import com.itwill.spring3.dto.PostSearchDto;
import com.itwill.spring3.dto.PostUpdateDto;
import com.itwill.spring3.repository.post.Post;
import com.itwill.spring3.repository.post.PostRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
    
    // 생성자를 사용한 의존성 주입 : 
    private final PostRepository postRepository;
    
    // DB POSTS 테이블에서 전체 검색한 결과를 리턴 : 
    @Transactional(readOnly = true) // select 속도를 올려준다.
    public List<Post> read() {
        log.info("read()");
        
        return postRepository.findByOrderByIdDesc();
    }

    // DB POSTS 테이블에 엔터티를 삽입(insert) : 
    public Post create(PostCreateDto dto) {
        log.info("create(dto = {})", dto);
        
        // DTO를 Entity로 변환 :
        Post entity = dto.toEntity();
        log.info("save 전 entity= {}", entity);
        
        // DB 테이블에 저장(insert)
        postRepository.save(entity);
        log.info("save 후 entity= {}", entity);
        
        return entity;
    }
    
    @Transactional(readOnly = true)
    public Post read(Long id) {
        log.info("read(id = {})", id);
        
        return postRepository.findById(id).orElseThrow();
    }

//    public Post update(PostUpdateDto dto, Long id) {
//        log.info("update(dto = {})", dto);
//        
//        Post post = postRepository.findById(id).orElseThrow();
//        log.info("SERVICE post = {}", post);
//        
//        post.update(dto);
//        log.info("SERVICE UPDATE post = {}", post);
//        
//        postRepository.save(post);
//        
//        return post;
//    }
    
    @Transactional // (readOnly)는 기본 값이 false이다. => 계속 해서 엔터티가 변경되는지 안되는지 관리해서 속도가 느리다.
                   // true가 되면 관리가 필요가 없어서 select 속도가 빨라진다.
                   //  -> 업데이트 하는 값이 아니고 읽기 전용(read)이면 true로 해주면 좋다 (선택사항)
    public void update(PostUpdateDto dto, Long id) {
        log.info("update(dto = {})", dto);
        
        
        // (1) 메서드에 @Transactional 애너테이션을 설정하고,
        // (2) DB에서 엔터티를 검색하고,
        // (3) 검색한 엔터티를 수정하면,
        // 트랙잭션이 끝나는 시점에 DB update가 자동으로 수행됨!
        
        // 엔터티 검색
        Post entity = postRepository.findById(id).orElseThrow();
        
        // 엔터티 업데이트 (entity를 수정함으로써 @Transactional이 자동으로 수정을 진행한다.)
        entity.update(dto);
    }

    public void delete(Long id) {
        log.info("delete(id = {})", id);
        
        postRepository.deleteById(id);
    }
    
    @Transactional(readOnly = true)
    public List<Post> search(PostSearchDto dto) {
        log.info("search(dto = {})", dto);
        
        List<Post> list = new ArrayList<>();
        switch(dto.getType()) {
        
        case "t" :
            list = postRepository.findByTitleContainsIgnoreCaseOrderByIdDesc(dto.getKeyword());
            break;
        case "c" :
            list = postRepository.findByContentContainsIgnoreCaseOrderByIdDesc(dto.getKeyword());
            break;
        case "tc" :
            list = postRepository.searchByKeyword(dto.getKeyword());
            break;
        case "a" :
            list = postRepository.findByAuthorContainsIgnoreCaseOrderByIdDesc(dto.getKeyword());
            break;
        }
        
        return list;
    }
    
}
