import time

class OG_naive:
    def __init__(self):
        self.res_max = -1
        self.res_start = ''
        self.res_edges = []

    class Edge:
        def __init__(self, target, weight):
            self.target = target
            self.weight = weight
        def __str__(self):
            return str(self.target) + '(' + str(self.weight) + ')'



    def get_edge(self, keys):
        '''
        :param keys: input string list
        :return: dictionary, adjacent list of each input string
        '''
        res_dict = {}
        for i in range(len(keys)):
            source = keys[i]
            res_dict[source] = []
            for j in range(len(keys)):
                if i == j:
                    continue
                target = keys[j]
                weight = self.check_match(source, target)
                if (weight >= 2):
                    res_dict[source].append(self.Edge(target, weight))

        return res_dict

    def check_match(self, source, target):
        '''
        :param source: source string
        :param target: target string
        :return: overlap length
        '''
        s_len = len(source)
        res = s_len - 1
        while (res >= 0 and source[s_len - res:] != target[:res]):
            res -= 1
        return res

    def get_string(self, edges):
        '''
        :param edges: dictionary, adjacent list of each input string
        :return: shortest superstring contains all input string
        '''
        all_dict = {}
        for s in edges.keys():
            visited = set()
            res = []
            visited.add(s)
            self.dfs(s, visited, edges, res, s)
            all_dict[s] = res
        sb = self.res_start
        for e in self.res_edges:
            sb += e.target[e.weight:]
        return sb


    def dfs(self, s, visited, edges, res, first):
        '''
        :param s: current string
        :param visited: all visited string
        :param edges: adjacent edges map
        :param res: path list
        :param first: start string
        :return: nothing
        '''
        if (len(visited) == len(edges)):
            cnt = 0
            for e in res:
                cnt += e.weight
            if cnt > self.res_max:
                self.res_start = first
                self.res_edges = res.copy()
                self.res_max = cnt
            return

        edge = edges[s]
        for e in edge:
            if e.target in visited:
                continue
            res.append(e)
            next = e.target
            visited.add(next)
            self.dfs(next, visited, edges, res, first)
            visited.remove(next)
            res.remove(e)

# keys = ["CGAT", "ATCG", "GCAG", "AGCG", "GAGC", "GATC", "CAGC", "GGAG", "AGCA", "GCGA", "TCGG"]

file = open('rosalind_ba3c.txt', 'r')

keys = []

for row in file:

    keys.append(row.rstrip('\n'))

OG = OG_naive()
start = time.time()
edges = OG.get_edge(keys)
result = OG.get_string(edges)
end = time.time()
print('time', end - start) # sec: 119
print(result)
