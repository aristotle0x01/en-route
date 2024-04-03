package leetcode.bfs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BinaryTreeLevelOrderTraversal102 {
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

    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> list = new ArrayList<>();
        if (root == null) {
            return list;
        }

        Queue<TreeNode> curlevel = new LinkedList<>();
        Queue<TreeNode> nextlevel = new LinkedList<>();
        curlevel.offer(root);
        while (!curlevel.isEmpty()) {
            List<Integer> l = new ArrayList<>();
            while (!curlevel.isEmpty()) {
                TreeNode node = curlevel.poll();
                l.add(node.val);
                if (node.left != null) {
                    nextlevel.offer(node.left);
                }
                if (node.right != null) {
                    nextlevel.offer(node.right);
                }
            }

            if (!l.isEmpty()) {
                list.add(l);
            }
            if (!nextlevel.isEmpty()) {
                curlevel.addAll(nextlevel);
                nextlevel.clear();
            }
        }

        return list;
    }

}
