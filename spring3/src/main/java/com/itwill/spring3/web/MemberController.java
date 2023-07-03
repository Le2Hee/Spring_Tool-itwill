package com.itwill.spring3.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itwill.spring3.dto.member.MemberSignUpDto;
import com.itwill.spring3.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    
    private final MemberService memberService;
    
    @GetMapping("/signup")
    public void signUp() {
        log.info("signUp()");
    }
    
    @PostMapping("/signup")
    public String signUp(MemberSignUpDto dto) {
        log.info("signUp(dto = {})", dto);
        
        long result = memberService.registerMember(dto);
        
        // 회원 가입 이후에 로그인 화면으로 이동(redirect);
        return "redirect:/login";
    }
    
}
