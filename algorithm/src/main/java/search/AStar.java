package search;

import java.util.LinkedList;

//最短路径规划
public class AStar {
    private LinkedList<Edge> adj[]; // 邻接表
    private int v; // 顶点个数

    // Graph类的成员变量，在构造函数中初始化
    Vertex[] vertexes = new Vertex[this.v];

    // 新增一个方法，添加顶点的坐标
    public void addVetex(int id, int x, int y) { vertexes[id] = new Vertex(id, x, y);}

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
     * 优先级队列构建的方式不同。A* 算法是根据 f 值（也就是刚刚讲到的 f(i)=g(i)+h(i)）来构建优先级队列，
     * 而 Dijkstra 算法是根据 dist 值（也就是刚刚讲到的 g(i)）来构建优先级队列；A* 算法在更新顶点 dist 值的时候，会同步更新 f 值；
     * 循环结束的条件也不一样。Dijkstra 算法是在终点出队列的时候才结束，A* 算法是一旦遍历到终点就结束。
     *
     * @param start
     * @param end
     */
    public void astar(int start, int end) {
        int[] predecessor = new int[this.v];    //还原最短路径
        PriorityQueue queue = new PriorityQueue(this.v);    //小顶堆
        boolean[] inqueue = new boolean[this.v];            //表机该编号是否如果队
        vertexes[start].dist = 0;   //起始位置开始
        inqueue[start] = true;
        while(!queue.isEmpty()){
            Vertex minVertex = queue.poll();    //取出堆顶元素并删除
            if(minVertex.id == end) return;     //最短路径产生了
            for(int i = 0; i<adj[minVertex.id].size(); i++){
                Edge e = adj[minVertex.id].get(i);  //取出关于这条边的每个临界点
                Vertex nextVertex = vertexes[e.tid];    //minVertex.id -->nextVertex.id
                if(minVertex.dist + e.w < nextVertex.dist){ //当前计算下来的这条路径目前是最短
                    //替换信息，如果队列存在这个路径信息就更新 如果不存在就新增
                    nextVertex.dist = minVertex.dist + e.w;
                    nextVertex.f = nextVertex.dist + hManhattan(nextVertex, vertexes[end]);
                    predecessor[nextVertex.id] = minVertex.id;
                    if(inqueue[nextVertex.id]){
                        queue.update(nextVertex);
                    }else{
                        queue.add(nextVertex);
                        inqueue[nextVertex.id] = true;
                    }
                }
                if (nextVertex.id == end) { // 只要到达t就可以结束while了
                    queue.clear(); // 清空queue，才能推出while循环
                    break;
                }
            }
        }
        // 输出最短路径
        System.out.print(start);
        print(start, end, predecessor);
    }


    int hManhattan(Vertex v1, Vertex v2) { // Vertex表示顶点，后面有定义
        return Math.abs(v1.x - v2.x) + Math.abs(v1.y - v2.y);
    }

    private void print(int s, int t, int[] predecessor) {
        if (s == t) return;
        print(s, predecessor[t], predecessor);
        System.out.print("->" + t);
    }

    class PriorityQueue {//构建小顶堆
        Vertex[] nodes;
        private int count;//队列个数
        private int n;

        public PriorityQueue(int v) {
            nodes = new Vertex[v + 1];//小顶堆，数组从小标1开始，好计算
            this.count = 0;//初始0个元素
            this.n = v;
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

        public void clear(){
            nodes = new Vertex[n];
            count = 0;
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
        public int f; // 新增：f(i)=g(i)+h(i)
        public int x, y; // 新增：顶点在地图中的坐标（x, y）

        public Vertex(int id, int x, int y) {
            this.id = id;
            this.x = x;
            this.y = y;
            this.f = Integer.MAX_VALUE;
            this.dist = Integer.MAX_VALUE;
        }
    }
}
