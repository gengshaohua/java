package graph;

import java.util.LinkedList;
import java.util.Queue;

public class Graph {

    private int v;  //当前顶点数
    private LinkedList<Integer> adj[];

    public Graph(int v){
        this.v = v;
        adj = new LinkedList[v];
        for(int i = 0; i<v; i++){
            adj[i] = new LinkedList<>();
        }
    }

    public void add(int s, int j){
        adj[s].add(j);
        adj[j].add(s);
    }

    /**
     * 广度优先搜索
     * @param s 起始位置
     * @param t 终止顶点
     */
    public void bfs(int s, int t){
        if(s == t) return;
        boolean[] invited = new boolean[v];
        invited[s] = true;
        Queue<Integer> queue = new LinkedList<>();
        queue.add(s);
        int[] prev = new int[v];
        for(int i = 0; i<v; i++){
            prev[i] = -1;
        }
        while(!queue.isEmpty()){
            int w = queue.poll();   //弹出队列中的第一个元素 获取相邻节点
            for(int i = 0; i<adj[w].size(); i++){
                int q = adj[w].get(i);
                if(!invited[q]){
                    prev[q] = w;
                    if(q == t) {
                        print(prev, s, t);
                        return;
                    }
                    invited[q] = true;
                    queue.add(q);
                }
            }
        }
    }

    boolean found = false; // 全局变量或者类成员变量

    public void dfs(int s, int t){
        if(s == t) return;
        boolean[] visited = new boolean[v];
        int[] prev = new int[v];
        for(int i = 0; i<v; i++){
            prev[i] = -1;
        }
        recurDfs(s, t, visited, prev);
        print(prev, s, t);
    }

    private void recurDfs(int w, int t, boolean[] visited, int[] prev) {
        if(found) return;
        visited[w] = true;
        if(w == t) {
            found = true;
            return;
        }
        for(int i = 0; i<adj[w].size(); i++){
            int q = adj[w].get(i);
            if(!visited[q]){
                prev[q] = w;
                recurDfs(q, t, visited, prev);
            }
        }
    }

    public void print(int[] prev, int s, int t){
        if (prev[t] != -1 && t != s) {
            print(prev, s, prev[t]);
        }
        System.out.print(t + " ");
    }

}
