package com.itwill.spring3.repository.post;

import com.itwill.spring3.dto.PostUpdateDto;
import com.itwill.spring3.repository.BaseTimeEntity;

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

@Entity // JPA 엔터티 클래스 -> 이게 있어야 @Table, ... 을 사용할 수 있다. (domain을 Entity라고 부르는 것)
        // 데이터베이스 테이블과 매핑되는 클래스.
@Table(name = "POSTS") // 테이블을 찾아준다. POSTS or 기본값 : 클래스 이름.
                       // 엔터티 클래스 이름이 데이터베이스 테이블 이름과 다른 경우, 테이블 이름을 명시.
@SequenceGenerator(name = "POSTS_SEQ_GEN", sequenceName = "POSTS_SEQ", allocationSize = 1)
                                 // allocationSize = 1 -> 시퀀스 증가할 때 1씩 증가하게 만들었기 때문에 1로 설정 (기본값 : 50)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
public class Post extends BaseTimeEntity {
    // 일부로 테이블 이름과 다르게 class이름을 선언했다.
    
    @Id // Primary key 제약 조건
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "POSTS_SEQ_GEN")
    private Long id;
    // id는 시쿼스로 자동으로 값이 등록되기 때문에 아이디를 만드는 방법을 시퀀스(strategy = GenerationType.SEQUENCE)로 하겠다.
    // strategy = GenerationType.SEQUENCE는 뭐로 할꺼냐 generator = "POSTS_SEQ_GEN로 하겠다.
    
    @Column(nullable = false) // 제약조건 not null이라고 설정했을때, 애너테이션을 추가해준다. (기본값 : null이 가능하다)
    private String title;
    
    @Column(nullable = false)
    private String content;
    
    @Column(nullable = false)
    private String author;
    
    // created_time, modified_time 은 BaseTimeEntity에 상속받을 것이다.
    
    // Post 엔터티의 title과 content를 수정해서 리턴하는 메서드: (반드시 리턴까지 해야된다.)
    public Post update(PostUpdateDto dto) {
        this.title = dto.getTitle();
        this.content = dto.getContent();
        // Setter메서드 2개를 update() 하나로 대체하겠다.
        
        return this; // 바꾼 값을 그대로 리턴하겠다.
    }
    
}
