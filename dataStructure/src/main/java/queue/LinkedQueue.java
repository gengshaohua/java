package queue;

import linkedList.ListNode;

public class LinkedQueue {

    private ListNode node;

    public LinkedQueue(ListNode node){
        this.node = node;
    }

    public boolean enqueue(ListNode node){
        if(node == null) this.node = node;
        ListNode temp = node;
        while(temp.next != null){
            temp = temp.next;
        }
        temp.next = node;
        return true;
    }

    public String dequeue(){
        if(node == null) return null;
        String item = node.next.val + "";
        node = node.next;
        return item;
    }
}
