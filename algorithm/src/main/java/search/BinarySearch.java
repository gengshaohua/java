package search;

public class BinarySearch {

    public int search(int[] arr, int start, int end, int value){
        if(start > end) return -1;
        int middle = start + ((end - start) >> 1);
        if(arr[middle] == value){
            return middle;
        }else if(arr[middle] < value){
            return search(arr, middle + 1, end, value);
        }else{
            return search(arr, start, middle-1, value);
        }
    }

    //查找第一个值等于给定值的元素
    public int search(int[] arr, int n, int value){
        int low = 0;
        int height = n-1;
        while(low <= height){
            int middle = low + ((height - low) >> 1);
            if(arr[middle] > value){
                height = middle - 1;
            }else if(arr[middle] < value){
                low = middle + 1;
            }else{
                if(middle == 0 || (arr[middle-1]!=value)) return middle;
                else height = middle - 1;
            }
        }
        return -1;
    }

    //查找最后一个值等于给定值的元素
    public int searchEnd(int[] arr, int n, int value){
        int low = 0;
        int height = n-1;
        while(low <= height){
            int middle = low + ((height - low) >> 2);
            if(arr[middle] > value){
                height = middle - 1;
            }else if(arr[middle] < value){
                low = middle + 1;
            }else{
                if(middle == n || arr[middle+1] != value) return middle;
                else low = middle + 1;
            }
        }
        return -1;
    }

    //5 ------6s
    //1--3--4--6--7
    //查找第一个大于等于给定值的元素
    public int searchGreaterThan(int[] arr, int n, int value){
        int low = 0;
        int height = n - 1;
        while(low <= height){
            int middle = low + ((height - low) >> 2);
            if(arr[middle] < value){
                low = middle + 1;
            }else if(arr[middle] >= value){
                if(middle == 0 || arr[middle - 1] < value) return middle;
                else height = middle - 1;
            }
        }
        return -1;
    }

    //查找第一个小于等于给定值的元素
    public int searchLessThan(int[] arr, int n, int value){
        int low = 0;
        int height = n -1;
        while(low <= height){
            int middle = low + ((height - low) >> 2);
            if(arr[middle] > value){
                height = middle - 1;
            }else if(arr[middle] <= value){
                if(middle == n - 1 || (arr[middle + 1] > value)) return middle;
                else low = middle + 1;
            }
        }
        return -1;
    }
}
