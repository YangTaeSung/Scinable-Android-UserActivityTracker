package QueuePractice;
/*
 * 
 * Scinable - Ken
 * 
 * ���� ������ ���� ����
 *
 */


interface Queue {
    boolean isEmpty();
    boolean isFull();
    void enqueue(String item);
    String dequeue();
    String peek();
    void clear();
}
 
// sc.js�� _scq�迭 -> �񵿱� ������ �����ϰ� �ϴ� ��, ť ����
// scq�� ��ü�� ����� ���� Ŭ���� ����
public class QueuePractice implements Queue {
    
    private int front;
    private int rear;
    private int queueSize;
    private String queueArr[];
 
    // ť�� �����ϴ� ������
    public QueuePractice(int queueSize) {
        front = -1;    // front ������ �ʱ�ȭ
        rear = -1;    // rear ������ �ʱ�ȭ
        this.queueSize = queueSize;    // queue ������ ����
        queueArr = new String[this.queueSize];    // ť �迭 ����
    }
    
    // ť�� ����ִ� �������� Ȯ��
    public boolean isEmpty() {
    	
        // front, rear �����Ͱ� ���� ��� �����Ͱ� ���� �����̹Ƿ� �����͸� ��� -1�� �ʱ�ȭ
        if(front == rear) {
            front = -1;
                rear = -1;
        }
        
        // front, rear �����Ͱ� ���� ��� �����Ͱ� ���� �����̹Ƿ� true �ƴ� ��� false return
        return (front == rear);
    }
    
    // ť�� ������ �������� Ȯ��
    public boolean isFull() {
        // rear �����Ͱ� ť�� ������ �ε����� ������ ��� true �ƴ� ��� false return
        return (rear == this.queueSize-1);
    }
    
    // ť�� ������ ����
    public void enqueue(String item) {
        if(isFull()) {
            System.out.println("Queue is full!");
        } else {             
            queueArr[++rear] = item;    // ���� rear �����Ͱ� ����Ű�� ��ġ�� ������ �߰�
            System.out.println("Inserted Item : " + item);
        }
    }
    
    // ť���� ������ ���� �� ����
    public String dequeue() {
        if(isEmpty()) {
            System.out.println("Deleting fail! Queue is empty!");
            return "";
        } else {
            // ť���� ������ ������ ��ȯ
            System.out.println("Deleted Item : " + queueArr[front+1]);
 
            // front �����ʹ� ������ ��ġ�� �ִ� �����̹Ƿ� ������ ���� (front + 1) % size �� ����.
            // front + 1 �� �����ϸ� front �����Ͱ� ������ ��ҿ� ��ġ���� ���,
            // ArrayOutOfBoundException�� �߻��ϱ� ������ (front + 1) % size �� ��������.
            // ex) ť�� size�� 5�� �� (index ������ 0 ~ 4)
            // index of front 3: (3 + 1) % 5 = 4
            // index of front 4: (4 + 1) % 5 = 0
            front = (front + 1) % this.queueSize;
 
            return queueArr[front];
        }
                
    }
    
    // ť�� ù��° ������ ����
    public String peek() {
        if(isEmpty()) {
            System.out.println("Peeking fail! Queue is empty!");
            return "";
        } else { 
            // front �����ʹ� ������ ��ġ�� �����Ƿ� +1�� ���༭ ù��° ��Ҹ� �����ϵ��� ����.
            System.out.println("Peeked Item : " + queueArr[front+1]);
            return queueArr[front+1];
        }
    }
    
    // ť �ʱ�ȭ
    public void clear() {
        if(isEmpty()) {
            System.out.println("Queue is already empty!");
        } else {
            front = -1;    // front ������ �ʱ�ȭ
            rear = -1;    // rear ������ �ʱ�ȭ
            queueArr = new String[this.queueSize];    // ���ο� ť �迭 ����
            System.out.println("Queue is clear!");
        }
    }
    
    // ť�� ����� ��� �����͸� ���
    public void printQueue() {
        if(isEmpty()) {
            System.out.println("Queue is empty!");
        } else {
            System.out.print("Queue elements : ");
            // front �����ʹ� -1 �Ǵ� ������ ����� ��ġ�� �ֱ� ������,
            // +1 ��ġ�� ���������� ����.
            for(int i=front+1; i<=rear; i++) {
                System.out.print(queueArr[i] + " ");
            }
            System.out.println();
        }
    }
 
   /* 
    * queue �۵� Ȯ�� ����
    * 
    * public static void main(String args[]) {
        int queueSize = 5;
        QueuePractice arrQueue = new QueuePractice(queueSize);
        
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

