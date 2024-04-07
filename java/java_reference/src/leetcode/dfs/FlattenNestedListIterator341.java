package leetcode.dfs;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class FlattenNestedListIterator341 {
    public class NestedIterator implements Iterator<Integer> {
        private Queue<Integer> vList;
        private List<NestedInteger> nestedList;

        public NestedIterator(List<NestedInteger> nestedList) {
            this.nestedList = nestedList;
            this.vList = new LinkedList<>();
            this.dfs(this.nestedList, this.vList);
        }

        private void dfs(List<NestedInteger> nestedList, Queue<Integer> queue) {
            for (NestedInteger ni : nestedList) {
                if (ni.isInteger()) {
                    queue.offer(ni.getInteger());
                } else {
                    dfs(ni.getList(), queue);
                }
            }
        }

        @Override
        public Integer next() {
            return vList.poll();
        }

        @Override
        public boolean hasNext() {
            return !vList.isEmpty();
        }
    }

    // This is the interface that allows for creating nested lists.
    // You should not implement it, or speculate about its implementation
    public interface NestedInteger {

        // @return true if this NestedInteger holds a single integer, rather than a nested list.
        public boolean isInteger();

        // @return the single integer that this NestedInteger holds, if it holds a single integer
        // Return null if this NestedInteger holds a nested list
        public Integer getInteger();

        // @return the nested list that this NestedInteger holds, if it holds a nested list
        // Return empty list if this NestedInteger holds a single integer
        public List<NestedInteger> getList();
    }
}
