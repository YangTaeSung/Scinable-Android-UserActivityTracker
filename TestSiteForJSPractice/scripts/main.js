// document.querySelector나 alert는 언제든지 사용 가능할 수 있게 브라우저에 내장되어 있습니다.
let myImage = document.querySelector('img');

// onclick 이벤트 핸들러
myImage.onclick = function() {
    let mySrc = myImage.getAttribute('src');
    if(mySrc === 'images/Git.png') {
      myImage.setAttribute ('src','images/Android.png');
    } else {
      myImage.setAttribute ('src','images/Git.png');
    }
}

let myButton = document.querySelector('button');
let myHeading = document.querySelector('h1');

function setUserName() {
    //prompt()함수는 alert()와 비슷하게 대화상자를 불러오는 함수를 포함합니다. prompt()는 사용자에게 어떤 데이터를 입력하길 요청하고, 사용자가 OK를 누른 후에 그 값을 변수에 저장합니다.
    //브라우저에 데이터를 저장하고 나중에 불러올 수 있도록 해주는 localStorage라는 API를 호출합니다. setItem()으로 'name'이라는 항목을 생성합니다. 사용자에게 입력받은 데이터가 myName이 됩니다.
    //textContent로 유저의 이름을 포함한 스트링이 브라우저에 노출되게 합니다.
    //사용자가 입력을 취소했을 때, null이 저장되고, 공백인 상태에서 Ok버튼을 누르게 되면 공백이 저장됩니다. 이를 방지하기 위해 if조건문을 추가합니다.
  let myName = prompt('Please enter your name.');
  if(!myName || myName === null) {
      setUserName();
  } else {
      localStorage.setItem('name', myName);
      myHeading.textContent = 'Mozilla is cool, ' + myName;
  }
}

//부정 연산자로 'name'항목이 있는지 검사합니다. 없으면 'name'항목을 사용자로부터 입력받기 위해 setUserName()함수를 호출합니다.
if(!localStorage.getItem('name')) {
  setUserName();
} else { //'name'항목이 존재한다면 setUserName()함수의 기능과 똑같이 동작합니다.
  let storedName = localStorage.getItem('name');
  myHeading.textContent = 'Mozilla is cool, ' + storedName;
} //설정해놓은 이름은 localStorage에 저장되기 때문에 사이트를 닫은 후 재접속해도 그대로 유지됩니다.

//유저가 원하는 이름을 언제든지 다시 설정할 수 있도록 합니다.
myButton.onclick = function() {
  setUserName();
}