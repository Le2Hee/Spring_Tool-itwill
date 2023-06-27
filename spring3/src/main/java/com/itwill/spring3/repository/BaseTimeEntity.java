package com.itwill.spring3.repository;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

// 여러 테이블에서 공통으로 사용되는 생성시간, 수정시간을 프로퍼티로 갖는 객체 설계.

@EntityListeners(AuditingEntityListener.class) // main 메서드를 갖고 있는 메인 클래스에서 JPA Auditing 기능이 활성화되어 있는 경우에
                                               //AuditingEntityListener를 사용하면 수정시간과 작성시간을 자동으로 기록.
@MappedSuperclass // 다른 도메인(엔터티) 클래스의 상위 클래스로 사용된다.
                  // 상속하는 하위 클래스는 BaseTimeEntity가 정의하는 컬럼들을 갖게 된다.
@Getter
public class BaseTimeEntity {
    
    // 엔터티 클래스의 필드 이름은  
    // 데이터베이스 테이블의 컬럼 이름과 같거나, 컬럼 이름 camel 표기법으로 변환한 이름으로 작성.
    // (예) 테이블 careated_time으로 되어 있으면, 클래스에서는 createdTime으로 작성하면 된다.
    
    @CreatedDate // insert될 때의 시간이 자동으로 기록됨.
    private LocalDateTime createdTime; // 표기법은 카멜표기법(자바관습)을 사용하되 문자열은 SQL 컬럼 이랑 똑같아야 한다.
                                       // 테이블의 컬럼 이름은 데이터베이스의 관습(snake 표기법)
                                       // 카멜표기법으로 작성한 변수이름을 자동으로 찾아준다.
    
    @LastModifiedDate // update될 때의 시간이 자동으로 기록됨.
    private LocalDateTime modifiedTime;
    
}
