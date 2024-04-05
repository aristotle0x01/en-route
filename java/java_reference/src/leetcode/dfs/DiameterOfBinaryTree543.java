package leetcode.dfs;

import leetcode.TreeNode;

public class DiameterOfBinaryTree543 {
    private int diameter = 0;

    public int diameterOfBinaryTree(TreeNode root) {
        height(root);
        return diameter;
    }

    private int height(TreeNode root) {
        if (root == null) {
            return 0;
        }
        if (root.left == null && root.right == null) {
            return 0;
        }

        int left = 0;
        if (root.left != null) {
            left = 1 + height(root.left);
        }
        int right = 0;
        if (root.right != null) {
            right = 1 + height(root.right);
        }
        diameter = Math.max(diameter, left+right);
        return Math.max(left, right);
    }
}
