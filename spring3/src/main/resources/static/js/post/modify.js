/**
 * 포스트 업데이트 & 삭제
 */

// html document가 엘리먼트가 다 생성되면 이벤트를 실행하겠다.
// 2개를 동시에 사용하면 html은 하나 밖에 없기 때문에 마지막에 실행되는 것 하나만 남게된다.
document.addEventListener('DOMContentLoaded', () => {
    
    // form 찾기
    const form = document.querySelector('form#postModifyForm');
    
    // post title 찾기
    const inputTitle = document.querySelector('input#title');
    
    // post content 찾기
    const textareaContent = document.querySelector('textarea#content');
    
    // 수정완료 버튼 찾기
    const btnUpdate = document.querySelector('button#btnUpdate');
    
    // 삭제 버튼 찾기
    const btnDelete = document.querySelector('button#btnDelete');
    
    // 수정 버튼 이벤트 등록
    btnUpdate.addEventListener('click', (e) => {
        e.preventDefault;
        
        // 이벤트 리스너 밖에 있으면 그 전에 있던 값들로 저장이 된다.
        const title = inputTitle.value;
        const content = textareaContent.value;
        
        // 내용이 없으면 수정을 못하게 방지
        if (title === '' || content === '') {
            alert('제목과 내용은 반드시 입력해야 합니다.');
            return;
        }
        
        const result = confirm('수정하시겠습니까?')
        
        if (result) {
            
            form.method = 'post';
            form.action = '/post/modify';
            form.submit();
        }
        
        
    });
    
    // 삭제 버튼 이벤트 등록
    btnDelete.addEventListener('click', (e) => {
        e.preventDefault;
        
        const result = confirm('삭제하기겠습니까?');
        
        if (result) {
            
            form.method = 'get';
            form.action = '/post/delete';
            form.submit();
            
        }
        
    });
    
});
 