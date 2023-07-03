package com.itwill.spring3.repository.reply;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itwill.spring3.repository.post.Post;

public interface ReplyRepository extends JpaRepository<Reply, Long>{
    
    // Post (id)로 검색하기
    List<Reply> findByPostOrderByIdDesc(Post post);
    // Post 엔터티를 찾아서 알아서 넣어준다.
    
    // Post에 달린 댓글 개수 : 
    Long countByPost(Post post);

    
}
