package com.itwill.spring3.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itwill.spring3.dto.reply.ReplyCreateDto;
import com.itwill.spring3.dto.reply.ReplyUpdateDto;
import com.itwill.spring3.repository.reply.Reply;
import com.itwill.spring3.service.ReplyService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor // final 변수가 생성자의 의해서 의존서을 주입하기 위해.
@RequestMapping("/api/reply")
public class ReplyRestController {
    
    private final ReplyService replyService;
    
    @PreAuthorize("hasRole('USER')") // 페이지 접근 이전에 인증(권한, 로그인) 여부를 확인 하겠다.
    @GetMapping("/all/{postId}") 
    public ResponseEntity<List<Reply>> all(@PathVariable Long postId) {
        log.info("all(postId = {})", postId);
        
        List<Reply> list = replyService.read(postId);
        
        // 클라이언트로 댓글 리스트를 응답으로 보냄.
        return ResponseEntity.ok(list);
    }
    
    @PreAuthorize("hasRole('USER')") // 페이지 접근 이전에 인증(권한, 로그인) 여부를 확인 하겠다.
    @PostMapping
    public ResponseEntity<Reply> create(@RequestBody ReplyCreateDto dto) { // @RequestBody를 넣어줘야 dto에 값이 들어간다.
        log.info("create(dto = {})", dto);                                  // RequestBody는 Request Payload안에 들어가 있는 값.
        
        Reply reply = replyService.create(dto);
        log.info("CONTROLLER CREATE() reply = {}", reply);
        
        return ResponseEntity.ok(reply);
    }
    
    @PreAuthorize("hasRole('USER')") // 페이지 접근 이전에 인증(권한, 로그인) 여부를 확인 하겠다.
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        log.info("delete(id = {})", id);
        
        // DB replies 테이블에서 ID(고유키)로 테이블 데이터 삭제하기.
        replyService.delete(id);
        
        return ResponseEntity.ok("성공");
    }
    
    @PreAuthorize("hasRole('USER')") // 페이지 접근 이전에 인증(권한, 로그인) 여부를 확인 하겠다.
    @PostMapping("/update")
    public ResponseEntity<String> update(@RequestBody ReplyUpdateDto dto) {
        log.info("update(dto = {})", dto);
        
        replyService.update(dto);
        
        return ResponseEntity.ok("성공");
    }
    
}
