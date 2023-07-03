package com.itwill.spring3.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.itwill.spring3.dto.member.MemberSignUpDto;
import com.itwill.spring3.repository.member.Member;
import com.itwill.spring3.repository.member.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    // Security Filter Chain에서 UserDetailsService 객체를 사용할 수 있도록 하기 위해서.
    // UserDetailsServic를 구현한다. (회원 service에서 무조건 해야된다.)
    
    private final MemberRepository memberRepository;
    
    // SecurityConfig에서 설정한 PasswordEncoder 빈(been)을 주입해줌.
    private final PasswordEncoder passwordEncoder;

    public long registerMember(MemberSignUpDto dto) {
        log.info("signUp(dto = {})", dto);

        Member entity = Member.builder()
                .username(dto.getUsername())
                .password(passwordEncoder
                .encode(dto.getPassword())) // 인코딩을 하고 넣어야 한다.
                .email(dto.getEmail()).build();

        memberRepository.save(entity);

        return entity.getId();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadUserByUsername(username = {})", username);

        // DB에서 username으로 사용자 정보 검색(select).
        UserDetails user = memberRepository.findByUsername(username);
        // findByUsername의 리턴 타입은 Member이지만 UserDetails로 받을 수 있는 이유는 상속을 받았기 때문이다.

        // username이 일치하는 것이 없으면 null이고 있으면 null이 아닐것이다.
        // 그래서 무조건 리턴을 하면 안된다.

        // username 레코드를 리턴하거나, Exception를 발생시켜야한다.

        if (user != null) {

            return user;

        }
        
        throw new UsernameNotFoundException(username + " - not found");
        // 에러메세지를 리턴해라.
    }

}

// 스프링 시큐리티 필터 체인은 정해진 메서드 이름을 사용해야한다. 
// 그 메서드를 받는 것이 UserDetails 인터페이스이다.