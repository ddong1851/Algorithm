import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

class Main_백준_1967_골드4_트리의지름_2484 {

    private static class Node {
        int idx, weight;

        private Node(int num, int weight) {
            this.idx = num;
            this.weight = weight;
        }
    }

    private static int N, max;
    private static boolean[] visited;
    private static ArrayList<ArrayList<Node>> nodeList = new ArrayList<>();

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        /*
         * 깊이 우선 탐색을 통해, 모든 Node 들에 대해서 브루트포스? 방식으로 탐색을 진행한다.
         */

        N = Integer.parseInt(st.nextToken());

        visited = new boolean[N + 1];
        for (int idx = 0; idx < N + 1; idx++) {
            nodeList.add(new ArrayList<Node>());
        }

        max = Integer.MIN_VALUE; // max value 로 갱신할거기 때문에 min 값으로 초기화 한다.

        for (int idx = 0; idx < N - 1; idx++) {
            st = new StringTokenizer(br.readLine(), " ");
            int currNode = Integer.parseInt(st.nextToken());
            int connectedNodeNum = Integer.parseInt(st.nextToken());
            int weight = Integer.parseInt(st.nextToken());
            nodeList.get(currNode).add(new Node(connectedNodeNum, weight));
            nodeList.get(connectedNodeNum).add(new Node(currNode, weight));
        }

        for (int idx = 1; idx < N + 1; idx++) {
            Arrays.fill(visited, false);
            visited[idx] = true; // 현재 시작 지점을 다시 방문하지 않도록
            dfs(idx, 0);
        }

        System.out.println(max);
    }

    private static void dfs(int currNode, int length) {
        max = Math.max(max, length);
        for (Node node: nodeList.get(currNode)) {
            if (!visited[node.idx]) {
                visited[node.idx] = true;
                dfs(node.idx, length + node.weight);
            }
        }
    }
}
