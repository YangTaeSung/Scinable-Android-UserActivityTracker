public class Tracker {
	 
	// ���� Ʈ��Ŀ �ڵ带 �߰����� �� scq ť�� ���������.
	// ������ �������� ������ Tag,Content�� ���̶�� ����, �� ���� scqť ����
	// ť�� ������� ���Ƿ� 30 ����
	int queueSize = 30;
	SCQ scqTag = new SCQ(queueSize);
	SCQ scqContent = new SCQ(queueSize);
	
	// sc.js�� setAccount�� Ʈ��Ŀ�� ��ü �޼ҵ� �̸�, accountId�� ��ü �޼ҵ忡 ������ �μ�
	// push�ϸ鼭 ť�� ���� ����
	// Tracker�� �޼ҵ�� ���� - �Ű������� ���޹��� accountId�� ť�� ����
	public void setAccount(String accountId) {
		
		// ť�� ���� �� ���
		if(scqContent.isFull()) {
			System.out.println("scq is full");
		}
		
		// 
		else {
			scqTag.enqueue("setAccount");
			scqContent.enqueue(accountId);
		}
		
	}
	
	/* ť�� push�� ���� �����͵��� ������ �ϰ� ���� */
	public void trackPageview() {
		
		// Tag�� Content�� ������ ���ִ� ������ scq ť�迭�� ������ �ϰ�����
		// dequeue() �޼ҵ�� �����͸� ������ �� ���� ��
		while(!scqContent.isEmpty()) {
		
			sendToServer(scqTag.dequeue(), scqContent.dequeue());
		
		}
		
	}
	
	public void sendToServer(String tag, String content) {
		//
		// ������ ������ ������ �ȵ���̵忡�� �����غ���
		//
	}
	
}
