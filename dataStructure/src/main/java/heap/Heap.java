package heap;

public class Heap {

    private int[] a;    //数组，从下标1开始存储数据
    private int n;      //堆可以存储的最大数据个数
    private int count;  //堆中已经存储的数据个数

    public Heap(int capacity) { a = new int[capacity + 1]; n = capacity; count = 0; }

    public void insert(int data){
        if(count >= n) return;
        ++count;
        a[count] = data;
        int i = count;
        while(i/2>0 && a[i]>a[i/2]){
            swap(a, i, i/2);
            i = i/2;
        }
    }

    public void removeMax(){
        if(count == 0) return;
        a[1] = a[count];
        --count;
        heapify(a, count, 1);
    }

    // n表示数据的个数，数组a中的数据从下标1到n的位置。
    public static void sort(int[] a, int n) {
        buildHeap(a, n);
        int k = n;
        while (k > 1) {
            swap(a, 1, k);
            --k;
            heapify(a, k, 1);
        }
    }

    private static void buildHeap(int[] a, int n) {
        for (int i = n/2; i >= 1; --i) {
            heapify(a, n, i);
        }
    }

    private static void heapify(int[] a, int n, int i){
        while(true){
            int maxPos = i;
            if(i*2 <=n && a[i] < a[i*2]) maxPos = i*2;
            if(i*2+1 <= n && a[maxPos] < a[i*2+1]) maxPos = i*2+1;
            if(maxPos == i) break;
            swap(a, i, maxPos);
            i = maxPos;
        }
    }

    public static void swap(int[] a, int tempA, int tempB){
        int temp = a[tempA];
        a[tempA] = a[tempB];
        a[tempB] = temp;
    }

}
