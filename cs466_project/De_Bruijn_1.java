package cs466_project;

import java.util.*;

public class De_Bruijn_1 {
	private int vertices; // No. of vertices 
	private ArrayList<Integer>[] adj; // adjacency list 
	private String[] mapping;
	private String res;
	De_Bruijn_1(String[] keysDB) 
	{ 
		List<List<Integer>> adj = new ArrayList<>();
		int k = keysDB[0].length();
		int idx = 0;
		HashMap<String, Integer> m = new HashMap<>();
		for (String str : keysDB) {
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
		this.vertices = adj.size(); 
		initGraph(); 
		for (int i = 0; i < adj.size(); i++) {
			List<Integer> l = adj.get(i);
			for (int j = 0; j < l.size(); j++) {
				addEdge(i, l.get(j));
			}
		}
		String[] mapping = new String[m.size()];
		for (String s : m.keySet()) {
			mapping[m.get(s)] = s;
		}
		this.mapping = mapping;
		this.res = this.getEulerTour();
	} 
	
	public String getString() {
		return this.res;
	}
	
	private void initGraph() 
	{ 
		adj = new ArrayList[vertices]; 
		for (int i = 0; i < vertices; i++) 
		{ 
			adj[i] = new ArrayList<>(); 
		} 
	} 
	
	private void addEdge(Integer u, Integer v) 
	{ 
		adj[u].add(v); 
		adj[v].add(u); 
	} 

	private void removeEdge(Integer u, Integer v) 
	{ 
		adj[u].remove(v); 
		adj[v].remove(u); 
	} 
	
	private String getEulerTour() 
	{ 

		Integer u = 0; 
		for (int i = 0; i < vertices; i++) 
		{ 
			if (adj[i].size() % 2 == 1) 
			{ 
				u = i; 
				break; 
			} 
		} 
		List<String> sb = new ArrayList<>();
		printEulerUtil(u, sb); 
		StringBuilder res = new StringBuilder();
		for (int i = 0; i < sb.size(); i += 2) {
			int tmp = sb.get(i).length();
			if (i == 0) {
				res.append(sb.get(i));
				res.append(sb.get(i + 1).substring(tmp - 1, tmp));
			} else {
				res.append(sb.get(i + 1).substring(tmp - 1, tmp));
			}
		}
		return res.toString();
	} 

	private void printEulerUtil(Integer u, List<String> sb) 
	{ 

		for (int i = 0; i < adj[u].size(); i++) 
		{ 
			Integer v = adj[u].get(i); 
			if (isValidNextEdge(u, v)) 
			{ 
				sb.add(mapping[u]);
				sb.add(mapping[v]);
				removeEdge(u, v); 
				printEulerUtil(v, sb); 
			} 
		} 
	} 


	private boolean isValidNextEdge(Integer u, Integer v) 
	{ 

		if (adj[u].size() == 1) { 
			return true; 
		} 

		boolean[] isVisited = new boolean[this.vertices]; 
		int count1 = dfsCount(u, isVisited); 
		removeEdge(u, v); 
		isVisited = new boolean[this.vertices]; 
		int count2 = dfsCount(u, isVisited); 

		addEdge(u, v); 
		return (count1 > count2) ? false : true; 
	} 

	private int dfsCount(Integer v, boolean[] isVisited) 
	{ 
		isVisited[v] = true; 
		int count = 1; 
		for (int adj : adj[v]) 
		{ 
			if (!isVisited[adj]) 
			{ 
				count = count + dfsCount(adj, isVisited); 
			} 
		} 
		return count; 
	} 
	
	public static void main(String[] args) {
		 

		generate_data gd = new generate_data(10, 6);
		String[] keysDB = gd.getKeys("DB");
		String[] keysOG = gd.getKeys("OG");
		String data = gd.getOriginal();
		
		System.out.println(data);
		De_Bruijn_1 g1 = new De_Bruijn_1(keysDB); 
		String res = g1.getString();
		System.out.println(res);
		

	}

}
