package leetcode.list;

class Solution160 {

    // 160
    // https://leetcode.com/problems/intersection-of-two-linked-lists/description/

    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode a = headA;
        ListNode b;
        while (a != null) {
            b = headB;
            while (b != null) {
                if (a == b) {
                    return a;
                }
                b = b.next;
            }

            a = a.next;
        }

        return null;
    }

    private static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
            val = x;
            next = null;
        }
    }
}