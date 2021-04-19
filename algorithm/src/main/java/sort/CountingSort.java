package sort;

/**
 * 计数排序
 */
public class CountingSort {

    public void sort(int[] a, int n){
        if(n <= 1)
            return;
        int max = a[0];
        for(int i = 1; i<n; ++i){
            if(max<a[i]){
                max = a[i];
            }
        }
        int[] c = new int[max + 1];
        //将每个元素放入c中
        for(int i = 0; i<n; i++){
            c[a[i]]++;
        }
        //依次累加
        for(int i = 0; i<max + 1; ++i){
            c[i] = c[i] - 1 + c[i];
        }

        //临时数组r，存储排序之后的结果
        int[] r = new int[n];
        for(int i = n-1; i>=0; --i){
            int index = c[a[i]]-1;
            r[index] = a[i];
            c[a[i]]-- ;
        }

        for(int i = 0; i<n; ++i){
            a[i] = r[i];
        }
    }

}
