package stack;

import linkedList.ListNode;

public class LinkedStack {

    private ListNode node;

    public LinkedStack(ListNode node){
        this.node = node;
    }

    public boolean push(ListNode node){
        if(node == null){
            this.node = node;
        } else{
            node.next = this.node;
            this.node = node;
        }
        return true;
    }

    public String pop(){
        if(node == null) return null;
        String item = node.next.val + "";
        node = node.next;
        return item;
    }

}
