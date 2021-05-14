package back;

//0-1背包
public class Knapsack {

    public int maxW = Integer.MAX_VALUE;    ////存储背包中物品总重量的最大值

    /**
     * @param i 当前物品下标
     * @param cw 背包总重量和
     * @param items 物品列表
     * @param n 物品个数
     * @param w 背包重量
     * 假设背包可承受重量100，物品个数10，物品重量存储在数组a中，那可以这样调用函数：f(0, 0, a, 10, 100)
     */
    public void f(int i, int cw, int[] items, int n, int w){
        if(cw == w || i == n){  //cw==w表示装满了;i==n表示已经考察完所有的物品
            if (cw > maxW) maxW = cw;
            return;
        }
        f(i+1, cw, items, n, w);    //items是从小到大排序，然后依次先看后面的物品能否装入背包，再从小的开始拿 算是后退操作
        if (cw + items[i] <= w) {  // 已经超过可以背包承受的重量的时候，就不要再装了
            f(i+1,cw + items[i], items, n, w);
        }
    }

}
