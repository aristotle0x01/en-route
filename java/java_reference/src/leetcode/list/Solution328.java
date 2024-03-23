package leetcode.list;

public class Solution328 {
    // 328
    // https://leetcode.com/problems/odd-even-linked-list/description/

    public ListNode oddEvenList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        int i = 3;
        ListNode l1 = head, l1cur = head;
        ListNode l2 = head.next, l2cur = head.next;
        ListNode cur = l2cur.next;
        while (cur != null) {
            if (i % 2 != 0) {
                l1cur.next = cur;
                l1cur = cur;
            } else {
                l2cur.next = cur;
                l2cur = cur;
            }
            cur = cur.next;
            i++;
        }
        l1cur.next = l2;
        l2cur.next = null;

        return l1;
    }

    private static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int x) {
            val = x;
            next = null;
        }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
}
