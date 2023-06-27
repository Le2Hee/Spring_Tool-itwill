package com.itwill.spring3.repository.post;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>{
                                            // T : 테이블을 사용한 클래스 이름, ID : id컬럼의 변수 타입
    
    // id 내림차순 정렬 : 
    // select * from POSTS order by ID desc
    List<Post> findByOrderByIdDesc(); 
    // 메서드 이름은 어떤 규칙에 의해 만들어 주면 SQL문장을 Jpa라이브러리가 자동으로 만들어 준다.
    // By다음에 where절이 있으면 추가로 더 붙이고 없으면 넘어간다.
    
}
