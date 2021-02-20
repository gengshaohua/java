import java.lang.reflect.Array;
import java.util.Arrays;

public class Practice {

    public static void maopao(int[] items){

        //循环每遍找到最大的放在最后
        for(int i = 0; i<items.length; i++){
            for(int j = 0; j<items.length - i - 1; j++){
                if(items[j] < items[j+1]){
                    int temp = items[j + 1];
                    items[j + 1] = items[j];
                    items[j] = temp;
                }
            }
        }
    }

    public static void charu(int[] items){
        //依次和前面的有序数组作比较
        for(int i = 0; i<items.length; i++){
            int value = items[i];
            int j = i-1;
            for(; j>=0; j--){
                if(items[j] < value){
                    items[j+1] = value;
                }else{
                    break;
                }
            }
            items[j+1] = value;
        }
    }

    public static void xuanze(int[] items){
        //每次循环找到最小值
        for(int i = 0; i<items.length; i++){
            int middle = i;
            for(int j = i; j<items.length; j++){
                if(items[j] < items[middle])
                    middle = j;
            }
            int temp = items[middle];
            items[middle] = items[i];
            items[i] = temp;
        }
    }

    public static int[] guibing(int[] items){
        if(items.length < 2)
            return items;
        int[] arr = Arrays.copyOf(items, items.length);
        int middle = items.length / 2;
        int[] left = Arrays.copyOfRange(arr, 0, middle);
        int[] right = Arrays.copyOfRange(arr, middle, items.length);

        return merge(guibing(left), guibing(right));
    }

    public static int[] merge(int[] left, int[] right){
        int index = 0;
        int[] result = new int[left.length + right.length];

        while(left.length > 0 && right.length > 0){
            if(left[0] <= result[0]){
                result[index++] = left[0];
                left = Arrays.copyOfRange(left, 1, left.length);
            }else{
                result[index++] = right[0];
                right = Arrays.copyOfRange(right, 1, left.length);
            }
        }

        while(left.length > 0){
            result[index++] = left[0];
            left = Arrays.copyOfRange(left, 1, left.length);
        }

        while(right.length > 0){
            result[index++] = right[0];
            right = Arrays.copyOfRange(right, 1, left.length);
        }
        return result;
    }

    /**
     * 快速排序
     * @param arr 数组
     * @param start 开始位置
     * @param end   结束位置
     */
    public void kuaisu(int[] arr, int start, int end){
        if(start < end){
            int i = start;  //数组 i
            int j = end;    //数组 j
            int x = arr[i]; //基数
            while(i < j){
                while(i < j && arr[j] >= x){
                    j--;
                }
                if(i < j)
                    arr[i++] = arr[j];
                while(i < j && arr[i] >= x){
                    i++;
                }
                if(i < j)
                    arr[j--] = arr[i];
            }
            arr[i] = x;
            kuaisu(arr, start, i - 1); // 递归调用
            kuaisu(arr, i + 1, end);
        }
    }



}
