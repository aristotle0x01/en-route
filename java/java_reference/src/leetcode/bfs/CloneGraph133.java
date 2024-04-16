package leetcode.bfs;

import java.util.*;

public class CloneGraph133 {
    public Node cloneGraph(Node node) {
        if (node == null) {
            return null;
        }

        Set<Integer> set = new HashSet<>();
        Queue<Node> queue = new LinkedList<>();
        queue.offer(node);

        Node root = new Node(node.val, new ArrayList<>());
        HashMap<Integer, Node> newMap = new HashMap();
        newMap.put(node.val, root);
        Node cur;

        while (!queue.isEmpty()) {
            Node nd = queue.poll();
            if (set.contains(nd.val)) {
                continue;
            }
            set.add(nd.val);

            cur = newMap.get(nd.val);
            for (Node n : nd.neighbors) {
                if (!set.contains(n.val)) {
                    queue.offer(n);
                }
                if (newMap.containsKey(n.val)) {
                    cur.neighbors.add(newMap.get(n.val));
                } else {
                    Node nt = new Node(n.val, new ArrayList<>());
                    cur.neighbors.add(nt);
                    newMap.put(n.val, nt);
                }
            }
        }

        return root;
    }

    public static void main(String[] args) {
        CloneGraph133 scl = new CloneGraph133();
        Node n1 = new Node(1, new ArrayList<>());
        Node n2 = new Node(2, new ArrayList<>());
        Node n3 = new Node(3, new ArrayList<>());
        Node n4 = new Node(4, new ArrayList<>());

        n1.neighbors.add(n2);
        n1.neighbors.add(n4);
        n2.neighbors.add(n1);
        n2.neighbors.add(n3);
        n3.neighbors.add(n2);
        n3.neighbors.add(n4);
        n4.neighbors.add(n1);
        n4.neighbors.add(n3);

        scl.cloneGraph(n1);
    }

    // Definition for a Node.
    public static class Node {
        public int val;
        public List<Node> neighbors;
        public Node() {
            val = 0;
            neighbors = new ArrayList<Node>();
        }
        public Node(int _val) {
            val = _val;
            neighbors = new ArrayList<Node>();
        }
        public Node(int _val, ArrayList<Node> _neighbors) {
            val = _val;
            neighbors = _neighbors;
        }
    }
}
