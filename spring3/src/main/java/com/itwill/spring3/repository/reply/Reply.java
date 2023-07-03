package com.itwill.spring3.repository.reply;

import com.itwill.spring3.dto.reply.ReplyUpdateDto;
import com.itwill.spring3.repository.BaseTimeEntity;
import com.itwill.spring3.repository.post.Post;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Entity
@Builder
@AllArgsConstructor
@ToString(exclude = {"post"}) // 필수 x, debuging을 위해서.
                              // exclude = {"post"} 옵션을 주면 post라는 걸 제외하고 toString을 만들어 준다. (exclude = 제외하다.)
                              // Lombok의 기능.
@NoArgsConstructor
@Table(name = "REPLIES")
@SequenceGenerator(name = "REPLIES_SEQ_GEN", sequenceName = "REPLIES_SEQ", allocationSize = 1)
public class Reply extends BaseTimeEntity { // 상속을 받아 modifiedTime과 ceatedTime을 받는다.
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REPLIES_SEQ_GEN")
    private Long id; // Primary Key
    
    @ManyToOne(fetch = FetchType.LAZY) // Reply클래스 기준으로 생각하기.
                                       // post 하나에 여러개의 Reply가 가능하다.
                                       // -> @ManyToOne 을 사용한다.  (많은 댓글이 하나의 포스트)
                                       //    Many : Reply 클래스 기준으로 앞에 many
                                       //    one : post 하나
                                       // (fetch = ) : 기본값이 EAGER('즉시')이다.
                                       //              fetch 때문에 join이 발생한다.
                                       //      EAGER : 즉시 로딩 -> 즉시 (실무에서 많이 사용하지 않는다.)
                                       //       LAZY : 지연 로딩 -> 나중에 해도 된다 라는 뜻 (게으른)
    private Post post; // 외래키에 해당하는 부분 클래스 이름을 그대로 쓴다. (postId)
                       // Foreign Key, 관계를 맺고 있는 엔터티
                       // (postId라고 사용해도 상관없지만 이렇게 사용하면 관계를 맺고 있다는걸 알리게 된다.)
                       // join이 된다.
    
    @Column(nullable = false) // not null
    private String replyText; // 댓글 내용
    
    @Column(nullable = false) // not null
    private String writer; // 댓글 작성자
    
    public Reply update(ReplyUpdateDto dto) {
        this.replyText = dto.getReplyText();
        
        return this;
    }
    
}

// @Entity 조건에 만족하지 못하면 error들이 발생한다.
// 1. 기본 생성자 (@NoArgsConstructor)
// 2. 값을 읽을 수 있는 메서드 (@Getter)
// 3. Foreign Key 변수 애너테이션