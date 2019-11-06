/*
 * Scinable - Ken
 * 
 * 
 * Tracker Object의 개념 이해 부족
 * sc.js는 scq 배열에 push할 때, Tracker 메소드와 그 인자를 보내는 형식인데
 * 본 프로젝트에서는 큐 두개 만들고 Tracker 메소드의 이름과 인자의 이름 두 개만 서버로 보내주도록 만드는 중
 * "_trackPageview" 처럼 데이터를 모아두었다가 일괄적으로 서버에 보내는 작업 필요 - while문으로 구현, 더 알아볼 것
 *
 * 
 */



// scq 큐를 배열로 구현
interface Queue {
    boolean isEmpty();
    boolean isFull();
    void enqueue(String item);
    String dequeue();
    String peek();
    void clear();
}
 
// sc.js의 _scq배열 -> 비동기 구문을 가능하게 하는 것, 큐 역할
// scq를 객체로 만들기 위해 클래스 생성
public class SCQ implements Queue {
    
    private int front;
    private int rear;
    private int queueSize;
    private String queueArr[];
 
    // 큐를 생성하는 생성자
    public SCQ(int queueSize) {
        front = -1;    // front 포인터 초기화
        rear = -1;    // rear 포인터 초기화
        this.queueSize = queueSize;    // queue 사이즈 설정
        queueArr = new String[this.queueSize];    // 큐 배열 생성
    }
    
    // 큐가 비어있는 상태인지 확인
    public boolean isEmpty() {
    	
        // front, rear 포인터가 같은 경우 데이터가 없는 상태이므로 포인터를 모두 -1로 초기화
        if(front == rear) {
            front = -1;
                rear = -1;
        }
        
        // front, rear 포인터가 같은 경우 데이터가 없는 상태이므로 true 아닌 경우 false return
        return (front == rear);
    }
    
    // 큐가 가득찬 상태인지 확인
    public boolean isFull() {
        // rear 포인터가 큐의 마지막 인덱스와 동일한 경우 true 아닌 경우 false return
        return (rear == this.queueSize-1);
    }
    
    // 큐에 데이터 삽입
    public void enqueue(String item) {
        if(isFull()) {
            System.out.println("Queue is full!");
        } else {             
            queueArr[++rear] = item;    // 다음 rear 포인터가 가리키는 위치에 데이터 추가
            System.out.println("Inserted Item : " + item);
        }
    }
    
    // 큐에서 데이터 추출 후 삭제
    public String dequeue() {
        if(isEmpty()) {
            System.out.println("Deleting fail! Queue is empty!");
            return "";
        } else {
            // 큐에서 삭제할 데이터 반환
            System.out.println("Deleted Item : " + queueArr[front+1]);
 
            // front 포인터는 삭제할 위치에 있는 상태이므로 다음과 같이 (front + 1) % size 로 설정.
            // front + 1 로 설정하면 front 포인터가 마지막 요소에 위치했을 경우,
            // ArrayOutOfBoundException이 발생하기 때문에 (front + 1) % size 로 설정해줌.
            // ex) 큐의 size가 5일 때 (index 범위는 0 ~ 4)
            // index of front 3: (3 + 1) % 5 = 4
            // index of front 4: (4 + 1) % 5 = 0
            front = (front + 1) % this.queueSize;
 
            return queueArr[front];
        }
                
    }
    
    // 큐의 첫번째 데이터 추출
    public String peek() {
        if(isEmpty()) {
            System.out.println("Peeking fail! Queue is empty!");
            return "";
        } else { 
            // front 포인터는 삭제한 위치에 있으므로 +1을 해줘서 첫번째 요소를 추출하도록 지정.
            System.out.println("Peeked Item : " + queueArr[front+1]);
            return queueArr[front+1];
        }
    }
    
    // 큐 초기화
    public void clear() {
        if(isEmpty()) {
            System.out.println("Queue is already empty!");
        } else {
            front = -1;    // front 포인터 초기화
            rear = -1;    // rear 포인터 초기화
            queueArr = new String[this.queueSize];    // 새로운 큐 배열 생성
            System.out.println("Queue is clear!");
        }
    }
    
    // 큐에 저장된 모든 데이터를 출력
    public void printQueue() {
        if(isEmpty()) {
            System.out.println("Queue is empty!");
        } else {
            System.out.print("Queue elements : ");
            // front 포인터는 -1 또는 삭제된 요소의 위치에 있기 때문에,
            // +1 위치를 시작점으로 지정.
            for(int i=front+1; i<=rear; i++) {
                System.out.print(queueArr[i] + " ");
            }
            System.out.println();
        }
    }
 
   /* 
    * queue 작동 확인 예제
    * 
    * public static void main(String args[]) {
        int queueSize = 5;
        SCQ arrQueue = new SCQ(queueSize);
        
        arrQueue.enqueue('A');
        arrQueue.printQueue();
        
        arrQueue.enqueue('B');
        arrQueue.printQueue();
        
        arrQueue.enqueue('C');
        arrQueue.printQueue();
        
        arrQueue.enqueue('D');
        arrQueue.printQueue();
        
        arrQueue.enqueue('E');
        arrQueue.printQueue();
        
        arrQueue.dequeue();
        arrQueue.printQueue();
        
        arrQueue.dequeue();
        arrQueue.printQueue();
        
        arrQueue.dequeue();
        arrQueue.printQueue();
        
        arrQueue.dequeue();
        arrQueue.printQueue();
        
        arrQueue.peek();
        arrQueue.printQueue();
        
        arrQueue.clear();
        arrQueue.printQueue();
    }
    *
    *
    */
}