package com.itwill.spring3.dto;

import com.itwill.spring3.repository.post.Post;

import lombok.Data;

@Data
public class PostUpdateDto {
    
    private String title;
    private String content;
    
}

// String타입의 변수를 2개 선언했는데,
// view에서 목록테이블에서 상세보기에 들어가고 수정하기 들어가서 수정할 컬럼들을 설정했다.