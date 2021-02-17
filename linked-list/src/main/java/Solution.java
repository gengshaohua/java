/**
 * 解决方案
 */
public class Solution {

    /**
     * 是否是回文链表  eg : 1->2->3->2->1 true ，   快慢指针
     * @param head
     * @return
     */
    public boolean isPalindrome(ListNode head) {
        //1.判断链表是否为空
        if(head == null || head.next == null)
            return false;

        //用3个指针存储链表
        ListNode prev = null;   //链表折中左边的值
        ListNode slow = head;   //链表折中右边的值
        ListNode fast = head;   //快指针分出前2个链表

        while(fast != null && fast.next != null){
            fast = head.next.next;      //3->2->1 ; 1->null
            ListNode next = head.next;  //2->3->2->1 ; 3->2->1
            slow.next = prev;           //1 ; 2->1->null
            prev = slow;                //1 -> null; 2->1->null
            slow = next;                //2->3->2->1; 3->2->1
        }

        if(fast != null){
            slow = slow.next;
        }

        while(slow != null){
            if(slow.next != prev.next)
                return false;
            slow = slow.next;
            prev = prev.next;
        }
        return true;
    }

}
