package leetcode;

import java.util.ArrayList;
import java.util.List;

public class Solution148 {

    // 0148
    // https://leetcode.com/problems/sort-list/description/

    public class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    // using the concept of merge sort
    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        List<ListNode> list = divide(head);
        ListNode h1 = sortList(list.get(0));
        ListNode h2 = sortList(list.get(1));
        return merge(h1, h2);
    }

    // using fast and slow pointer
    private List<ListNode> divide(ListNode h){
        ListNode c = h;
        ListNode n = h.next.next;
        // [4,2,1,3]
        while (n != null) {
            c = c.next;
            n = n.next;
            if (n != null){
                n = n.next;
            }
        }

        ListNode h2 = c.next;
        c.next = null;
        ArrayList<ListNode> list = new ArrayList<>(2);
        list.add(h);
        list.add(h2);
        return list;
    }

    private ListNode merge(ListNode h1, ListNode h2){
        ListNode h = null;
        ListNode cur = null;
        while (h1 != null && h2 != null) {
            if (h1.val < h2.val){
                if (h == null) {
                    h = h1;
                    cur = h;
                } else {
                    cur.next = h1;
                    cur = h1;
                }
                h1 = h1.next;
            } else {
                if (h == null) {
                    h = h2;
                    cur = h;
                } else {
                    cur.next = h2;
                    cur = h2;
                }
                h2 = h2.next;
            }
        }
        while (h1 != null) {
            cur.next = h1;
            break;
        }
        while (h2 != null) {
            cur.next = h2;
            break;
        }

        return h;
    }

    // [4,2,1,3]
    public ListNode sortList1(ListNode head) {
        ListNode h = head;
        ListNode nex = (h != null) ? h.next : null;
        ListNode cur = head;
        while (nex != null) {
            if (nex.val >= cur.val) {
                cur = nex;
                nex = nex.next;
                continue;
            }

            ListNode tempNex = nex.next;
            if (nex.val <= h.val) {
                nex.next = h;
                h = nex;

                cur.next = tempNex;
                nex = tempNex;
                continue;
            }

            ListNode tp = h;
            ListNode tn = tp.next;
            while (true) {
                if (nex.val <= tn.val){
                    tp.next = nex;
                    nex.next = tn;
                    break;
                }

                if (tn == cur){
                    break;
                }
                tp = tn;
                tn = tn.next;
            }

            cur.next = tempNex;
            nex = tempNex;
        }

        return h;
    }
}
