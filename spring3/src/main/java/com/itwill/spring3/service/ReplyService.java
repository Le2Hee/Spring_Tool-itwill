package com.itwill.spring3.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itwill.spring3.repository.post.Post;
import com.itwill.spring3.repository.reply.Reply;
import com.itwill.spring3.repository.reply.ReplyRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class ReplyService {
    
    private final ReplyRepository replyRepository;
    
    // 댓글 전체 검색 service
    @Transactional(readOnly = true)
    public List<Reply> read(Post post) {
        log.info("read(post = {})", post);  
        
        return replyRepository.findByPost(post);
    }
    
}
