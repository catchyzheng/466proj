from collections import defaultdict, Counter



class Graph(object):
    def __init__(self, num):
        self.num = num
        self.adj = [[] for _ in range(self.num)]
        self.assemble = ''

    def add_edge(self, u, v):
        self.adj[u].append(v)
        self.adj[v].append(u)

    def add_edge_list(self, l):
        for u, v in l:
            self.adj[u].append(v)
            self.adj[v].append(u)

    def dfs_count(self, u, visited):
        count = 1
        for i in self.adj[u]:
            if i == -1:
                continue
            elif not visited[i]:
                visited[i] = True
                count += self.dfs_count(i, visited)

        return count

    def rmv_edge(self, u, v):
        # print('u, v', u, v)
        idx1 = self.adj[u].index(v)
        self.adj[u][idx1] = -1
        idx2 = self.adj[v].index(u)
        self.adj[v][idx2] = -1
        return idx1, idx2

    def recover_edge(self, u, v, idx1, idx2):
        self.adj[u][idx1] = v
        self.adj[v][idx2] = u

    def find_odd_vertex(self):
        lst = []
        for v in range(self.num):
            if len(self.adj[v]) % 2 == 1:
                lst.append(v)
        return lst

    def find_eulerian(self, v):
        # if v == -1: return

        length = len(self.adj[v])

        next = -1
        for i in self.adj[v]:
            if length == 1:
                # print('%d-%d' % (v, i))
                self.assemble += v_list[i][-1]
                self.adj[v].remove(i)
                self.adj[i].remove(v)
                self.find_eulerian(i)

            else:
                visited = [False for _ in range(self.num)]
                reach1 = self.dfs_count(v, visited)

                visited = [False for _ in range(self.num)]
                idx1, idx2 = self.rmv_edge(v, i)
                reach2 = self.dfs_count(v, visited)
                self.recover_edge(v, i, idx1, idx2)

                if reach1 == reach2:
                    # print('%d-%d' % (v, i))
                    self.assemble += v_list[i][-1]
                    next = i
                    self.adj[v].remove(i)
                    self.adj[i].remove(v)
                    break

        if next != -1:
            self.find_eulerian(next)


v_list = []

if __name__ == '__main__':
    k = 4 # k-mer
    s = 'TTACTGTCACGGCCTAAATC'
    s_list = []

    for i in range(len(s)-k+1):
        s_list.append(s[i:i+k])

    dic = defaultdict(int)
    for short in s_list:
        dic[short[:3]] += 1
        dic[short[1:]] += 1

    # print(Counter(dic))

    lst = list(dic.keys())
    v_list = lst
    # print(v_list)
    num = defaultdict(int)
    for i in range(len(v_list)):
        num[v_list[i]] = i


    g1 = Graph(len(v_list))

    for short in s_list:
        g1.add_edge(num[short[:3]], num[short[1:]])

    g1.assemble = s[:3]
    g1.find_eulerian(num[s[:3]])

    print(s)
    print(g1.assemble)

    # g1 = Graph(10)
    # g1.add_edge_list([(0,1), (0,2), (1,2), (2,3), (3,1)] )
    # g1.find_eulerian(2)


'''
# test:
GGAAAGCCGCTGTGCTAAAG
CTAAAGAACGCTATTTTAGA
CCGAGCTAAATGGAGCTGTA
TTACTGTCACGGCCTAAATC
CTTACGGATGGCTTACCATA
'''
