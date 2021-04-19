package sort;

/**
 * 冒泡排序
 */
public class BubblingSort {

    public static int[] sort(int[] items){
        for(int i = 0; i<items.length; i++){
            for(int j = 0; j<items.length - i - 1; j++){
                if(items[j+1] < items[j]){
                    int temp = items[j+1];
                    items[j+1] = items[j];
                    items[j] = temp;
                }
            }
        }
        return items;
    }

    public static void main(String[] args) {
        BubblingSort.sort(new int[]{1,2,3,45});
    }
}
