package leetcode.dfs;

import leetcode.TreeNode;

public class ValidateBinarySearchTree98 {
    private Integer last;

    public boolean isValidBST(TreeNode root) {
        return dfs(root);
    }

    private boolean dfs(TreeNode root) {
        if (root == null) {
            return true;
        }
        if (root.left == null && root.right == null) {
            if (last == null) {
                last = root.val;
                return true;
            } else {
                if (root.val > last) {
                    last = root.val;
                    return true;
                } else {
                    return false;
                }
            }
        }
        if (!dfs(root.left)) {
            return false;
        }
        if (last == null ) {
            last = root.val;
        } else if (root.val > last) {
            last = root.val;
        } else {
            return false;
        }
        return dfs(root.right);
    }
}
