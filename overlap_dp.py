from collections import defaultdict
import math
import numpy as np
import time

def shortest_superstring(ss):
    n = len(ss)
    g = [[0 for _ in range(n)] for __ in range(n)]
    for i in range(n):
        for j in range(n):
            g[i][j] = len(ss[j])
            for k in range(min(len(ss[i]), len(ss[j]))):
                if ss[i][len(ss[i])-k:] == ss[j][:k]:
                    g[i][j] = len(ss[j]) - k

    dp = [[float('inf') for _ in range(n)] for __ in range(1<<n)]
    parent = [[-1 for _ in range(n)] for __ in range(1<<n)]

    for i in range(n):
        dp[1<<i][i] = len(ss[i])

    for s in range(1, 1<<n):
        for j in range(n):
            if not s & (1<<j): continue
            ps = s & ~(1<<j) # s - (1<<j)
            for i in range(n):
                if dp[ps][i] + g[i][j] < dp[s][j]:
                    dp[s][j] = dp[ps][i] + g[i][j]
                    parent[s][j] = i

    j = np.argmin([dp[(1<<n)-1][j] for j in range(n)])
    # j = it - dp[(1<<n)-1][0]
    s = (1<<n)-1
    ans = ''
    while s>0:
        i = parent[s][j]
        if i < 0:
            ans = ss[j] + ans
        else:
            ans = ss[j][(len(ss[j]) - g[i][j]):] + ans
        s -= (1<<j)
        j = i

    return ans


if __name__ == '__main__':
    file = open('rosalind_ba3c_copy_20.txt', 'r')
    ss = []
    for row in file:
        ss.append(row.split('\n')[0])
    # ss = ["CGAT", "ATCG", "GCAG", "AGCG", "GAGC", "GATC", "CAGC", "GGAG", "AGCA", "GCGA", "TCGG"]
    start = time.time()
    ans = shortest_superstring(ss)
    end = time.time()
    print(ans, end-start)
