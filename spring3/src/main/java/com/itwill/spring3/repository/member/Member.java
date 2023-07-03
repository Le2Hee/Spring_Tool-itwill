package com.itwill.spring3.repository.member;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.itwill.spring3.repository.BaseTimeEntity;
import com.itwill.spring3.repository.post.Post;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "MEMBERS")
@SequenceGenerator(name = "MEMBERS_SEQ_GEN", sequenceName = "MEMBERS_SEQ1", allocationSize = 1)
// Member IS-A UserDetails
// 스프링 시큐리티는 로그인 처리를 위해서 UserDetails 객체를 사용하기 때문에, 구현을 해야한다.
// 회원 정보 엔터티는 UserDetails 인터페이스를 구현해야 함.
public class Member extends BaseTimeEntity implements UserDetails {
                    // UserDetails를 구현한다. UserDetails가 상위 타입. 반드시 해줘야된다.
    @Id
    @GeneratedValue(generator = "MEMBERS_SEQ_GEN", strategy = GenerationType.SEQUENCE)
    private Long id;
    
    @Column(nullable = false, unique = true) // null과 unique 제약조건
    private String username;
    
    @Column(nullable = false)
    private String password;
    
    @Column(nullable = false)
    private String email;
    
    @Column(nullable = false)
    private Role role;
    
    @Builder // 이렇게 Builder을 주면 원하는 아큐먼트만으로 Builder를 만들 수 있다.
    private Member(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = Role.USER; // 회원가입에서 사용자 권한의 기본값은 USER 로 설정.
    }
    
    // UserDetails 인터페이스의 추상 메서드들을 구현 : 
    @Override // ? extend GrantedAuthority : GrantedAuthority를 상속받는 클래스는 무엇이든 가능하다.
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Role_USER 권한을 갖는다.
        return Arrays.asList(new SimpleGrantedAuthority(role.getKey())); // role.USER를 리턴하겠다는 뜻이다.
    }
    // 2개를 넣을려면 2개를 다 넣으면 된다.
    
    @Override // 계정이 만료되지 않았는지 물어보는 메서드
    public boolean isAccountNonExpired() {
        // Java에서 관습적으로 메서드가 is로 시작하면 대부분 리턴문이 boolean이다.
        
        // false: 만료됨, true: 만료안됨.
        return true; // 계정(account)이 non-expired(만료되지 않음). 
    }

    @Override // 계정이 잠겼냐고 물어보는 메서드
    public boolean isAccountNonLocked() {
        //  false면 로그인이 안된다.
        return true; // 계정이 non-lock(잠기지 않음).
    }

    @Override // 비밀번호가 만료가 되었는지 물어보는 메서드
    public boolean isCredentialsNonExpired() {
        return true; // 비밀번호가 non-expired.
    }

    @Override // 유저 정보가 활성화 되어 있느냐 물어보는 메서드
    public boolean isEnabled() {
        return true; // 사용자 상세정보(UserDetails)가 활성화(enable).
        // false는 회원 탈퇴할때 사용한다.
    }
    
}
