package linkedList;

public class MergeLink {

    public static void main(String[] args) {
        ListNode listNode = new ListNode(2);
        ListNode listNode1 = new ListNode(4);
        ListNode listNode2 = new ListNode(3);
        listNode.next = listNode1;
        listNode1.next = listNode2;

        ListNode listNode4 = new ListNode(5);
        ListNode listNode5 = new ListNode(6);
        ListNode listNode6 = new ListNode(4);

        listNode4.next = listNode5;
        listNode5.next = listNode6;
        ListNode merge = merge(listNode, listNode4);
        while(merge != null){
            System.out.println(merge.val);
            merge = merge.next;
        }
    }

    public static ListNode merge(ListNode listNode1, ListNode listNode2){
        if(listNode1 == null || listNode2 == null)
            return null;

        ListNode result = null;
        ListNode pNode = listNode1;
        ListNode fNode = listNode2;
        int much = 0;
        while(pNode != null){
            int val = pNode.val;
            int val1 = fNode.val;
            int sum = val + val1 + much;
            much = 0;
            if(sum >= 10){
                much = sum / 10;
                if(result == null) {
                    result = new ListNode(sum % 10);
                }else {
                    ListNode temp = result;
                    while (temp.next != null)
                        temp = temp.next;
                    temp.next = new ListNode(sum % 10);
                }
            }else{
                if(result == null) {
                    result = new ListNode(sum);
                }else{
                    ListNode temp = result;
                    while (temp.next != null)
                        temp = temp.next;
                    temp.next = new ListNode(sum);
                }
            }
            pNode = pNode.next;
            fNode = fNode.next;
        }
        return result;
    }
}
