from collections import defaultdict


class Graph(object):
    def __init__(self, num):
        self.num = num
        self.adj = [[] for _ in range(self.num)]

    def add_edge(self, u, v):
        self.adj[u].append(v)

    def add_edge_list(self, l):
        for u, v in l:
            self.adj[u].append(v)

    def circuit(self):
        edge_cnt = defaultdict()
        for i in range(len(self.adj)):
            edge_cnt[i] = len(self.adj[i])

        cur_path = []
        circuit = []

        cur_path.append(0)
        cur_v = 0
        while len(cur_path) != 0:
            if edge_cnt[cur_v] > 0:
                cur_path.append(cur_v)

                next_v = self.adj[cur_v][-1]
                print('next v', next_v)
                edge_cnt[cur_v] -= 1
                self.adj[cur_v].pop()

                cur_v = next_v

            else:
                circuit.append(cur_v)

                cur_v = cur_path[0]
                cur_path.pop()

        print(circuit)
        for i in range(len(circuit)-1, -1, -1):
            print(circuit[i], end='')
            if i > 0:
                print(' > ', end='')


if __name__ == '__main__':
    g1 = Graph(3)
    g1.add_edge_list([(0,1), (1,2), (2,0)])
    g1.circuit()