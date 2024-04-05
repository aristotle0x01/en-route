package leetcode.dfs;

import leetcode.TreeNode;

public class KthSmallestElementInABST230 {
    int count = 0;
    int K = 0;
    int v = -1;
    public int kthSmallest(TreeNode root, int k) {
        K = k;
        dfs(root);
        return v;
    }

    private void dfs(TreeNode root) {
        if (root == null) {
            return;
        }
        dfs(root.left);
        count++;
        if (count == K) {
            v = root.val;
            return;
        }
        dfs(root.right);
    }
}
