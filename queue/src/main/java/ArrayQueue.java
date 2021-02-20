public class ArrayQueue {

    private final String[] items;
    private final int n;
    private int head = 0;
    private int tail = 0;

    public ArrayQueue(int n){
        this.n = n;
        this.items = new String[n];
    }

    public boolean enqueue(String item){
        if (tail == n) { // tail ==n && head==0，表示整个队列都占满了 if (head == 0) return false; // 数据搬移
            // 搬移完之后重新更新head和 tail
            if (tail - head >= 0) System.arraycopy(items, head, items, 0, tail - head);
            tail -= head;
            head = 0;
        }
        items[tail] = item;
        ++tail;
        return true;
    }

    public String dequeue(){
        if(head == tail) return null;
        String item = items[head];
        ++head;
        return item;
    }

}
