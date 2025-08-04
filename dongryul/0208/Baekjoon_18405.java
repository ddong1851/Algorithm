import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Baekjoon_18405 {

    private static class Point {
        int row;
        int col;
        public Point(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    private static class Virus {
        int key;
        private final List<Point> pointList = new ArrayList<>();

        public Virus(int key) {
            this.key = key;
        }

        public List<Point> getPointList() {
            return pointList;
        }

        public void addPoint(Point point) {
            pointList.add(point);
        }
    }

    public static void main(String[] args) {

        /*
         * N X N
         * 모든 바이러스는 1 ~ K 번 바이러스에 속함
         * 바이러스는 1초마다 상하좌우 방향으로 증식함
         * 단, 매 초마다 번호가 낮은 종류의 바이러스 먼저 증식한다.
         * 증식 과정에서 한 칸에 이미 바이러스가 존재한다면 증식 X
         *
         * S초가 지난 후에 좌표에 (X, Y) 존재하는 바이러스의 종류를 출력하는 프로그램 작성
         * 단, 바이러스가 없으면 0 출력
         *
         * 시험관의 가장 왼쪽 위는 (1,1)
         *
         * 바이러스의 순서대로 좌표값을 저장할 List 필요 + 움직일 수 없다면 증식 리스트에서 제거
         */

        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
            // 1. N K 파싱
            String[] splitData = bufferedReader.readLine().split(" ");
            int N = Integer.parseInt(splitData[0]);
            int K = Integer.parseInt(splitData[1]);

            // 2. N+2 MAP 생성, 외곽은 -1 로 설정 (테두리)
            int[][] map = new int[N+2][N+2];
            Arrays.fill(map[0], -1);
            Arrays.fill(map[N+1], -1);
            for (int idx = 1; idx < N + 2; idx++) {
                map[idx][0] = -1;
                map[idx][N+1] = -1;
            }

            Virus[] virusArray = new Virus[K + 1];
            // 3. 좌표 확인 + virus 관리 array 에 추가
            for (int row = 1; row < N + 1; row++) {
                splitData = bufferedReader.readLine().split(" ");
                for (int col = 0; col < splitData.length; col++) {
                    if (!Objects.equals(splitData[col], "0")) {
                        int virusNum = Integer.parseInt(splitData[col]);
                        map[row][col + 1] = virusNum;
                        if (virusArray[virusNum] == null) {
                            Virus virus = new Virus(virusNum);
                            virus.addPoint(new Point(row, col + 1));
                            virusArray[virusNum] = virus;
                        } else {
                            virusArray[virusNum].addPoint(new Point(row, col + 1));
                        }
                    }
                }
            }

            // 4. 시간과 위치 읽기
            splitData = bufferedReader.readLine().split(" ");
            int s = Integer.parseInt(splitData[0]);
            int targetX = Integer.parseInt(splitData[1]);
            int targetY = Integer.parseInt(splitData[2]);


        } catch (IOException e) {

        }
    }

}
