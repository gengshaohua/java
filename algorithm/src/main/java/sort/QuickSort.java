package sort;

import java.util.Arrays;

public class QuickSort {

  // 快速排序，a是数组，n表示数组的大小
  public static void quickSort(int[] a, int n) {
    quickSortInternally(a, 0, n-1);
  }

  // 快速排序递归函数，p,r为下标
  private static void quickSortInternally(int[] a, int p, int r) {
    if (p >= r) return;

    int q = partition(a, p, r); // 获取分区点
    quickSortInternally(a, p, q-1);
    quickSortInternally(a, q+1, r);
  }

  private static int partition(int[] a, int p, int r) {
    int pivot = a[r];
    int i = p;
    for(int j = p; j < r; ++j) {
      if (a[j] < pivot) {
        if (i == j) {
          ++i;
        } else {
          int tmp = a[i];
          a[i++] = a[j];
          a[j] = tmp;
        }
      }
    }

    int tmp = a[i];
    a[i] = a[r];
    a[r] = tmp;

    System.out.println("i=" + i);
    return i;
  }

  //快速排序
  static void quick_sort(int s[], int l, int r)
  {
    if (l < r)
    {
      //Swap(s[l], s[(l + r) / 2]); //将中间的这个数和第一个数交换 参见注1
      int i = l, j = r, x = s[l];
      while (i < j)
      {
        while(i < j && s[j] >= x) // 从右向左找第一个小于x的数
          j--;
        if(i < j)
          s[i++] = s[j];

        while(i < j && s[i] < x) // 从左向右找第一个大于等于x的数
          i++;
        if(i < j)
          s[j--] = s[i];
      }
      s[i] = x;
      quick_sort(s, l, i - 1); // 递归调用
      quick_sort(s, i + 1, r);
    }
  }

  public static void main(String[] args) {
    int[] ints = new int[]{5, 9, 1, 9, 5, 3, 7, 6, 1};
    /*quickSort(ints, ints.length);*/
    quick_sort(ints, 0, ints.length-1);
    System.out.println(Arrays.toString(ints));
  }
}