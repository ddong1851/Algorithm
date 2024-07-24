import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

class Main_16234 {
    private static int N, L, R;
    private static int[][] map;
    private static HashMap<Point, Point> pointParentMap = null;
    private static HashMap<Point, HashSet<Point>> pointChildMap = null;
    private static final int WALL_VALUE = 201;

    private static class Point {
        int row, col;

        public Point(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return row == point.row && col == point.col;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, col);
        }
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");

        /*
         * 1. 현재 이동에서 연합을 구한다.
         * 2. 연합에 속한 국가들끼리 사람들을 균일하게 나누어 갖는다. (소수점 버림)
         * ==> 이를 반복하며 인구 이동이 며칠 동안 발생하는지 구한다.
         *
         *
         * 포인트
         * 1. 연합을 구하는 알고리즘은? ( UnionFind? )
         * UnionFind 를 돌려서,,,, 연합을 찾고, 연합끼리 인원을 나눈다를 반복?
         */

        N = Integer.parseInt(st.nextToken());
        L = Integer.parseInt(st.nextToken());
        R = Integer.parseInt(st.nextToken());

        initMap();

        for (int row = 1; row < N + 1; row++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int col = 1; col < N + 1; col++) {
                map[row][col] = Integer.parseInt(st.nextToken());
            }
        }

        // 상하좌우
        int[] dr = new int[] {-1, 1, 0, 0};
        int[] dc = new int[] {0, 0, -1, 1};

        int result = 0;
        while (result < 2000) {
            initData();

            boolean isFoundUnion = false;
            for (int r = 1; r < N + 2; r++) {
                for (int c = 1; c < N + 2; c++) {
                    // 각 자리를 돌면서 union 을 한다.
                    Point curr = new Point(r, c);
                    for (int d = 0; d < 4; d++) {
                        Point temp = new Point(r + dr[d], c + dc[d]);
                        if (isUniondale(curr, temp)) {
                            isFoundUnion = true;
                            union(curr, temp);
                        } // end of if uniondale
                    } // end of for direction
                } // end of for col
            } // end of for row

            // union 에 실패했다면
            if (!isFoundUnion) {
                break;
            }

            // 각각 동료? 들끼리 인구를 나눈다.


            ++result;
        } // end of while

    } // end of main

    private static boolean isUniondale(Point a, Point b) {
        int diff = Math.abs(map[a.row][a.col] - map[b.row][b.col]);
        return diff >= L && diff <= R;
    }

    private static void initMap() {
        map = new int[N + 2][N + 2];
        Arrays.fill(map[0], WALL_VALUE);
        Arrays.fill(map[N + 1], WALL_VALUE);
        for (int r = 1; r < N + 1; r++) {
            map[r][0] = WALL_VALUE;
            map[r][N + 1] = WALL_VALUE;
        }
    }

    private static void initData() {
        if (pointParentMap == null) {
            pointParentMap = new HashMap<>();
        }
        pointParentMap.clear();

        if (pointChildMap == null) {
            pointChildMap = new HashMap<Point, HashSet<Point>>();
        }
        pointChildMap.clear();

        for (int r = 1; r < N + 1; r++) {
            for (int c = 1; c < N + 1; c++) {
                Point currPoint = new Point(r, c);
                pointParentMap.put(currPoint, currPoint);

                pointChildMap.put(currPoint, new HashSet<Point>());
                pointChildMap.get(currPoint).add(currPoint);
            }
        }
    }

    private static void union(Point a, Point b) {
        Point rootA = findParent(a);
        Point rootB = findParent(b);
        if (rootA != rootB) { // 서로의 부모가 다르다면, 자식이 더 많은 부모에 이어준다, 동일하다면 r, c 가 더 작은 값으로 이어준다.
            if (pointChildMap.get(rootA).size() > pointChildMap.get(rootB).size()) {

            } else if (pointChildMap.get(rootA).size() < pointChildMap.get(rootB).size()) {

            } else {
                // r, c 가 더 작은 값에 추가.

            }
        }
    }

    private static Point findParent(Point target) {
        if (pointParentMap.get(target) == target) {
            return target;
        } else {
            pointParentMap.put(target, findParent(pointParentMap.get(target)));
            return pointParentMap.get(target);
        }
    }
}
