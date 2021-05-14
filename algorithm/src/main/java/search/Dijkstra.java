package search;

import java.util.LinkedList;

//最短路径
public class Dijkstra {

    private LinkedList<Edge> adj[]; // 邻接表
    private int v; // 顶点个数

    public class Graph { // 有向有权图的邻接表表示
        private LinkedList<Edge> adj[]; // 邻接表
        private int v; // 顶点个数

        public Graph(int v) {
            this.v = v;
            this.adj = new LinkedList[v];
            for (int i = 0; i < v; ++i) {
                this.adj[i] = new LinkedList<>();
            }
        }

        public void addEdge(int s, int t, int w) { // 添加一条边
            this.adj[s].add(new Edge(s, t, w));
        }
    }

    /**
     * 最短路径查询
     *
     * @param start
     * @param end
     */
    public void dijkstra(int start, int end) {
        int[] predecessor = new int[this.v];    //还原最短路径
        Vertex[] vertices = new Vertex[this.v]; //记录每个路径每个路径的最短距离
        for(int i = 0; i<this.v; i++){
            vertices[i] = new Vertex(i, Integer.MAX_VALUE);
        }
        PriorityQueue queue = new PriorityQueue(this.v);    //小顶堆
        boolean[] inqueue = new boolean[this.v];            //表机该编号是否如果队
        vertices[start].dist = 0;   //起始位置开始
        inqueue[start] = true;
        while(!queue.isEmpty()){
            Vertex minVertex = queue.poll();    //取出堆顶元素并删除
            if(minVertex.id == end) return;     //最短路径产生了
            for(int i = 0; i<adj[minVertex.id].size(); i++){
                Edge e = adj[minVertex.id].get(i);  //取出关于这条边的每个临界点
                Vertex nextVertex = vertices[e.tid];    //minVertex.id -->nextVertex.id
                if(minVertex.dist + e.w < nextVertex.dist){ //当前计算下来的这条路径目前是最短
                    //替换信息，如果队列存在这个路径信息就更新 如果不存在就新增
                    nextVertex.dist = minVertex.dist + e.w;
                    predecessor[nextVertex.id] = minVertex.id;
                    if(inqueue[nextVertex.id]){
                        queue.update(nextVertex);
                    }else{
                        queue.add(nextVertex);
                        inqueue[nextVertex.id] = true;
                    }
                }
            }
        }
        // 输出最短路径
        System.out.print(start);
        print(start, end, predecessor);
    }

    private void print(int s, int t, int[] predecessor) {
        if (s == t) return;
        print(s, predecessor[t], predecessor);
        System.out.print("->" + t);
    }

    class PriorityQueue {//构建小顶堆
        Vertex[] nodes;
        private int count;//队列个数

        public PriorityQueue(int v) {
            nodes = new Vertex[v + 1];//小顶堆，数组从小标1开始，好计算
            this.count = 0;//初始0个元素
        }

        public Vertex poll() {
            Vertex v = nodes[1];//返回堆顶原始
            nodes[1] = nodes[count];//将最后一个元素添加到堆顶，自上而下堆化
            --count;
            heapifyUpToDown(1);//堆顶从上而下堆化
            return v;
        }

        public void add(Vertex vertex) {
            nodes[++count] = vertex;
            vertex.id = count;
            heapifyDownToUp(count);//从下往上堆化
        }

        public void update(Vertex vertex) {
//查找，并更新
            nodes[vertex.id].dist = vertex.dist;
            heapifyDownToUp(vertex.id);//从下往上堆化
        }

        public boolean isEmpty() {
            return this.count == 0;
        }

        //自上而下堆化
        private void heapifyUpToDown(int i) {
            while (i <= count) {
                int maxPos = i;
                if ((i * 2) <= count && nodes[maxPos].dist > nodes[i * 2].dist) maxPos = 2 * i;
                else if ((i * 2 + 1) <= count && nodes[maxPos].dist > nodes[i * 2 + 1].dist) maxPos = 2 * i + 1;
                else break;
                swap(i, maxPos);//交换
                i = maxPos;
            }
        }

        //从下往上堆化
        private void heapifyDownToUp(int i) {
            while (i / 2 > 0 && nodes[i].dist < nodes[i / 2].dist) {
                swap(i, i / 2);//交换
                i = i / 2;
            }
        }

        /**
         * 数据交换
         *
         * @param i
         * @param maxPos
         */
        private void swap(int i, int maxPos) {
            nodes[i].id = maxPos;//下标交换记录
            nodes[maxPos].id = i;

            Vertex tmp = nodes[i];
            nodes[i] = nodes[maxPos];
            nodes[maxPos] = tmp;
        }
    }

    public class Edge {
        public int sid; // 边的起始顶点编号
        public int tid; // 边的终止顶点编号
        public int w; // 权重

        public Edge(int sid, int tid, int w) {
            this.sid = sid;
            this.tid = tid;
            this.w = w;
        }
    }

    // 下面这个类是为了dijkstra实现用的
    public class Vertex {
        public int id; // 顶点编号ID
        public int dist; // 从起始顶点到这个顶点的距离

        public Vertex(int id, int dist) {
            this.id = id;
            this.dist = dist;
        }
    }
}

