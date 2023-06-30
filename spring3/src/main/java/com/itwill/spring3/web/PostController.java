package com.itwill.spring3.web;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itwill.spring3.dto.PostCreateDto;
import com.itwill.spring3.dto.PostSearchDto;
import com.itwill.spring3.dto.PostUpdateDto;
import com.itwill.spring3.repository.post.Post;
import com.itwill.spring3.service.PostService;
import com.itwill.spring3.service.ReplyService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    
    private final PostService postService;
    private final ReplyService replyService;
    
    @GetMapping
    public String read(Model model) {
        log.info("read()");
        
        // 포스트 목록 검색 :
        List<Post> list = postService.read();
        
        // Model에 검색 결과를 세팅 :
        model.addAttribute("posts", list);
        
        return "/post/read";
    }
    
    @PreAuthorize("hasRole('USER')") // Security 접근 권한 설정. (메서드 그대로 문자열을 넣어준다.)
                                     // Authorize : 인증하겠다. , Pre : 이전 (post : 이후)
                                     // 페이지 접근 이전에 인증(권한, 로그인) 여부를 확인 하겠다.
    @GetMapping("/create")
    public void create() {
        log.info("create() GET");
    }
    
    @PreAuthorize("hasRole('USER')") // 페이지 접근 이전에 인증(권한, 로그인) 여부를 확인 하겠다.
    @PostMapping("/create")
    public String create(PostCreateDto dto) { // form에서 submit된걸 Dispatcher-Servlet(디스패처 서블릿)이 dto로 만들어준다.
        log.info("create(dto = {}) POST", dto);
        
        // form에서 submit(제출)된 내용을 DB 테이블에 insert
        postService.create(dto);
        
        // DB 테이블 insert 후 포스트 목록 페이지로 redirect 이동.
        // Post -> Redirect -> Get 패턴을 PRG패턴이라고 한다.
        return "redirect:/post";
    }
    
    // "/post/details", "/post/modify" 요청 주소들을 처리하는 컨트롤러 메서드.
    // 컨트롤러 메서드의 리턴값이 없는 경우 (void인 경우),
    // 뷰의 이름은 요청 주소오 같다.
    // details -> detail.html, modify -> modify.html
    @GetMapping({"/details", "/modify"}) // 쿼리 String은 주소에 사용하지 않는다.
                             // GetMapping() 안에 {}(배열)로 선언하면 하나의 GetMapping을 이용해 2개의 요청방식을 사용할 수 있다.
    @PreAuthorize("hasRole('USER')") // 페이지 접근 이전에 인증(권한, 로그인) 여부를 확인 하겠다.
    public void read(Long id, Model model) { // html에서 @RequestParam으로 보낸 변수 이름이 같으면 자동으로 채워준다.
        log.info("read(id = {})", id);
        
        // POSTS 테이블에서 id에 해당하는 포스트를 검색.
        Post post = postService.read(id);
        log.info("CONTROLLER read() post = {}", post);
        
        // REPLIES 테이블에서 해당 포스터에 달린 댓글 개수를 검색.
        Long count = replyService.countByPost(post);
        
        // replyList 결과를 model에 저장.
        model.addAttribute("replyCount", count);
        
        // post 결과를 model에 저장. -> 뷰로 전달
        model.addAttribute("post", post);
        
    }
    
    @PreAuthorize("hasRole('USER')") // 페이지 접근 이전에 인증(권한, 로그인) 여부를 확인 하겠다.
    @PostMapping("/modify")
    public String modify(PostUpdateDto dto, @RequestParam Long id) {
        log.info("modify POST (dto = {})", dto);
        
        postService.update(dto, id);
        
        return "redirect:/post/details?id=" + id;
    }
    
    @PreAuthorize("hasRole('USER')") // 페이지 접근 이전에 인증(권한, 로그인) 여부를 확인 하겠다.
    @GetMapping("/delete")
    public String delete(@RequestParam Long id) {
        log.info("delete(id = {})", id);
        
        postService.delete(id);
        
        return "redirect:/post";
    }
    
    @GetMapping("/search")
    public String search(PostSearchDto dto, Model model) {
        log.info("search(dto = {})", dto);
        
        List<Post> list = postService.search(dto);
        
        model.addAttribute("posts", list);
        
        return "/post/read";
    }
    
    
}
