import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.StringTokenizer;

class Main_백준_21608_골드5_상어초등학교_188ms {

    private static class Student {
        int number;
        HashSet<Integer> set;
        int row, col;

        public Student(int number, HashSet<Integer> set) {
            this.number = number;
            this.set = set;
        }
    }

    private static int N;
    // 상하좌우
    private static final int[] dr = {-1, 1, 0, 0};
    private static final int[] dc = {0, 0, -1, 1};

    public static void main(String[] args) throws Exception{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        /*
         * N 이 주어지고
         * X 학생이 좋아하는 짝의 수 4개를 입력 받는다
         * 순서대로 학생들을 배치한다.
         *
         * 다음 규칙에 따라 학생들을 배치한다.
         * 1. 비어있는 칸 중에서 좋아하는 학생이 인접한 칸에 가장 많은 칸으로 자리를 정한다.
         * 2. 1을 만족하는 칸이 여러개가 있다면, 인접한 칸 중 비이있는 칸이 가장 많은 칸으로 정한다
         * 3. 2번을 만족하는 칸도 여러개라면, 행의 번호가 가장 작은 칸으로, 이마저도 동일하다면 열의 번호가 가장 작은 칸으로 배치한다.
         */

        N = Integer.parseInt(st.nextToken());
        ArrayList<Student> students = new ArrayList<>();

        for (int studentIdx = 0, size = N * N; studentIdx < size; studentIdx++) {
            st = new StringTokenizer(br.readLine(), " ");
            int number = Integer.parseInt(st.nextToken());
            HashSet<Integer> set = new HashSet<>();
            for (int favoriteIdx = 0; favoriteIdx < 4; favoriteIdx++) {
                set.add(Integer.parseInt(st.nextToken()));
            }
            students.add(new Student(number, set));
        }

        int[][] map = new int[N + 2][N + 2];

        initMap(map);
        positionStudents(map, students);

        System.out.println(calculateSatisfaction(map, students));
    }

    private static void initMap(int[][] map) {
        Arrays.fill(map[0], -1);
        Arrays.fill(map[N + 1], -1);
        for (int i = 1; i < N + 2; i++) {
            map[i][0] = -1;
            map[i][N + 1] = -1;
        }
    }
    
    private static void positionStudents(int[][] map, ArrayList<Student> students) {
        /*
         * 1. 비어있는 칸 중에서 좋아하는 학생이 인접한 칸에 가장 많은 칸으로 자리를 정한다.
         * 2. 1을 만족하는 칸이 여러개가 있다면, 인접한 칸 중 비이있는 칸이 가장 많은 칸으로 정한다
         * 3. 2번을 만족하는 칸도 여러개라면, 행의 번호가 가장 작은 칸으로, 이마저도 동일하다면 열의 번호가 가장 작은 칸으로 배치한다.
         */
        for (Student student : students) {
            int studentRow = 0;
            int studentCol = 0;
            // -1 ,-1  로 해야 첫번째 좌표에서 무조건 갱신한다!!
            int countMaxFriend = -1;
            int countMaxEmptySpace = -1;
            for (int row = 1; row < N + 1; row++) {
                for (int col = 1; col < N + 1; col++) {
                    int countFriend = 0;
                    int countEmptySpace = 0;
                    // 현재 Map 을 기준으로, 이미 숫자가 있는 칸을 제외하고
                    if (map[row][col] == 0) {
                        // 1, 2, 3번을 만족하는 칸을 구한다.
                        for (int dir = 0; dir < 4; dir++) {
                            // 주변이 빈공간이면
                            if (map[row + dr[dir]][col + dc[dir]] == 0) {
                                ++countEmptySpace;
                            } else if (student.set.contains(map[row + dr[dir]][col + dc[dir]])) { // 인접한 좌표에 친한 친구가 있다면
                                ++countFriend;
                            }
                        }
                        // 인접한 좌표의 있는 좋아하는 짝꿍의 수 비교
                        if (countFriend > countMaxFriend) {
                            countMaxFriend = countFriend;
                            countMaxEmptySpace = countEmptySpace;
                            studentRow = row;
                            studentCol = col;
                        } else if (countFriend == countMaxFriend && countMaxEmptySpace < countEmptySpace){
                            // 1. 인접한 좌표의 비어 있는 칸의 수
                            countMaxEmptySpace = countEmptySpace;
                            // 2. 행 렬 번호가 더 작은 수로 세팅
                            studentRow = row;
                            studentCol = col;
                        }
                    } // end of if current position not 0
                } // end of for col
            } // end of for row
            // 조건을 만족하는 좌표가 나왔다면 map 에 추가한다.
            student.row = studentRow;
            student.col = studentCol;
            map[studentRow][studentCol] = student.number;
        } // for students
    }
    
    private static int calculateSatisfaction(int[][] map, ArrayList<Student> students) {
        int result = 0;
        // 모든 학생들을 돌면서, 상하좌우에 좋아하는 친구가 얼마나 있는지 확인한다.
        for (Student student : students) {
            int row = student.row;
            int col = student.col;
            int count = 0;
            for (int dir = 0; dir < 4; dir++) {
                int studentNum = map[row + dr[dir]][col + dc[dir]];
                if (student.set.contains(studentNum)) {
                    ++count;
                }
            }
            // count 가 0 이면 0을 더하고, 그 이상이라면 10 의 n-1 승을 더해준다.
            if (count > 0) {
                result += (int) Math.pow(10, count - 1);
            }
        }

        return result;
    }
}
