/**
 * 选择排序
 */
public class ChooseSort {

    public static int[] sort(int[] items){
        if(items.length <= 1)
            return items;

        for(int i = 0; i<items.length; i++){
            int minIndex = i;
            for(int j = i; j<items.length; j++){
                if(items[j] < items[minIndex])
                    minIndex = j;
            }
            int temp = items[i];
            items[i] = items[minIndex];
            items[minIndex] = temp;
        }
        return items;
    }

}
