package com.baekjoon.implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class baekjoon_21610_gold_5_324ms_40392kb {

    private static int N, M;

    private static class RainFunction {
        int dir;
        int distance;
        public RainFunction(int dir, int distance) {
            this.dir = dir;
            this.distance = distance;
        }
    }

    private static class Point {
        int row, col;
        public Point(int row, int col) {
            this.row = row;
            this.col = col;
        }

        // HashSet contains 사용하기 위한 equals, hashCode 오버라이딩
        @Override
        public boolean equals(Object obj) {
            boolean result = false;
            if (obj instanceof Point) {
                result = this.row == ((Point) obj).row && this.col == ((Point) obj).col;
            }
            return result;
        }

        @Override
        public int hashCode() {
            return this.row * 10000 + this.col;
        }
    }

    // -, ←, ↖, ↑, ↗, →, ↘, ↓, ↙
    private static final int[] xDir = {0, -1, -1, 0, 1, 1, 1, 0, -1};
    private static final int[] yDir = {0, 0, -1, -1, -1, 0, 1, 1, 1};

    private static final String REGEX_SPACE = " ";

    public static void main(String[] args) {

        /**
         * NxN 격자가 있고
         * 가장 왼쪽은 1,1 우측 하단은 N, N
         *
         * 1) 바구니는 물을 무한으로 저장할 수 있다
         * 2) 비바라기를 시전하면 N,1 / N,2 / N-1,1, / N-1,2 에 비구름이 생김
         * 3) 구름 이동은 M 번
         * 4) 이동 명령은 방향(d)과 거리(s)로 이루어져 있다
         * 5) 방향은 1부터 ←, ↖, ↑, ↗, →, ↘, ↓, ↙
         *
         * 1) 모든 구름이 di 방향으로 si칸 이동한다.
         *    이동 시 나머지 연산과 음수 처리 필요
         * 2) 각 구름에서 비가 내려 구름이 있는 칸의 바구니에 저장된 물의 양이 1 증가한다.
         * 3) 구름이 모두 사라진다.
         * 4) 2에서 물이 증가한 칸 (r, c)에 물복사버그 마법을 시전한다.
         *    물복사버그 마법을 사용하면, 대각선 방향으로 거리가 1인 칸에 물이 있는 바구니의 수만큼
         *    (r, c)에 있는 바구니의 물이 양이 증가한다.
         *    이때는 이동과 다르게 경계를 넘어가는 칸은 대각선 방향으로 거리가 1인 칸이 아니다.
         *    예를 들어, (N, 2)에서 인접한 대각선 칸은 (N-1, 1), (N-1, 3)이고, (N, N)에서 인접한 대각선 칸은 (N-1, N-1)뿐이다.
         * 5) 바구니에 저장된 물의 양이 2 이상인 모든 칸에 구름이 생기고,
         *    물의 양이 2 줄어든다. 이때 구름이 생기는 칸은 3에서 구름이 사라진 칸이 아니어야 한다.
         */

        int result = 0;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            String[] splitData = br.readLine().split(REGEX_SPACE);
            N = Integer.parseInt(splitData[0]);
            M = Integer.parseInt(splitData[1]);

            // 격자 추출
            List<String[]> mapDataList = new ArrayList<>();
            for (int mapIdx = 0; mapIdx < N; mapIdx++) {
                splitData = br.readLine().split(REGEX_SPACE);
                mapDataList.add(splitData);
            }
            int[][] map = createMap(N, mapDataList);

            // 함수 추출
            List<String[]> functions = new ArrayList<>();
            for (int functionIdx = 0; functionIdx < M; functionIdx++) {
                splitData = br.readLine().split(REGEX_SPACE);
                functions.add(splitData);
            }
            RainFunction[] rainFunctions = createRainFunction(M, functions);
            result = countTotalWater(map, rainFunctions, N);
        } catch (IOException ignore) { }
        System.out.println(result);
    }

    private static int countTotalWater(int[][] map, RainFunction[] rainFunctions, int N) {
        int result = 0;
        List<Point> startPointList = new ArrayList<>();
        List<Point> tempCloudList;
        Set<Point> erasedCloudSet = new HashSet<>();
        startPointList.add(new Point(N-1, 0));
        startPointList.add(new Point(N-1, 1));
        startPointList.add(new Point(N-2, 0));
        startPointList.add(new Point(N-2, 1));
        for (RainFunction function: rainFunctions) {
            // 1. 모든 구름 이동
             tempCloudList = moveClouds(startPointList, function);
            // 2. 각 구름에서 비가 내려 구름이 있는 칸의 물의 양이 1 증가
            for (Point point: tempCloudList) {
                map[point.row][point.col] += 1;
                // 3. 구름이 모두 사라짐 --> HashSet 에 사라진 구름 칸에 대한 것을 저장
                erasedCloudSet.add(point);
            }
            // 4. 물이 증가한 칸에 물복사버그 마법을 시전
            pourWater(map, tempCloudList);
            tempCloudList.clear();

            // 5. 바구니에 저장된 물의 양이 2 이상인 모든 칸에 구름이 생기고, 물의 양이 2 줄어든다.
            for (int row = 0; row < N; row++) {
                for (int col = 0; col < N; col++) {
                    Point currPoint = new Point(row, col);
                    // 이때 3에서 삭제된 칸은 아니여야 한다.
                    if (map[row][col] >= 2 && !erasedCloudSet.contains(currPoint)) {
                        tempCloudList.add(currPoint);
                        map[row][col] -= 2;
                    }
                }
            }

            // 시작 구름 지점 교체
            startPointList = new ArrayList<>(tempCloudList); // 얕은 복사
            erasedCloudSet.clear();
            tempCloudList.clear();
        }

        // 전체 합
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                result += map[row][col];
            }
        }

        return result;
    }

    private static List<Point> moveClouds(List<Point> pointList, RainFunction function) {
        List<Point> cloudList = new ArrayList<>();
        for (Point point: pointList) {
            int nextRow = (point.row + (yDir[function.dir] * function.distance)) % N;
            int nextCol = (point.col + (xDir[function.dir] * function.distance)) % N;
            if (nextRow < 0) {
                nextRow += N;
            }
            if (nextCol < 0) {
                nextCol += N;
            }
            cloudList.add(new Point(nextRow, nextCol));
        }
        return cloudList;
    }

    private static void pourWater(int[][] map, List<Point> pointList) {
        int[] increasePointArray = new int[pointList.size()];
        for (int pointIdx = 0; pointIdx < pointList.size(); pointIdx++) {
            Point point = pointList.get(pointIdx);
            for (int dir = 0; dir < 4; dir++) {
                try {
                    int nextRow = point.row + yDir[2 * (dir + 1)];
                    int nextCol = point.col + xDir[2 * (dir + 1)];
                    if (map[nextRow][nextCol] > 0) {
                        increasePointArray[pointIdx] += 1;
                    }
                } catch (IndexOutOfBoundsException ignore) { }
            }
        }

        for (int idx = 0; idx < pointList.size(); idx++) {
            Point point = pointList.get(idx);
            map[point.row][point.col] += increasePointArray[idx];
        }
    }

    private static int[][] createMap(int mapSize, List<String[]> splitDataList) {
        int[][] map = new int[mapSize][mapSize];
        for (int row = 0; row < mapSize; row++) {
            for (int col = 0; col < mapSize; col++) {
                map[row][col] = Integer.parseInt(splitDataList.get(row)[col]);
            }
        }
        return map;
    }

    private static RainFunction[] createRainFunction(int totalFunctionSize, List<String[]> splitDataList) {
        RainFunction[] rainFunctions = new RainFunction[totalFunctionSize];
        for (int functionIdx = 0; functionIdx < totalFunctionSize; functionIdx++) {
            String[] splitData = splitDataList.get(functionIdx);
            int dir = Integer.parseInt(splitData[0]);
            int distance = Integer.parseInt(splitData[1]);
            rainFunctions[functionIdx] = new RainFunction(dir, distance);
        }
        return rainFunctions;
    }

}
