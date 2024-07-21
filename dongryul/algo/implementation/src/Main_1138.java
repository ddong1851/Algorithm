import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.LinkedList;
import java.util.StringTokenizer;

class Main_1138 {

//    private static class Person implements Comparable<Person> {
//        int weight;
//        int position;
//
//        public Person(int weight, int position) {
//            this.weight = weight;
//            this.position = position;
//        }
//
//        @Override
//        public int compareTo(Person person) {
//            int weightComparison = Integer.compare(this.weight, person.weight);
//            if (weightComparison != 0) {
//                return weightComparison;
//            }
//            return Integer.compare(this.position, person.position);
//        }
//    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        /*
         * N 명의 사람들이 있고
         * 키는 1 부터 N 까지 있다.
         * 입력은
         * N
         * X X X X X..
         * X 는 해당 자리의 있는 사람 왼쪽에
         * 본인보다 더 키가 큰 사람들의 수를 의미한다.
         *
         * 마지막 출력은
         * 순서대로 각 사람들이 어디에 서 있는지 작성하면 된다 (1~N)
         *
         * Person 이라는 객체를 만들고
         * 1. 가중치
         * 2. 몇 번째 순서
         * 를 기준으로 compare 할 수 있도록 하여
         * 순서대로 나열한다.
         * ===> 실패. 정렬만 해서는 안된다.
         *
         * 2. 정답 참고
         * 키가 큰 사람부터 줄을 세우는데,
         * 규칙에만 맞추면 된다. (왼쪽에 자기보다 키 큰 사람의 수 규칙)
         *
         * 1. 키가 큰 순서로 정렬한다.
         * 2. 규칙에 맞춰 줄을 세운다.
         */

        int N = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());

        int[] line = new int[N+1]; //줄에 선 순서
        for (int i = 1; i<=N; i++) {
            int counts = Integer.parseInt(st.nextToken());//i: 본인 키, counts = 본인보다 왼쪽에 큰사람
            for (int j = 1; j<=N; j++) {
                if (counts == 0) { //본인보다 키가 큰 사람이 없다-> i는 키순서로 들어오기 때문에 본인보다 큰 사람이 없다면 그자리가 본인 자리임
                    if (line[j] == 0) { //자리에 아무도 없어서 줄 섰다.
                        line[j] = i;
                        break;
                    }
                    else { //본인보다 큰 사람이 왼편에 없지만 이미 누군가 자기보다 작은 사람이 서있다 -> 양보하고 다른 빈칸중 가장 가까운곳에 서면 된다
                        continue;
                    }
                }
                else if (line[j] == 0) { //아직 count가 0이 아님-> 본인보다 큰 사람 존재하므로 다음칸으로 가보자
                    counts--;
                }
            }
        }

        //Print Answer:
        for (int i = 1; i <= N; i++) {
            System.out.print(line[i]+" ");
        }
    }
}
