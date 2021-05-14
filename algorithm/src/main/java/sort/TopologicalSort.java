package sort;

import java.util.LinkedList;

public class TopologicalSort {

    private int v; // 顶点的个数
    private LinkedList<Integer> adj[]; // 邻接表

    public TopologicalSort(int v) {
        this.v = v;
        adj = new LinkedList[v];
        for (int i=0; i<v; ++i) {
            adj[i] = new LinkedList<>();
        }
    }

    public void addEdge(int s, int t) { // s先于t，边s->t
        adj[s].add(t);
    }

    public void byKahn(){
        int[] inDegree = new int[v];    //统计每个顶点的入度
        for(int i = 0; i<v; ++i){
            for(int j = 0; j < adj[i].size(); j++){
                int w = adj[i].get(j);
                inDegree[w]++;
            }
        }
        LinkedList<Integer> queue = new LinkedList<>();
        for(int i = 0; i<v; i++){
            if(inDegree[i] == 0) queue.add(i);
        }
        while (!queue.isEmpty()){
            int i = queue.remove();
            System.out.print("->" + i);
            for(int j = 0; j<adj[i].size(); j++){
                int k = adj[i].get(j);
                inDegree[k]--;
                if (inDegree[k] == 0) queue.add(k);
            }
        }

    }

    public void byDFS(){
        LinkedList<Integer> inverseAdj[] = new LinkedList[v];
        for(int i = 0; i<v; i++){
            inverseAdj[i] = new LinkedList<>();
        }
        for(int i = 0; i<v; i++){
            for(int j = 0; j<adj[i].size(); j++){
                int w = adj[i].get(j);
                inverseAdj[w].add(i);
            }
        }
        boolean[] visited = new boolean[v];
        for(int i = 0; i<v; ++i){
            if(!visited[i]){
                visited[i] = true;
                dfs(i, inverseAdj, visited);
            }
        }
    }

    public void dfs(int vertex, LinkedList<Integer> inverseAdj[], boolean[] visited){
        for(int i = 0; i<inverseAdj[vertex].size(); ++i){
            int w = inverseAdj[vertex].get(i);
            if(visited[w]) continue;
            visited[w] = true;
            dfs(w, inverseAdj, visited);
        }
        System.out.print("->" + vertex);
    }

}