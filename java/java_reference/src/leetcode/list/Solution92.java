package leetcode.list;

public class Solution92 {
    // 92
    // https://leetcode.com/problems/reverse-linked-list-ii/description/

    public ListNode reverseBetween(ListNode head, int left, int right) {
        if (head == null || head.next == null) {
            return head;
        }

        // first list: head, tail
        // second list: reversed, head, tail
        // third list: head
        // concatenate above three lists

        // list 1
        ListNode h1 = head;
        ListNode t1 = null;
        // list 2
        ListNode h2 = null, t2 = null;
        // list 3
        ListNode h3 = null;

        int i=1;
        while (i < left) {
            if (t1 == null) {
                t1 = head;
            } else {
                t1 = t1.next;
            }
            i++;
        }

        // list 1 is null, reverse from first node
        if (t1 == null) {
            h2 = head;
        } else {
            h2 = t1.next;
            t1.next = null;
        }

        // head of list2 reversed
        ListNode nh2 = null;
        ListNode prev = null, cur = h2, next = null;
        while (i <= right && cur != null) {
            if (prev == null) {
                t2 = cur;
            }

            next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;

            nh2 = prev;

            if (i == right) {
                h3 = next;
            }
            i++;
        }

        ListNode nHead;
        if (t1 != null) {
            t1.next = nh2;
            nHead = head;
        } else {
            nHead = nh2;
        }
        if (t2 != null) {
            t2.next = h3;
        }

        return nHead;
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
