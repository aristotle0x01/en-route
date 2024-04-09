package leetcode.dfs;

import leetcode.TreeNode;

public class FlipEquivalentBinaryTrees951 {
    public boolean flipEquiv(TreeNode root1, TreeNode root2) {
        if (root1 == null && root2 == null) {
            return true;
        }
        if (root1 == null || root2 == null) {
            return false;
        }
        if (root1.val != root2.val) {
            return false;
        }

        if (!compareNodes(root1.left, root2.left) || !compareNodes(root1.right, root2.right)) {
            TreeNode t = root1.right;
            root1.right = root1.left;
            root1.left = t;
            if (!compareNodes(root1.left, root2.left) || !compareNodes(root1.right, root2.right)) {
                return false;
            }
        }

        return flipEquiv(root1.left, root2.left) && flipEquiv(root1.right, root2.right);
    }

    private boolean compareNodes(TreeNode n1, TreeNode n2) {
        if (n1 == null && n2 == null) {
            return true;
        }
        if (n1 == null || n2 == null) {
            return false;
        }
        return n1.val == n2.val;
    }
}
