package com.itwill.spring3.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

// @EnableWebSecurity // 우리가 설정한 config에 따라서 WebSecurity를 활성화 시키겠다라는 애너테이션
@EnableMethodSecurity // 윗 애너테이션과 같이 사용할 수 없다.
                      // 컨트롤러 메서드를 위해서 Security를 활성화 하겠다.
@Configuration // 스프링 컨테이너에서 빈(been)으로 생성, 관리하고 필요한곳에 의존성 주입을 해준다.
public class SecurityConfig {
    
    // Spring Security 5 버전부터는 비밀번호는 반드시 암호화를 해야 한다.
    // 만약에 비밀번호를 암호화 하지않으면 HTTP 403, 500 error를 발생시킨다.
    // HTTP 403 error : access denied, 접근 거부
    // HTTP 500 error : internal server error, 내부 서버 오류
    // 비밀번호 인코더(Password encoder) 객체를 반드시 bean으로 생성을 해야 한다.
    // bean이라는 인코더를 리턴할 수 있는 메서드를 만들어 bean 애너테이션을 넣어준다.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // BCrypt : 암호화 하는 알고리즘 중에 하나이다.
    }
    
    /*
    // 로그인 할때 사용할 임시 사용자
    // DB에 저장되어 있는 것이 아니라 단순히 메모리에 임시 저장되는 사용자.)
    @Bean
    public UserDetailsService inMemoryUserDetailsService() {
        // UserDetails : DB에 들어가는 유저 정보 (Entity)
        UserDetails user1 = User
                .withUsername("user1") // 로그인할 때 사용할 사용자 이름.
                .password(passwordEncoder().encode("1111")) 
                // 로그인할 때 사용할 비밀번호. 인코딩을 시져야되서 passwordEncoder() 사용
                .roles("USER") // 사용자 권한(USER 일반 사용자, ADMIN, ...)
                .build(); // UserDetails 객체 생성.
        
        UserDetails user2 = User
                .withUsername("user2")
                .password(passwordEncoder().encode("2222"))
                .roles("USER", "ADMIN")
                .build();
        
        UserDetails user3 = User
                .withUsername("user3")
                .password(passwordEncoder().encode("3333")) 
                .roles("ADMIN")
                .build();
        
        // 로그인을 하는 객체는 무조건 UserDetails의 객체여야 한다.
        // 모든 User 객체는 UserDetails의 객체여야 하기 때문에 상속을 받아야 한다.
        
        return new InMemoryUserDetailsManager(user1, user2, user3); 
    }
    */
    
    // Security Filter 설정 bean :
    // 로그인과 로그아웃 설정
    // 로그인 페이지 설정, 로그아웃 이후 이동할 페이지.
    // 페이지 접근 권한 -> 로그인해야만 접근 가능한 페이지, 로그인 없이 접근 가능한 페이지.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // HttpSecurity http를 이용해서 filter 설정을 하고 Spring controller가 bean을 생성해 관리하고,
        // 필요한 정보를 넘겨준다.
        
        // Customizer<CsrfConfigurer<HttpSecurity>> : 익명 표현식 (람다 표현식을 사용해야됨)
        // CSRF(Cross Site Request Forgery) -> csrf.disable()을 넣어주는 이유 : 
        // -> csrf라는 기능을 비활성화, 사용하지 않겠다는 의미
        // -> 해주는 목적 : 활성화를 하게되면 보안은 좋지만, Ajax의 POST/PUT/DELETE (GET 은 상관없다.) 요청에서 CSRF 토큰을 서버로
        //                  전송하지 않으면 403에러가 발생한다.
        http.csrf((csrf) -> csrf.disable());
        
        // 로그인 페이지 설정. -> 스프링에서 제공하는 기본 로그인 페이지를 사용하겠다. (form을 이용해 로그인 하겠다.)
        http.formLogin(Customizer.withDefaults());
        // Customizer : 내 입맛에 맞게 만드는 것.
        
        // 페이지 접근 권한에 대한 설정. HttpRequests에 대해서 authorize(권한을 부여) 한다.
        // 익명 클래스를 사용하면 @Override를 사용해서 메서드를 정의 해줘야하는데, 람다식으로 간단핳게 표현한 것이다. 
        // -> 함수형 인터페이스(추상메서드가 딱 하나만 있는 것)이라서 람다식으로 표현이 가능하다.
/*        http.authorizeHttpRequests((authRequest) -> 
                authRequest // 접근권한을 설정할 수 있는 객체.
                // 권한을 필요한 페이지들을 설정.
                .requestMatchers("/post/create", "/post/details", "/post/modify", "/post/update", "/post/delete"
                        , "/api/reply/**") // "/api/reply/**" : /api/reply/ 밑에 어떤 주소든 상관없다. 
                                           // /post/create 페이지는, 쿼리 스트링은 따로 표현을 안해도 된다.
                                           // 쿼리 스트링 : datails 뒤에 들어가는 포스트 번호
//                .authenticated() : 위에 페이지에 대해서는 인증을 성공하면 들어갈 수 있다. (로그인은 하면서 권한은 상관없다.)
//                                   .hasRole과 같이 사용할 수 없다.
                .hasRole("USER") // 위에서 설정한 페이지들이 USER 권한을 요규함을 설정
                .requestMatchers("/**") // 위에 요청주소를 제외한, 
                                        // *이 2개인 이유는 /post/details 에서 하나만 사용하면 /post까지이고
                                        // 2개를 사용하면 그 하위 폴더까지 전부 다 라는 뜻이다.
             // .anyRequest() : 위에 요청을 제외하고 어떤 요청이든지 허용하겠다.) 
             // -> requestMatchers("/**")를 사용한거랑 똑같다.
                .permitAll() // 권한없이 접근 허용.
                ); */
        
        // 단점 : 새로운 요청 경로, 컨트롤러를 작성할 때마다 Config 자바 코드를 수정해야 한다.
        // -> 컨트롤러 메서드를 작성할 때 애너테이션을 사용해서 접근 권한을 설정할 수 있다.
        // 애너테이션을 하기 위해서는 2가지를 해줘야한다.
        // (1) SecurityConfig 클래스에서 @EnableGlobalMethodSecurity 애너테이션 설정. (SecurityConfig클래스에게 알려주고)
        // (2) 각각의 컨트롤러 메서드에서 @PreAuthorize 또는 @PostAuthorize 애너테이션을 사용.
        
        // 로그아웃 이후 이동할 페이지
        http.logout((logout) -> logout.logoutSuccessUrl("/")); // 로그아웃을 성공하면 메인페이지로 가겠다.
        
        return http.build();
    }
    
}

// 클라이언트에서 Request가 들어온다. 중간에 필터를 걸치고 컨트롤러에 들어간다.
// SecurityFilterChain 환경설정을 해버리면 사이트를 들어갈때 로그인을 바로 안들어가지고, 메서드 안에서 환경설정을 해줘야 한다.
// -> 권한에 대한 환경설정

// Tomcat은 다른말로 servlet 컨테이너라고도 한다.
// 컨테이너라는 개념은 객체들을 생성할 수 있는 프로그램, 객체들을 사용할 수 있는 프로그램.