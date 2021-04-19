package linkedList;

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

    /**
     * 暴力反转法 找到最后一个指针 移除 复制 进行反转
     * @param head
     * @return
     */
    public ListNode reverse1(ListNode head){
        if(head == null || head.next == null)
            return head;
        ListNode reverse = null;
        ListNode object = head;

        while(head != null){
            if(head.next == null){
                ListNode node = head;
                ListNode temp = reverse;
                if(temp == null){
                    reverse = node;
                }else{
                    while (temp.next != null) {
                        temp = temp.next;
                    }
                    temp.next = node;
                }
                head = null;
            }else if(head.next.next == null){
                ListNode node = head.next;
                ListNode temp = reverse;
                if(temp == null){
                    reverse = node;
                }else{
                    while (temp.next != null) {
                        temp = temp.next;
                    }
                    temp.next = node;
                }
                head.next = null;
                head = object;
            }else{
                head = head.next;
            }
        }
        return reverse;
    }

    /**
     * 反转方式二：从头部取第一个放在反转列表里最后一个位置
     * @param head
     * @return
     */
    public ListNode reverse2(ListNode head){
        if(head == null || head.next == null)
            return head;
        ListNode reverse = null;
        while(head != null){
            if(head.next != null){
                ListNode next= head.next;
                ListNode node = head;
                node.next = reverse;
                reverse = node;
                head = next;
            }else{
                ListNode node = head;
                node.next = reverse;
                reverse = node;
                head = null;
            }
        }
        return reverse;
    }

    /**
     * 检测链表是否是环状
     * @param head
     * @return
     */
    public boolean cycle(ListNode head){
        if(head == null || head.next == null)
            return false;

        ListNode slow = head;
        ListNode fast = head;

        while(fast != null && fast.next != null){
            fast = fast.next.next;
            slow = slow.next;
            if(fast == slow)
                return true;
        }
        return false;
    }

    /**
     * 合并有序链表
     * @param node1
     * @param node2
     * @return
     */
    public ListNode merge(ListNode node1, ListNode node2){
        if(node1 == null ||  node2 == null)
            return null;

        ListNode newList = null;

        while(node1 != null){
            if(node2 != null){
                while(node2 != null){
                    ListNode temp = newList;
                    if(node1.val >= node2.val){
                        ListNode listNode = new ListNode(node2.val);
                        if(temp == null){
                            newList = listNode;
                        }else{
                            while(temp.next != null){
                                temp = temp.next;
                            }
                            temp.next = listNode;
                        }
                        node2 = node2.next;
                    }else{
                        ListNode listNode = new ListNode(node1.val);
                        if(temp == null){
                            newList = listNode;
                        }else{
                            while(temp.next != null){
                                temp = temp.next;
                            }
                            temp.next = listNode;
                        }
                        break;
                    }
                }
            }else{
                ListNode temp = newList;
                ListNode listNode = new ListNode(node1.val);
                while(temp.next != null){
                    temp = temp.next;
                }
                temp.next = listNode;
            }
            node1 = node1.next;
        }
        return newList;
    }

    /**
     * 删除链表中指定节点
     * @param head
     * @param n
     * @return
     */
    public ListNode removeIndex(ListNode head, int n){
        if(head == null)
            return null;
        ListNode object = head;
        int i = 1;
        while(i < n-1){
            head = head.next;
            if (head == null) {
                return object;
            }
            i++;
        }
        if(head.next != null){
            head.next = head.next.next;
        }
        return object;
    }

    /**
     * 查找中间节点
     * @param head
     * @return
     */
    public ListNode middleNode(ListNode head){
        ListNode fast = head;
        ListNode slow = head;
        while(fast != null && fast.next != null){
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(5);
        ListNode node3 = new ListNode(6);
        ListNode node4 = new ListNode(7);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        ListNode node5 = new ListNode(1);
        ListNode node6 = new ListNode(2);
        ListNode node7 = new ListNode(3);
        node5.next = node6;
        node6.next = node7;
       /* linkedList.ListNode reverse = solution.reverse1(node1);
        linkedList.ListNode reverse2 = solution.reverse2(reverse);*/
        /*boolean cycle = solution.cycle(node1);*/
        /*linkedList.ListNode merge = solution.merge(node1, node5);*/
        /*linkedList.ListNode node = solution.removeIndex(node1, 3);*/
        ListNode node = solution.middleNode(node1);
        System.out.println(node);
    }
}
