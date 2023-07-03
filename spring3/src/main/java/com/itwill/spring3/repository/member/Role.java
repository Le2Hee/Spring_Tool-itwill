package com.itwill.spring3.repository.member;

public enum Role {
    USER("ROLE_USER", "USER"), // Role.USER은 숫자로 꺼낼 수 있는데 숫자는 0이다.
    ADMIN("ROLE_ADMIN", "ADMIN"); // ADMIN은 1이다.
    //     key로 전달,  name으로 전달 해서 객체가 생성된다. 그 이름이 user, admin이 된 것이다. 
    
    private final String key;
    private final String name;
    
    Role(String key, String name) {
        this.key = key;
        this.name = name;
    }
    
    public String getKey() {
        return this.key;
    }
    
    
}

// 겉보기에는 클래스와 다른게 없지만 다른점은 객체를 직접 생성하고 있어야한다.