package leetcode.bfs;

import java.util.*;

public class BinaryTreeZigzagLevelOrderTraversal103 {
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> list = new ArrayList<>();
        if (root == null) {
           return list;
        }

        Deque<TreeNode> cur = new LinkedList<>();
        Deque<TreeNode> next = new LinkedList<>();
        cur.add(root);
        int level = 0;
        while (!cur.isEmpty()) {
            // save all the children
            for (TreeNode node : cur) {
                if (node.left != null) {
                    next.offer(node.left);
                }
                if (node.right != null) {
                    next.offer(node.right);
                }
            }

            // traverse node
            List<Integer> sublist = new ArrayList<>();
            if (level % 2 == 0) {
                while (!cur.isEmpty()) {
                    TreeNode node = cur.poll();
                    sublist.add(node.val);
                }
            } else {
                while (!cur.isEmpty()) {
                    TreeNode node = cur.pollLast();
                    sublist.add(node.val);
                }
            }
            list.add(sublist);
            if (!next.isEmpty()) {
                cur.addAll(next);
                next.clear();
            }
            level++;
        }

        return list;
    }

    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}
