package com.example.sctracker;

// The factory method that creates suggestion object
// 팩토리 메소드 패턴에서는 객체를 생성하기 위한 인터페이스를 정의하는데,
// 어떤 클래스의 인스턴스를 만들지는 서브클래스에서 결정하게 만든다.
// 즉, new생성자는 메인프로그램에 없다. 서브클래스에서 new 하게 된다.

public class Suggest {

    String input = null; // 값을 할당하지 않은 변수 undefined, 임의로 String 잡고 값은 null로 대체
    String button = null;
    String div = null;
    String oldText = "";
    String inputText = "";
    int cline = 0;
    int tline = 0;
    int iline = 0;
    boolean edited = false;
    String req = null;

    // Assign suggestion object to window object because event handlers
    // need to call it in global scope.
    // 이벤트 핸들러가 전역에서 호출되어야하기 때문에 suggestion 객체를 윈도우 객체에 할당하라.

}
