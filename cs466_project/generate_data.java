package cs466_project;

import java.util.*;

public class generate_data {
	private char[] gene = {'A', 'T', 'C', 'G'};
	private int length = 0, k = 0;
	private String data = "";
	private String[] keysDB;
	private String[] keysOG;
	public generate_data(int length, int k) {
		this.length = length;
		this.k = k;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			int idx = (int)(Math.random() * 4);
			sb.append(gene[idx]);
		}
		this.data = sb.toString(); //"ATTTGGGCATTTGGGC";)
		this.keysDB = this.generateDB();
		this.keysOG = this.generateOG();
	}
	
	private String[] generateDB() {
		List<String> resl = new ArrayList<>();
		for (int i = 0; i <= data.length() - k; i++) {
			String cand = data.substring(i, i + k);
			resl.add(cand);
		}
		String[] res = new String[resl.size()];
		int i = 0;
		for (String str : resl) {
			res[i++] = str;
		}
		return res;
	}
	
	private String[] generateOG() {
		Set<String> s = new HashSet<>();
		for (int i = 0; i <= data.length() - k; i++) {
			String cand = data.substring(i, i + k);
			if (!s.contains(cand)) {
				s.add(cand);
			}
		}
		String[] res = new String[s.size()];
		int i = 0;
		for (String str : s) {
			res[i++] = str;
		}
		return res;
	}
	
	public String[] getKeys(String method) {
		if (method.equals("DB"))
			return this.keysDB;
		else
			return this.keysOG;
	}
	
	public String getOriginal() {
		return this.data;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		generate_data res = new generate_data(20, 4, "DB");
//		String[] keys = res.getKeys();
//		String data = res.getOriginal();
//		System.out.println(data);
//		System.out.println(keys.length);
	}

}
