import java.io.BufferedReader;
import java.io.InputStreamReader;

/** Main_백준_5639_이진검색트리_골드5_444ms*/
class Main_5639_이진검색트리_골드5_김동률_444ms {
	
	static StringBuilder sb = new StringBuilder();
	// 빈 루트 노드 생성
	static Node root = null;
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		/* 문제 접근
		 * --> 루트 노드보다 크면 오른쪽, 작으면 왼쪽
		 * 서브 트리도 마찬가지
		 * 
		 * 전위 순회로 방문된 데이터를 입력받아
		 * 트리를 구성하여
		 * 이를 후위 순회로 반환한하.
		 * 
		 * + 노드의 수는 10000개 이하, 키 값은 100000보다 작은 양수
		 * --> 데이터 관리는 Int 형으로 할 수 있다.
		 * 
		 * 1. 이진 트리를 구현하자
		 * --> add로 트리의 데이터를 초기화
		 * --> root가 초기화되지 않았다면 root 초기화
		 * --> 초기화 되었다면 value 비교하여 왼쪽으로 갈지, 좌측으로 갈지 비교
		 * --> 노드가 비었다면 해당 자리로 연결
		 * --> 데이터를 후위 순회로 출력
		 */
		
		// 입력된 데이터가 있을 때 이진 트리 초기화
		while(true) {
			try{
				int val = Integer.parseInt(br.readLine());
				addNode(val);
			} catch (Exception e) {
				break;
			}
		} // end of while
		// 후위 순회 출력을 sb에 저장
		PostPrintTree(root);
		// 후위순회 데이터 출력
		System.out.println(sb.toString());
		
	} // end of main

	
	private static void PostPrintTree(Node root) {
		// L -> R -> Root
		if(root==null) return;
		PostPrintTree(root.left);
		PostPrintTree(root.right);
		
		sb.append(root.val).append("\n");
	}

//  이 부분 최적화된 코드 보고 했습니다 
//	혼자 짤 때 curr.left = new Node(val);
//	위 부분을 curr = new Node(val) 로 하면서 노드를 초기화 했었는데
//	이렇게 하면 left right가 초기화되지 않는다.
//	반복문으로 해결
	private static void addNode(int val) {
		// root가 null 이면 초기화
		if(root==null) {
			root = new Node(val);
		}
		// root가 null이 아닌 경우
		else {
			// 루트 노드 기준으로 더 작으면 왼쪽, 더 크면 오른쪽
			Node top = root;
			Node curr;
			// 좌측으로 보내야 하는 경우
			while(true) {
				// 비교대상 노드
				curr = top;
				// 현재 노드 기준 비교, 현재 노드값이 더 크면 왼쪽으로
				if(top.val > val) {
					top = top.left;
					// 빈 노드를 만나게 된다면 초기화
					if(top==null) {
						curr.left = new Node(val);
						break;
					}
				}
				// 현재 노드 값이 더 작다면 오른쪽으로
				else {
					top = top.right;
					if(top==null) {
						curr.right = new Node(val);
						break;
					}
				}
			} // end of while
		} // end of else
	} // end of addNode
	
} // end of Main

/** 트리를 이루는 노드의 정보를 저장하는 데이터 타입*/
class Node{
	// 왼쪽 
	public Node left;
	// 오른쪽
	public Node right;
	// 값
	public int val;
	// 이진 노드를 만들 때 값을 넣고 홀로 서 있게 양쪽 연결 여부는 null로
	public Node(int val) {
		super();
		this.val = val;
		this.left = null;
		this.right = null;
	}
}
