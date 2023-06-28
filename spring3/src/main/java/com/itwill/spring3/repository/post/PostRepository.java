package com.itwill.spring3.repository.post;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long>{
                                            // T : 테이블을 사용한 클래스 이름, ID : id컬럼의 변수 타입
                                            // 검색에 대한 메서드는 지원해주지 않는다.
    
    // id 내림차순 정렬 : 
    // select * from POSTS order by ID desc
    List<Post> findByOrderByIdDesc(); 
    // 메서드 이름은 어떤 규칙에 의해 만들어 주면 SQL문장을 Jpa라이브러리가 자동으로 만들어 준다.
    // By다음에 where절이 있으면 추가로 더 붙이고 없으면 넘어간다.
    
    // 제목으로 검색 :
    // select * from POSTS p
    // where lower(p.title) like lower('%' || ? || '%')
    // order by p.id desc
    List<Post> findByTitleContainsIgnoreCaseOrderByIdDesc(String title); // 검색 : find
                                                                         // 조건식 : ByTitle(컬럼이름)
                                                                         // Title을 검색하는데 키워드를 포함하면 : Contains
                                                                         // IgnoreCase: 대 소문자 구분없이
                                                                         // OrderByIdDesc : 내림차순
                                                                         // 야규먼트 : ?에 들어갈 이름 Title이여서 title로 설정.
    
    // 내용으로 검색 :
    // select * from POSTS p
    // where lower(p.content) like lower('%' || ? || '%')
    // order by p.id desc
    List<Post> findByContentContainsIgnoreCaseOrderByIdDesc(String content);
    
    // 작성자으로 검색 :
    // select * from POSTS p
    // where lower(p.author) like lower('%' || ? || '%')
    // order by p.id desc
    List<Post> findByAuthorContainsIgnoreCaseOrderByIdDesc(String author);
    
    // 제목과 내용으로 검색 :
    // select * from POSTS p
    // where lower(p.title) like lower('%' || ? || '%')
    //     or lower(p.content) like lower('%' || ? || '%')
    // order by p.id desc
    List<Post> findByTitleContainsIgnoreCaseOrContentContainsIgnoreCaseOrderByIdDesc(String title, String content);
    // 두번째 By에서는 생략을 해야된다. (직접 사용하지 않고 예제로 남겨둔다.)
    
    // JPQL(JPA Query Language) 문법으로 쿼리를 작성하고, 그 쿼리를 실행하는 메서드 이름을 설정:(SQL 문법과 비슷하지만 살짝 다른다.)
    // JPQL은 Entity 클래스의 이름과 필드 이름들을 사용해서 작성.
    // (주의) DB 테이블 이름과 컬럼 이름을 사용하지 않는다!!
    @Query("select p from Post p " +
           " where lower(p.title) like lower('%' || :keyword || '%') " +
           " or lower(p.content) like lower('%' || :keyword || '%') " +
           " order by p.id desc")
    // p(POST별명) : 모든 컬럼을 검색하겠다. -> (JPQL에서는 *를 사용할 수 없다.)
    // POST: POSTS 테이블 Entity 클래스 이름.
    // + 사이에 공백 중요!
    // :keyword : keyword라는 변수가 들어간다. (따로 따로 title, content로 사용할 수 있지만 따로하면 아규먼트를 2개를 받아야한다.)
    List<Post> searchByKeyword(@Param("keyword") String keyword);
    // 메서드 이름은 마음대로 가능, :keyword에 들어갈 아규먼트는 @Param("keyword") String keyword로 설정.
    
}
