package cs466_project;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class Overlap_Naive {
	class Edge{
		String target;
		int weight;
		public Edge(String target, int weight) {
			this.target = target;
			this.weight = weight;
		}
		
		public String toString() {
			return target + '(' + weight + ')';
		}
	}

	public Map<String, List<Edge>> getEdge(String[] keys) {
		Map<String, List<Edge>> res = new HashMap<>();
		for (int i = 0; i < keys.length; i++) {
			String source = keys[i];
			res.put(source, new ArrayList<>());
			for (int j = 0; j < keys.length; j++) {
				if (i == j) continue;
				String target = keys[j];
				int weight = checkMatch(source, target);
				if (weight >= 1) {
					res.get(source).add(new Edge(target, weight));
				}
			}
		}
		return res;
	}
	int max = 0;
	String start = "";
	List<Edge> resultEdge = new ArrayList<>();
	
	public String getString(Map<String, List<Edge>> edges) {
		Map<String, List<Edge>> all = new HashMap<>();
		for (Object s : edges.keySet()) {
			Set<String> visited = new HashSet<>();
			List<Edge> res = new ArrayList<>();
			visited.add((String)s);
			dfs((String)s, visited, edges, res, (String)s);
			all.put((String)s, res);
		}
		
//		System.out.println(start);
//		System.out.println(edge);
//		System.out.println();
		StringBuilder sb = new StringBuilder(start);
		for (Edge e : resultEdge) {
			sb.append(e.target.substring(e.weight));
		}
//		System.out.println(max);
		return sb.toString();
		
	}
	
	private void dfs(String s, Set<String> visited, Map<String, List<Edge>> edges, List<Edge> res, String first) {
		if (visited.size() == edges.size()) {
			int cnt = 0;
			for (Edge e : res) {
				cnt += e.weight;
			}
			
			if (cnt > max) {
				start = (String)first;
				resultEdge = new ArrayList<>(res);
				max = cnt;
			}
			return;
		}
		List<Edge> edge = edges.get(s);
		for (Edge e : edge) {
			if (visited.contains(e.target)) continue;
			res.add(e);
			String next = e.target;
			visited.add(next);
			dfs(next, visited, edges, res, first);
			visited.remove(next);
			res.remove(e);
		}
	}
	
	private int checkMatch(String source, String target) {
		int sLen = source.length(), tLen = target.length();
		int res = source.length() - 1;
		while (!source.substring(sLen - res).equals(target.substring(0, res)) && res >= 0) {
			res--;
		}
		return res;
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] keys = {"CGAT", "ATCG", "GCAG", "AGCG", "GAGC", "GATC", "CAGC", "GGAG", "AGCA", "GCGA", "TCGG"};
		List<String> tmp = new ArrayList<>();
		try {
			String pathname = "/Users/tengdai/Documents/CS_Courses/Spring2019/CS466/Project/CS466_Project/rosalind_ba3c_copy_20.txt";
			File filename = new File(pathname);
//				InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line = "";
			line = br.readLine();
			while (line != null) {
				tmp.add(line);
				line = br.readLine();
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
//		String[] keys = tmp.toArray(new String[0]);
		Overlap_Naive solution = new Overlap_Naive();
		long start = System.currentTimeMillis();
		Map res = solution.getEdge(keys);
		String result = solution.getString(res);
		long finish = System.currentTimeMillis();
		long timeElapsed = finish - start;
		System.out.println(result);
		System.out.println("Running takes: " + (timeElapsed * 0.001) + " secondes.");

	}

}
