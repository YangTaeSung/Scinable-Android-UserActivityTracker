public class Tracker {
	 
	// 고객이 트래커 코드를 추가했을 때 scq 큐가 만들어진다.
	// 서버로 보내지는 형식이 Tag,Content의 쌍이라고 가정, 두 개의 scq큐 생성
	// 큐의 사이즈는 임의로 30 지정
	int queueSize = 30;
	SCQ scqTag = new SCQ(queueSize);
	SCQ scqContent = new SCQ(queueSize);
	
	// sc.js의 setAccount는 트래커의 객체 메소드 이름, accountId는 객체 메소드에 전달할 인수
	// push하면서 큐에 들어가는 형식
	// Tracker의 메소드로 생성 - 매개변수로 전달받은 accountId를 큐에 저장
	public void setAccount(String accountId) {
		
		// 큐가 가득 찬 경우
		if(scqContent.isFull()) {
			System.out.println("scq is full");
		}
		
		// 
		else {
			scqTag.enqueue("setAccount");
			scqContent.enqueue(accountId);
		}
		
	}
	
	/* 큐에 push해 놓은 데이터들을 서버로 일괄 전송 */
	public void trackPageview() {
		
		// Tag와 Content가 쌍으로 들어가있는 각각의 scq 큐배열을 서버로 일괄전송
		// dequeue() 메소드는 데이터를 추출한 후 삭제 됨
		while(!scqContent.isEmpty()) {
		
			sendToServer(scqTag.dequeue(), scqContent.dequeue());
		
		}
		
	}
	
	public void sendToServer(String tag, String content) {
		//
		// 서버로 보내는 내용은 안드로이드에서 구현해보기
		//
	}
	
}
