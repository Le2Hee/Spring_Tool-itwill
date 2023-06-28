package com.itwill.spring3.dto;

import lombok.Data;

@Data
public class PostSearchDto {
    
    private String type;
    private String keyword;
    
    
}


// html에서 보내는 태그들의 name 속성과 같은 이름으로 해야 자동으로 값이 들어간다.