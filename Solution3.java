import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;


public class Solution3 {

	static int N, M, K;
	static long[][] D = new long[100001][3];
	static ArrayList<Node>[] adjList = new ArrayList[100001];
	static int from, to;
	static long cost;
	static Comp comp = new Comp();
	static PriorityQueue<Node> pq = new PriorityQueue<Node>(300001, comp);
	
	static class Node {
		int idx;
		long cost;
		int k;
		
		public Node(int idx, long cost, int k) {
			this.idx = idx;
			this.cost = cost;
			this.k = k;
		}
		
		public String toString() {
			return String.format("[%d %d %d]", idx, cost, k);
		}
	}
	
	static class Comp implements Comparator<Node> {

		@Override
		public int compare(Node o1, Node o2) {
			if(o1.cost < o2.cost) {
				return -1;
			} else if(o1.cost > o2.cost) {
				return 1;
			} else {
				return 0;
			}
		}
		
	}
	
	public static void main(String args[]) throws Exception {
		long start = System.currentTimeMillis();
		//System.setIn(new FileInputStream("eval_input02.txt"));
		
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		
		int T = Integer.parseInt(br.readLine().trim());
		
		String[] tokens;
		for(int test_case = 1; test_case <= T; test_case ++) {
			tokens = br.readLine().trim().split(" ");
			
			N = Integer.parseInt(tokens[0]);
			M = Integer.parseInt(tokens[1]);
			K = Integer.parseInt(tokens[2]);
			
			for (int i = 0; i <= N; i++) {
				for (int j = 0; j <= K; j++) {
					D[i][j] = Long.MAX_VALUE;
				}

				if (i != 0 && adjList[i] != null) {
					adjList[i].clear();
				}
			}
			
			for(int i = 0; i < M; i++) {
				tokens = br.readLine().trim().split(" ");
				
				from = Integer.parseInt(tokens[0]);
				to = Integer.parseInt(tokens[1]);
				cost = Integer.parseInt(tokens[2]);
				
				if(adjList[from] == null) {
					adjList[from] = new ArrayList<Node>();
				}
				adjList[from].add(new Node(to, cost, 0));
				
				if(adjList[to] == null) {
					adjList[to] = new ArrayList<Node>();
				}
				adjList[to].add(new Node(from, cost, 0));
			}
			
			tokens = br.readLine().trim().split(" ");
			from = Integer.parseInt(tokens[0]);
			to = Integer.parseInt(tokens[1]);
			
			pq.clear();
			pq.add(new Node(from, 0, 0));
			D[from][0] = 0;
			
			Node first;
			while(pq.size() > 0) {
				first = pq.poll();
				
				if(first.idx == to) {
					break;
				}
				
				// 가져온 코스트가 현재까지 발견된 최소값보다 클 경우 건너 뜀
				if(D[first.idx][first.k] < first.cost) {
					continue;
				}
				
				for(Node node : adjList[first.idx]) {
					// 일반 이동
					if(D[first.idx][first.k] + node.cost < D[node.idx][first.k]) {
						pq.add(new Node(node.idx, D[first.idx][first.k] + node.cost, first.k));
						D[node.idx][first.k] = D[first.idx][first.k] + node.cost;
					}

					// 워프로 이동
					if(first.k + 1 <= K && D[first.idx][first.k] + 1 < D[node.idx][first.k + 1]) {
						pq.add(new Node(node.idx, D[first.idx][first.k] + 1, first.k + 1));
						D[node.idx][first.k + 1] = D[first.idx][first.k] + 1;
					}
				}
			}
			
			System.out.println("#" + test_case + " " + D[to][K]);
		}
		
		//System.out.println(System.currentTimeMillis() - start);
	}
}
