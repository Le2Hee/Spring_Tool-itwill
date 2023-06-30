/**
 * 댓글 영역 보이기 / 숨기기 토글
 * 댓글 검색, 등록, 수정, 삭제
 */

document.addEventListener('DOMContentLoaded', () => {
    
    // 부트스트랩 Collapse 객체를 생성. 초기 상태는 화면에 보이지 않는 상태. 
    const bsCollapse = new bootstrap.Collapse('div#replyToggleDiv', {toggle: false});
                                                    // {}하고 key와 value 이다
    
    // btnToggleReply 찾고, 
    const btnToggleReply = document.querySelector('#btnToggleReply');
    
    // btnToggleReply 이벤트 리스너를 등록.
    btnToggleReply.addEventListener('click', (e) => { //e : 이벤트를 발생시킨 타켓을 찾을 수 있다. (botton)
        bsCollapse.toggle(); // 토글 작동
        // console.log(e.target.innerText);
        
        if (e.target.innerText === '보이기') {
            e.target.innerText = '숨기기';
            
            // 댓글 목록 불러오기 : 
            getRepliesWithPostId();
            
        } else {
            e.target.innerText = '보이기';
        }
        
    });
    
    // 댓글 삭제 버튼 이벤트 함수 생성
    const deleteReply = (e) => {
        //console.log(e.target);
        
        // 삭제를 할껀지 안할껀지 확인하기.
        const result = confirm('정말 삭제할까요?');
        
        if (!result) { // 아니요를 눌렀을때
            
            return; // 종료하기.
            
        }
        
        // 삭제할 댓글 아이디 찾기.
        const id = e.target.getAttribute('data-id');
        
        // Ajax DELETE 방식 요청 주소.
        const reqUrl = `/api/reply/${id}`; // 아이디가 필요하기 때문에 ``사용. (문자열 템플릿 사용)
        
        // Ajax 요청, 비동기적으로 실행됨
        axios 
            .delete(reqUrl) // Ajax DELETE 요청을 보냄.
            .then((response) => { // 성공 응답일 때 실행할 콜백 등록
                
                console.log(response);
                
                // 삭제 후 댓글 갱신.
                getRepliesWithPostId();
                
            })
            .catch((error) => console.log(error)); // 실패 응답일 때 실행할 콜백 등록
        
    };
    
    // 댓글 수정 버튼 이벤트 함수 등록.
    const updateReply = (e) => {
       // console.log(e.target);
       
       const replyId = e.target.getAttribute('data-id');
       const textAreaId = `textarea#replyText_${replyId}`;
       
       const btnModify = document.querySelector('.btnModify');
       const btnDelete = document.querySelector('.btnDelete');
       const textarea = document.querySelector('.replyContent');
       
       btnDelete.classList.add('d-none');
       
       btnModify.innerHTML = '수정완료';
       textarea.readOnly = false;
       
       btnModify.addEventListener('click', (e) => {
           
           const result = confirm('수정하시겠습니까?');
           const textarea = document.querySelector('.replyContent');
           
           if (!result) {
               return;
           }
           
           const replyId = e.target.getAttribute('data-id');
           const textareaValue = textarea.value;
           
           const data = {
               id: replyId,
               replyText: textareaValue
           };
           
           const reqUrl = `/api/reply/update`;
           
           axios
           .post(reqUrl, data) // Ajax POST 방식 요청을 보냄.
           .then((response) => { // 성공 응답 (response)일 때 실행할 콜백 등록
               
               console.log(response);
                
               // 댓글 목록 갱신.
               getRepliesWithPostId();
               
           })
           .catch((error) => console.log(error));
           
       });
       
    };
    
    // 댓글 보여주기 위한 함수 설정
    const makeReplyElements = (data) => {
        
        // 댓글 개수를 배열(data)의 원소 개수로 업데이트
        document.querySelector('span#replyCount').innerText = data.length;
        
        // 댓글 목록을 삽입할 div 요소를 찾음.
        const replies = document.querySelector('div#replies');
        
        // div 안에 작성된 기존 내용은 삭제.
        replies.innerHtml = '';
        
        // div 안에 삽입할 HTML 코드 초기화.
        let htmlStr = '';
        
        // 전달 받은 data 개수 만큼 반복
        for (let reply of data) { // for - of : 배열에서 하나씩 꺼내서 reply에 채워준다.
            
            htmlStr += `
                
                <div class="card my-2">
                    <div>
                        <span class="d-none">${reply.id}</span>
                        <span class="fw-bold">${reply.writer}</span>
                    </div>
                    <div>
                        <textarea class="replyContent form-control" id="replyText_${reply.id}" readonly>${reply.replyText}</textarea>
                    </div>
                    <div class="btnDiv my-2">
                        <button class="btnModify btn btn-outline-primary" data-id="${reply.id}" >수정</button>
                        <button class="btnDelete btn btn-outline-danger" data-id="${reply.id}" >삭제</button>
                    </div>
                </div>
                
            `;
            
        }
        // html안에 id를 안주고 class를 주는 이유는 id는 유일하게 정하는게 좋고 class는 같은 여러개일때 사용하면 좋다.
        // 윗 코드 에서 textarea 처럼 id 값안에 reply.id를 넣어 유일한 값으로 만들어 준다.
        
        // 작성된 HTML 문자열을 div 요소의 innerHTML로 설정.
        replies.innerHTML = htmlStr;
        
        // 모든 댓글 삭제 버튼들에 이벤트
        // 삭제 버튼 찾기.
        const btnDeletes = document.querySelectorAll('button.btnDelete'); // querySelectorAll : 같은 엘리먼트 모든 걸 찾아준다.
                                                                          // querySelector : 가장 가까운 하나만 찾아준다.
        
        // 반복문을 사용해 이벤트 헨들러 등록.
        for (let btn of btnDeletes) {
            btn.addEventListener('click', deleteReply); // collback 함수가 많은 일을해서 코드가 더러워지니 따로 생성해준다.
        }
        
        // 모든 댓글 수정 버튼들에 이벤트 헨들러 등록.
        // 수정 버튼 찾기.
        const btnModifies = document.querySelectorAll('button.btnModify');
        
        // 반복문을 사용해 이벤트 헨들러 등록.
        for (let btn of btnModifies) {
            btn.addEventListener('click', updateReply); 
            // 이벤트 리스너에 호출되는 함수는 클릭 전 까지는 호출하지 않기 때문에 함수 순서가 크게 상관없다.
        }
        
    };
    
    // Post 번호에 달려 있는 댓글 목롥을 (Ajax 요청으로) 가져오는 함수.
    const getRepliesWithPostId = async () => {
        
        // Post 번호 찾기
        const postId = document.querySelector('input#id').value;
        
        // Ajax 요청 주소
        const reqUrl = `/api/reply/all/${postId}`;
        
        // Ajax 요청 보내고 응답을 기다림.
        try { 
            const responce = await axios.get(reqUrl); // await : 이 요청의 결과가 올때까지 기다리겠다. (응답이 오면 저장하겠다.)
                                                   // await가 들어가면 async가 들어가야한다. (비동기 함수다 라는 걸 알려줘야 한다.)
                                                    // 동기 -> 기다리지 않고 즉각즉각 바로 실행한다.
                                                    // 비동기 -> 처리를 기다리고 실행한다.
            
            // 함수 호출
            makeReplyElements(responce.data);
            
        } catch (error) {
            console.log(error);
        }
        
        
    };
    
    // 댓글 등록 버튼을 찾고,
    const btnReplyCreate = document.querySelector('button#btnReplyCreate');
    
    // 이벤트 리스너 등록.
    btnReplyCreate.addEventListener('click', () => {
        
        // 포스트 아이디 찾기.
        const postId = document.querySelector('input#id').value;
        
        // 댓글 내용 찾기.
        const replyText = document.querySelector('textarea#replyText').value;
        
        // 댓글 내용이 비어있으면 요청을 보내면 안된다.
        if (replyText === '') {
            alert('댓글 내용이 없습니다.');
        }
        
        // 댓글 작성자
        // TODO
        const writer = 'admin';
       
       // Ajax 요청에서 보낼 데이터.
       const data = { // const에 바로 {}를 사용하면 '오브젝트'(key,value)라고 한다.
           postId: postId,
           replyText: replyText,
           writer: writer
       };  // 이게 표준 객체 표현 방법이고, 키와 값이 같다면 하나로 줄여서 postId, replyText, writer로 표현 가능하다.
       
       // Ajax 요청을 보낼 URL.
       const reqUrl = '/api/reply';
       
       // Ajax POST 방식의 요청을 보냄.
       axios
           .post(reqUrl, data) // Ajax POST 방식 요청을 보냄.
           .then((response) => { // 성공 응답 (response)일 때 실행할 콜백 등록
               
               console.log(response);
               
               // 댓글 목록 갱신.
               getRepliesWithPostId();
               
               // 댓글 입력한 내용을 지워주기.
               document.querySelector('textarea#replyText').value = '';
               
           })
           .catch((error) => console.log(error)); // 실패(error)일 때 실행할 콜백 등록.      
       
    });
    
    
});