/**
 * 
 */

function test1() {
    console.log('test1');
}

test1(); // html을 실행하면 바로 실행됨.

// --------------------------------------------------

test2(); // 순서가 달라져도 실행이 된다. 
         // function은 실행문이 아니라 선언문이여서 선언문 들은 다른 실행문 보다 위로 올라간다.

function test2() {
    console.log('test1');
}

// -------------------------------------------------- 화살표 함수

// test3(); // 화살표 함수 는 실행문이다. ; 이 있기 때문에.
            // 그래서 화살표 함수보다 위에 실행하게 되면 실행되지 않는다.
            
const test3 = () => console.log('test3');

test3(); // -> 여기서 실행해야됨.

//

// 화살표 함수와 function 함수의 this가 가르키는 것은 다르다.
// aixos도 화살표 함수말고 function(익명) 함수를 사용할 수 있다.

// 예)
// axios
//           .post(reqUrl, data) // Ajax POST 방식 요청을 보냄.
//           .then(function(response) { // 성공 응답 (response)일 때 실행할 콜백 등록
//               
//              console.log(response);
//               
               // 댓글 목록 갱신.
//               getRepliesWithPostId();
               
               // 댓글 입력한 내용을 지워주기.
//               document.querySelector('textarea#replyText').value = '';
               
//          })
//           .catch((error) => console.log(error)); // 실패(error)일 때 실행할 콜백 등록.      
       
