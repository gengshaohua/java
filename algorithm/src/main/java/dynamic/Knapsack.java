package dynamic;

public class Knapsack {

    /*
     * @param weight 物品重量集合
     * @param n 物品总个数
     * @param w 背包总重量
     * @return
     */
    public int knapsack(int[] weight, int n, int w){
        boolean[][] states = new boolean[n][w + 1];
        states[0][0] = true;
        if(weight[0] <= w){
            states[0][weight[0]] = true;
        }
        for(int i = 1; i < n; i++){
            //不放入背包，跟上次的背包重量达到一直
            for(int j = 0; j <= w; j++){
                if(states[i-1][j]) states[i][j] = true;
            }
            //放入背包，但是当前背包重量总和 + 当前物品的重量不能大于总重量
            for(int k = 0; k <= w - weight[i]; k++){
                if(states[i-1][k]) states[i][k+weight[i]] = true;
            }
        }
        for (int i = w; i >= 0; i--) { // 输出结果
            if (states[n-1][i]){
                return i;
            }
        }
        return 0;
    }

    /**
     * 一维数组优化
     * @return
     */
    public int knapsack2(int[] weight, int n, int w){
        boolean[] states = new boolean[w + 1];
        states[0] = true;
        if(weight[0] <= w){
            states[weight[0]] = true;
        }
        for(int i = 1; i<n; i++){
            for(int j = w - weight[i]; j>=0; --j){
                if (states[j]) states[j+weight[i]] = true;
            }
        }
        for (int i = w; i >= 0; i--) { // 输出结果
            if (states[i]){
                return i;
            }
        }
        return 0;
    }

    /**
     * 价格最优
     * @param items 商品列表
     * @param value 商品列表-对应价格
     * @param n 商品个数
     * @param w 可容纳的总重量
     * @return
     */
    public int knapsack3(int[] items, int[] value, int n, int w){
        int[][] states = new int[n][w + 1];
        for (int i = 0; i < n; ++i) { // 初始化states
            for (int j = 0; j < w+1; ++j) {
                states[i][j] = -1;
            }
        }
        states[0][0] = 0;
        if(items[0] <= w){
            states[0][items[0]] = value[0];
        }
        for(int i = 1; i<n; i++){
            for(int j = 0; j <= w; j++){
                if (states[i-1][j] >= 0) states[i][j] = states[i-1][j];
            }
            for(int k = 0; k<= w - items[i]; k++){
                if(states[i-1][k] >= 0){
                    //如果出现多种情况的重量和 例如不同重量2-4-3为9 3-6也为9 就比较哪个高
                    int v = states[i-1][k] + value[i];
                    if(v > states[i][k + items[i]]){
                        states[i][k + items[i]] = v;
                    }
                }
            }
        }
        int maxValue = -1;
        for (int j = 0; j <= w; j++) {
            if(states[n-1][j] > maxValue){
                maxValue = states[n-1][j];
            }
        }
        return maxValue;
    }

    /**
     * 双11促销
     * @param items 商品价格列表
     * @param n 商品数量
     * @param w 优惠力度
     */
    public static void double11advance(int[] items, int n, int w) {
        boolean[][] states = new boolean[n][3*w+1];
        states[0][0] = true;
        if(items[0] <= w){
            states[0][items[0]] = true;
        }
        for(int i = 1; i<n; i++){
            for(int j = 0; j<w*3; j++){ //不购买第i个商品
                if (states[i-1][j]) states[i][j] = states[i-1][j];
            }
            for(int j = 0; j<w*3 - items[i]; j++){{ //购买第i个商品
                if (states[i-1][j]) states[i][j+items[i]] = true;
            }}
        }
        int j;
        for(j = w; j<3*w; j++){
            if(states[n-1][j]) break;   //大于等于w中的最小值
        }
        if(j == 3*w) return;
        for(int i = n-1; i>=0; i--){
            if(j-items[i] >=0 && states[i-1][j - items[i]]){
                System.out.print(items[i] + " "); // 购买这个商品
                j = j - items[i];
            }
        }
        if (j != 0) System.out.print(items[0]);
    }

}
