import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Main_백준_1051_숫자정사각형_실버3_216ms {

    private static int N, M, maxLength;
    private static final int DEFAULT_RESULT = 1;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        /*
         * 구현 문제
         * N X M 직사각형에서
         * 꼭지점의 값이 같은 가장 큰 직사각형의 크기를 출력한다.
         *
         * 모든 칸에서, 한칸씩 늘리면서 도출하는거보다, 가장 멀리 있는거부터 찾는게 더 좋을 듯.
         */

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        int[][] map = new int[N][M];

        // 직사각형 데이터 파싱
        for (int row = 0; row < N; row++) {
            st = new StringTokenizer(br.readLine());
            String colData = st.nextToken();
            for (int col = 0; col < M; col++) {
                map[row][col] = colData.charAt(col) - '0';
            }
        }

        // 모든 점 조회
        int result = DEFAULT_RESULT;
        maxLength = Math.min(N, M);
        R: for (int row = 0; row < N; row++) {
            for (int col = 0; col < M; col++) {
                int currMaxLength = solveMaxLength(map, row, col);
                if (currMaxLength == maxLength) {
                    result = maxLength;
                    break R; // 전체 for 문에서 탈출
                } else {
                    result = Math.max(result, currMaxLength);
                }
            }
        }

        // 정답 출력
        System.out.println(result * result);
    }

    private static int solveMaxLength(int[][] map, int startRow, int startCol) {
        int tempLength = maxLength;
        while (tempLength > 0) {
            // 1. 현재 지점에서, tempLength 거리의 좌표를 확인한다.
            try {
                if (map[startRow][startCol] == map[startRow + tempLength][startCol] &&
                    map[startRow][startCol] == map[startRow + tempLength][startCol + tempLength] &&
                    map[startRow][startCol] == map[startRow][startCol + tempLength]
                ) {
                    break;
                }
                // 2. tempLength 거리의 좌표가 없는 경우 tempLength 를 낮춘다
                // 3. tempLength 거리의 좌표가 있는 경우, 4개의 좌표 값이 일치하는지 확인한다.
                // 5. tempLength 거리의 좌표들이 모두 일치하지 않는 경우 tempLength 를 낮춘다.
                --tempLength;
            } catch (ArrayIndexOutOfBoundsException e) {
                --tempLength;
            }
        }
        return tempLength + 1; // 현재 점이랑 떨어진 점의 거리 + 1 해야 현재점을 포함한 길이가 된다.
    }

}
