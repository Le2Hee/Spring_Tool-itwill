package com.itwill.spring3.repository.reply;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.itwill.spring3.repository.post.Post;
import com.itwill.spring3.repository.post.PostRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class ReplyRepositoryTest {
    
    @Autowired
    private ReplyRepository replyRepository;
    
    @Autowired
    private PostRepository postRepository;
    
//    @Test
    public void testFindById() {
        log.info("testFindById()");
        
        // 댓글 번호로 검색하기.
        Reply reply = replyRepository.findById(2L).orElseThrow(); // L을 붙이는 이유 : Long 타입이기 때문에.
        log.info(reply.toString());
        
//        log.info(reply.getPost().toString()); 
        // 검색을 하고 난 뒤 Closing JPA EntityManagerFactory로 Entity를 닫아버려서 다음 코드를 진행할 수 가 없다.
        // Post가 필요한 경우에는 join을 진행해 출력해 준다.
        
        // findById() 메서드는
        // Reply 엔터티에서 FetchType.EAGER를 사용한 경우에는 join 문장을 실행.
        // FetchType.LAZY를 사용한 경우에는 단순 select 문장을 실행하고,
        // Post 엔터티가 필요한 경우에 (나중에) join 문장이 실행된다.
    }
    
    @Test
    public void testFindByPost() {
        log.info("testFindByPost()");
        
        // 아규먼트에 Post가 들어가기 때문에 Post를 찾는다.
        Post post = postRepository.findById(44L).orElseThrow();
        
        // 해당 포스트에 달린 모든 댓글 검색 :
        List<Reply> list = replyRepository.findByPost(post); // post를 넘겼지만 post_id로 검색한다.
                                                             // fetch = FetchType.LAZY를 안 넣으면 join문장으로 출력이 된다.

        for (Reply r : list) {
            log.info("Reply = {}", r.toString());
        }
        
    }
}
