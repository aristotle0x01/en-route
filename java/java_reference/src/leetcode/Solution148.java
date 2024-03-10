package leetcode;

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

    // [4,2,1,3]
    public ListNode sortList(ListNode head) {
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
