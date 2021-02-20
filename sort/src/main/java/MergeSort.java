import java.util.Arrays;

public class MergeSort {

  // 归并排序算法, a是数组，n表示数组大小
  public static void mergeSort(int[] a, int n) {
    mergeSortInternally(a, 0, n-1);
  }

  // 递归调用函数
  private static void mergeSortInternally(int[] a, int p, int r) {
    // 递归终止条件
    if (p >= r) return;

    // 取p到r之间的中间位置q,防止（p+r）的和超过int类型最大值
    int q = p + (r - p)/2;
    // 分治递归
    mergeSortInternally(a, p, q);
    mergeSortInternally(a, q+1, r);

    // 将A[p...q]和A[q+1...r]合并为A[p...r]
    merge(a, p, q, r);
  }

  private static void merge(int[] a, int p, int q, int r) {
    int i = p;
    int j = q+1;
    int k = 0; // 初始化变量i, j, k
    int[] tmp = new int[r-p+1]; // 申请一个大小跟a[p...r]一样的临时数组
    while (i<=q && j<=r) {
      if (a[i] <= a[j]) {
        tmp[k++] = a[i++]; // i++等于i:=i+1
      } else {
        tmp[k++] = a[j++];
      }
    }

    // 判断哪个子数组中有剩余的数据
    int start = i;
    int end = q;
    if (j <= r) {
      start = j;
      end = r;
    }

    // 将剩余的数据拷贝到临时数组tmp
    while (start <= end) {
      tmp[k++] = a[start++];
    }

    // 将tmp中的数组拷贝回a[p...r]
    for (i = 0; i <= r-p; ++i) {
      a[p+i] = tmp[i];
    }
  }

  /**
   * 合并(哨兵)
   *
   * @param arr
   * @param p
   * @param q
   * @param r
   */
  private static void mergeBySentry(int[] arr, int p, int q, int r) {
    int[] leftArr = new int[q - p + 2];
    int[] rightArr = new int[r - q + 1];

    for (int i = 0; i <= q - p; i++) {
      leftArr[i] = arr[p + i];
    }
    // 第一个数组添加哨兵（最大值）
    leftArr[q - p + 1] = Integer.MAX_VALUE;

    for (int i = 0; i < r - q; i++) {
      rightArr[i] = arr[q + 1 + i];
    }
    // 第二个数组添加哨兵（最大值）
    rightArr[r-q] = Integer.MAX_VALUE;

    int i = 0;
    int j = 0;
    int k = p;
    while (k <= r) {
      // 当左边数组到达哨兵值时，i不再增加，直到右边数组读取完剩余值，同理右边数组也一样
      if (leftArr[i] <= rightArr[j]) {
        arr[k++] = leftArr[i++];
      } else {
        arr[k++] = rightArr[j++];
      }
    }
  }


  public static int[] sort(int[] sourceArray){
    int[] arr = Arrays.copyOf(sourceArray, sourceArray.length);
    if(arr.length < 2){
      return arr;
    }
    int middle = (int) Math.floor(arr.length / 2);
    int[] left = Arrays.copyOfRange(arr, 0, middle);
    int[] right = Arrays.copyOfRange(arr, middle, arr.length);

    return merge(sort(left), sort(right));
  }


  public static int[] merge(int[] left, int[] right) {
    int[] result = new int[left.length + right.length];
    int i = 0;
    while (left.length > 0 && right.length > 0) {
      if (left[0] <= right[0]) {
        result[i++] = left[0];
        left = Arrays.copyOfRange(left, 1, left.length);
      } else {
        result[i++] = right[0];
        right = Arrays.copyOfRange(right, 1, right.length);
      }
    }

    while (left.length > 0) {
      result[i++] = left[0];
      left = Arrays.copyOfRange(left, 1, left.length);
    }

    while (right.length > 0) {
      result[i++] = right[0];
      right = Arrays.copyOfRange(right, 1, right.length);
    }

    return result;
  }
  public static void main(String[] args) {
    int[] ints = {1, 5, 7, 2, 4, 6, 1, 2, 1};
   /* int[] int1 = {1, 5, 7, 2, 4, 6, 1, 2, 1};
    int[] int2 = {1, 5, 7, 2, 4, 6, 1, 2, 1};*/
/*    int middleIndex = ints.length / 2;
   *//* MergeSort.mergeSort(ints, ints.length);
    System.out.println(ints);*//*
    Arrays.sort(int1, 0, middleIndex);
    Arrays.sort(int2, middleIndex, ints.length);
    System.out.println("1");*/
    /*int[] items = {7,2};
    MergeSort.mergeSort(items, items.length);*/
    MergeSort.sort(ints);

  }

}