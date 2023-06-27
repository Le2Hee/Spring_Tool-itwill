package com.itwill.spring3.repository.post;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.itwill.spring3.dto.PostUpdateDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest // Test 클래스는 @SpringBootTest 필수
public class PostRepositoryTest {
    
    @Autowired
    private PostRepository postRepository; 
    
//    @Test
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
    public void testSave() {
        log.info("testSave()");
        
        // DB 테이블에 insert할 레코드(엔터티)를 생성 :
        Post entity = Post.builder()
                .title("JPA 테스트")
                .content("JPA 라이브러리를 사용한 INSERT")
                .author("admin")
                .build();
        
        log.info("insert 전 : {}", entity);
        log.info("created: {}, modified: {}", entity.getCreatedTime(), entity.getModifiedTime());
        
        // DB 테이블에 insert :
        postRepository.save(entity); // save 메서드는 테이블에 삽입할 엔터티를 파라미터에 전달하면,
                                     // 테이블에 저장된 엔터티 객체를 리턴.
                                     // 따로 변수선언하지 않고 저장하지 않는 이유 :
                                     // -> 파라미터에 전달된 엔터티 필드들을 변경해서 리턴. 
        log.info("insert 후 : {}", entity);
        log.info("created: {}, modified: {}", entity.getCreatedTime(), entity.getModifiedTime());
        
    }
    
//    @Test
    public void testUpdate() {
        log.info("testUpdate()");
        
        // 업데이트하기 전의 엔터티 검색 :
        Post entity = postRepository.findById(61L)
                .orElseThrow(); 
        // Optional<Post>을 리턴하는 걸 .orElseThrow()을 사용해서 Post 타입으로 변환한다.
        // orElseThrow() -> 값이 있으면 값을 리턴하고, 없으면 error를 던지겠다.
        // 일치하는 아이디가 없으면 검색 결과가 없어서(값이 있으면 Post타입, 없으면 error) Optional이라는 타입으로 리턴한다.
        log.info("update 전 : {}", entity);
        log.info("update 전 수정시간 : {}", entity.getModifiedTime());
        
        // Post 엔터티를 만들때 getter은 만들었지만 setter은 만들지 않았다.
        // Post 클래스에 update() 추가 -> dto 추가
        
        // dto 선언 : 
        PostUpdateDto dto = new PostUpdateDto();
        dto.setTitle("JPA update 테스트");
        dto.setContent("JPA Hibernate를 사용한 DB 테이블 업데이트");
        
        // 엔터티를 수정 :
        entity.update(dto);
        
        // DB 테이블 업데이트 : (save()가 insert와 update를 동시에 수행을 한다.)
        // JPA에서는 insert와 update 메서드가 구분되어 있지 않음.
        // save() 메서드의 아규먼트가 DB에 없는 엔터티이면 insert, DB에 있는 엔터티이면 update를 실행.
        postRepository.saveAndFlush(entity); // save()를 사용하면 즉각적으로 commit이 이루어 지지 않지만,
                                             // saveAndFlush()를 사용하면 즉각적으로 commit이 이루어 진다.
        
        
        
    }
    
    @Test
    public void testDelet() {
        log.info("testDelet()");
        
        long count = postRepository.count(); // DB 테이블의 행의 개수(엔터티 개수)
        log.info("삭제 전 count : {}", count);
        
        postRepository.deleteById(61L);
        
        count = postRepository.count();
        log.info("삭제 후 count : {}", count);
        
    }
    
}
