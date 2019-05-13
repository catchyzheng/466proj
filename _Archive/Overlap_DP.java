package cs466_project;

import java.io.*;
import java.util.*;

public class Overlap_DP {
	public String getString(String[] A) {
		int n = A.length;
		int[][] g = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				g[i][j] = A[j].length();
				for (int k = 1; k <= Math.min(A[i].length(), A[j].length()); k++) {
					if (A[i].substring(A[i].length() - k).equals(A[j].substring(0, k))) {
						g[i][j] = A[j].length() - k;
					}
				}
			}
		}
		
		int[][] dp = new int[1 << n][n];
		int[][] parent = new int[1 << n][n];
		for (int i = 0; i < dp.length; i++)
			for (int j = 0; j < dp[0].length; j++)
				dp[i][j] = Integer.MAX_VALUE / 2;
		for (int i = 0; i < parent.length; i++)
			for (int j = 0; j < parent[0].length; j++)
				parent[i][j] = -1;
		
		for (int i = 0; i < n; i++)
			dp[1 << i][i] = A[i].length();
		
		for (int s = 1; s < (1 << n); s++) {
			for (int j = 0; j < n; j++) {
				if ((s & (1 << j)) == 0 ) continue;
				int ps = s & ~(1 << j);
				for (int i = 0; i < n; i++) {
					if (dp[ps][i] + g[i][j] < dp[s][j]) {
						dp[s][j] = dp[ps][i] + g[i][j];
						parent[s][j] = i;
					}
				}
			}
		}
		
		int j = getMin(dp);
		int s = (1 << n) - 1;
		String res = "";
		while (s > 0) {
			int i = parent[s][j];
			if (i < 0) res = A[j] + res;
			else res = A[j].substring(A[j].length() - g[i][j]) + res;
			s &= ~(1 << j);
			j = i;
		}
		return res;
	}
	
	private int getMin(int[][] matrix) {
		int res = Integer.MAX_VALUE, idx = -1;
		for (int i = 0; i < matrix[0].length; i++) {
			if (res > matrix[matrix.length - 1][i]) {
				res = matrix[matrix.length - 1][i];
				idx = i;
			}
		}
		return idx;
	}

	public static void main(String[] args) {
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

		Overlap_DP solution = new Overlap_DP();
		long start = System.currentTimeMillis();
		String res = solution.getString(keys);
		long finish = System.currentTimeMillis();
		long timeElapsed = finish - start;
		System.out.println(res);
		System.out.println("Running takes: " + (timeElapsed * 0.001) + " secondes.");


	}

}
