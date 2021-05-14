package dynamic;

public class ShortestPath {


    //求出最短路径 --- 回溯算法
    private int minDist = Integer.MAX_VALUE;

    public void minDistBT(int i, int j, int[][] w, int n, int dist){
        if(i == n && j == n){
            if(dist < minDist) minDist = dist;
            return;
        }
        if(i < n){
            minDistBT(i+1, j, w, n, dist + w[i][j]);
        }
        if(j < n){
            minDistBT(i, j+1, w, n, dist + w[i][j]);
        }
    }

    //动态规划
    public int minDistDP(int[][] matrix, int n){
        int[][] states = new int[n][n];
        int sum = 0;
        for(int j = 0; j<n; j++){
            sum += matrix[0][j];
            states[0][j] = sum;
        }
        sum = 0;
        for(int i = 0; i<n; i++){
            sum += matrix[i][0];
            states[i][0] = sum;
        }
        for(int i = 1; i<n; i++){
            for(int j = 1; j<n; j++){
                states[i][j] = Math.min(states[i][j-1],states[i-1][j]);
            }
        }
        return states[n-1][n-1];
    }
}
