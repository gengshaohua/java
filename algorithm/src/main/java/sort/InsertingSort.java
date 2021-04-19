package sort;

/**
 * 快速排序
 */
public class InsertingSort {

    public int[] sort(int[] items){
        if(items.length <= 1)
            return items;

        for(int i = 0; i<items.length; i++){
            int value = items[i];
            int j = i - 1;
            for(; j>=0; j--){
                if(items[j] > value){
                    items[j + 1] = items[j];
                }else{
                    break;
                }
            }
            items[j+1] = value;
        }
        return items;
    }


    public static void main(String[] args) {
        InsertingSort object = new InsertingSort();
        int[] items = {5,6,1,2,6};
        int[] sort = object.sort(items);
        System.out.println(sort);
    }
}
