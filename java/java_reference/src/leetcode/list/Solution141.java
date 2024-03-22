package leetcode.list;

public class Solution141 {
    // 141
    // https://leetcode.com/problems/linked-list-cycle/description/

    public boolean hasCycle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        while (slow != null && fast != null) {
            slow = slow.next;
            fast = fast.next;
            if (fast == null) {
                break;
            }
            fast = fast.next;

            if (slow == fast) {
                return true;
            }
        }

        return false;
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
