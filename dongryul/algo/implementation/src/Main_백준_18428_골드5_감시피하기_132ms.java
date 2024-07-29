import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

class Main_백준_18428_골드5_감시피하기_132ms {

    private static class Node {
        int row, col;

        public Node(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Node) {
                Node node = (Node)obj;
                return this.row == node.row && this.col == node.col;
            } else {
                return false;
            }
        }
    }

    private static int N;
    private static char[][] map;
    private static ArrayList<Node> teacherList;
    private static ArrayList<Node> emptySpace;
    private static int[] dr = {-1, 1, 0, 0};
    private static int[] dc = {0, 0, -1, 1};

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        /*
         * 빈칸의 좌표를 모두 하나의 배열에 넣고
         * 장해물 3개를 조합을 통해 선택하고 나서
         * 피할 수 있는지를 확인한다.
         *
         * 50 C 3 이기 때문에, 복잡도는 가능하다
         */

        N = Integer.parseInt(st.nextToken());

        map = new char[N][N];
        teacherList = new ArrayList<>();
        emptySpace = new ArrayList<>();

        for (int row = 0; row < N; row++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int col = 0; col < N; col++) {
                map[row][col] = st.nextToken().charAt(0);
                if (map[row][col] == 'X') {
                    emptySpace.add(new Node(row, col));
                } else if (map[row][col] == 'T') {
                    teacherList.add(new Node(row, col));
                }
            }
        }

        String result = "NO";
        if (isAbleToHide(0, 0, new boolean[emptySpace.size()], new int[3])) {
            result = "YES";
        }

        System.out.println(result);

    }

    private static boolean isAbleToHide(int start, int count, boolean[] usedWallPosition, int[] wall) {
        if (count == 3) {
            // 벽 3개를 설치해서 확인한다.
            for (int wallIndex = 0; wallIndex < 3; wallIndex++) {
                Node currWall = emptySpace.get(wall[wallIndex]);
                map[currWall.row][currWall.col] = 'O';
            }
            // 벽 뒤에 숨었더라면
            if (checkHide()) {
                return true;
            } else {
                // 벽 원상복구
                for (int wallIndex = 0; wallIndex < 3; wallIndex++) {
                    Node currWall = emptySpace.get(wall[wallIndex]);
                    map[currWall.row][currWall.col] = 'X';
                }
                return false;
            }
        }
        for (int idx = start, size = emptySpace.size(); idx < size; idx++) {
            if (!usedWallPosition[start]) {
                usedWallPosition[idx] = true;
                wall[count] = idx;
                if (isAbleToHide(idx + 1, count + 1, usedWallPosition, wall)) {
                    return true;
                }
                wall[count] = 0;
                usedWallPosition[idx] = false;
            }
        }
        return false;
    }

    private static boolean checkHide() {
        for (int teacherIdx = 0; teacherIdx < teacherList.size(); teacherIdx++) {
            // 선생님을 선택
            Node teacher = teacherList.get(teacherIdx);
            // 상하좌우를 탐색하며 학생을 찾는다.
            for (int dir = 0; dir < 4; dir++) {
                int n = 1;
                boolean foundStudent = false;
                while (!foundStudent) {
                    try {
                        if (map[teacher.row + dr[dir] * n][teacher.col + dc[dir] * n] == 'S') {
                            foundStudent = true;
                        } else if (map[teacher.row + dr[dir] * n][teacher.col + dc[dir] * n] == 'O') {
                            break;
                        }
                        ++n;
                    } catch (ArrayIndexOutOfBoundsException e) {
                        break;
                    }
                }
                if (foundStudent) {
                    return false;
                }
            }
        }

        return true;
    }
}
