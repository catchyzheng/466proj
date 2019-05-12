package cs466_project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class De_Bruijn_2 {
	private String res;
	
	public De_Bruijn_2(String[] A) {
		this.res = printCircuit(A);
	}
	
	public String getString() {
		return this.res;
	}
	
	private String printCircuit(String[] keys) {
		List<List<Integer>> adj = new ArrayList<>();
		int k = keys[0].length();
		int idx = 0;
		HashMap<String, Integer> m = new HashMap<>();
		for (String str : keys) {
			String start = str.substring(0, k - 1);
			String end = str.substring(1);
			if (!m.containsKey(start)) {
				m.put(start, idx++);
			}
			if (!m.containsKey(end)) {
				m.put(end, idx++);
			}
			while (adj.size() < idx) {
				adj.add(new ArrayList<>());
			}
			adj.get(m.get(start)).add(m.get(end));
		}
		String[] mapping = new String[m.size()];
		for (String s : m.keySet()) {
			mapping[m.get(s)] = s;
		}
		
		Map<Integer, Integer> edgeCount = new HashMap<>();
		for (int i = 0; i < adj.size(); i++) {
			edgeCount.put(i, adj.get(i).size());
		}
		if (adj.size() == 0) return "";
		Stack<Integer> currPath = new Stack<>();
		List<Integer> circuit = new ArrayList<>();
		int[] indegree = new int[m.size()];
		int[] outdegree = new int[m.size()];
		for (int i = 0; i < adj.size(); i++) {
			List<Integer> l = adj.get(i);
			outdegree[i] += l.size();
			for (int j = 0; j < l.size(); j++) {
				indegree[l.get(j)]++;
			}
		}
		int cnt = 0, startIdx = -1;
		for (int i = 0; i < indegree.length; i++) {
			if (outdegree[i] - indegree[i] > 1) return "";
			if (Math.abs(outdegree[i] - indegree[i]) == 1) {
				cnt++;
				if (outdegree[i] > indegree[i])
					startIdx = i;
			}
		}
		if (cnt != 2 && cnt != 0) return "";
		int currV = 0;
		if (startIdx != -1)
			currV = startIdx;
		currPath.push(currV);
		
		while (!currPath.isEmpty()) {
			if (edgeCount.containsKey(currV) && edgeCount.get(currV) != 0) {
				currPath.push(currV);
				int tmpSize = adj.get(currV).size();
				int nextV = adj.get(currV).get(tmpSize - 1);
				edgeCount.put(currV, edgeCount.get(currV) - 1);
				adj.get(currV).remove(tmpSize - 1);
				currV = nextV;
			}
			else {
				circuit.add(currV);
				currV = currPath.pop();
			}
		}

		StringBuilder sb = new StringBuilder();
		for (int i = circuit.size() - 1; i >= 0; --i) {
			if (i == circuit.size() - 1) {
				sb.append(mapping[circuit.get(i)]);
				continue;
			}
			
			sb.append(mapping[circuit.get(i)].substring(k - 2, k - 1));
		}
		return sb.toString();
	}
	public static void main(String[] args) {
		generate_data gd = new generate_data(20, 6);
		String[] keysDB = gd.getKeys("DB");
		String[] keysOG = gd.getKeys("OG");
		String data = gd.getOriginal();
		
		De_Bruijn_2 solution = new De_Bruijn_2(keysDB);
		
		System.out.println(data);
		System.out.println(solution.getString());


	}

}
