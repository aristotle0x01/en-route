package leetcode.list;

class Solution206 {

    // 0206
    // https://leetcode.com/problems/reverse-linked-list/description/

    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode reversed = null;
        ListNode cur = head;
        ListNode next = head.next;
        while (cur != null) {
            cur.next = reversed;
            reversed = cur;
            cur = next;
            if (next != null) {
                next = next.next;
            }
        }

        return reversed;
    }

    private static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
}