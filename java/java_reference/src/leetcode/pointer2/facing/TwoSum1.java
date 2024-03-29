package leetcode.pointer2.facing;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class TwoSum1 {
    // https://leetcode.com/problems/two-sum/description/

    public int[] twoSum(int[] nums, int target) {
        if (nums == null || nums.length <= 1) {
            throw new InvalidParameterException("invliad param");
        }

        // sort
        int[] copy = nums.clone();
        Arrays.sort(copy);

        // two facing pointers facing each other from start and end
        int i = 0;
        int j = copy.length-1;
        while (i < j) {
            int v = copy[i] + copy[j];
            if (v > target) {
                j--;
            } else if (v < target){
                i++;
            } else {
                break;
            }
        }

        if (i >= j) {
            throw new RuntimeException("not found");
        }

        boolean bi = false, bj = false;
        for (int k=0; k<nums.length; k++) {
            if (!bi && nums[k] == copy[i]) {
                i = k;
                bi = true;
            } else if (!bj && nums[k] == copy[j]) {
                j = k;
                bj = true;
            }
        }
        int[] r = new int[2];
        r[0] = i;
        r[1] = j;

        return r;
    }

    public int[] twoSum2(int[] nums, int target) {
        if (nums == null || nums.length <= 1) {
            throw new InvalidParameterException("invliad param");
        }

        HashMap<Integer, List<Integer>> map = new HashMap<>();
        for (int i=0; i<nums.length; i++) {
            if (map.containsKey(nums[i])) {
                map.get(nums[i]).add(i);
            } else {
                List<Integer> list = new ArrayList<>();
                list.add(i);
                map.put(nums[i], list);
            }
        }

        // sort
        Arrays.sort(nums);

        // two facing pointers facing each other from start and end
        int i = 0;
        int j = nums.length-1;
        while (i < j) {
            int v = nums[i] + nums[j];
            if (v > target) {
                j--;
            } else if (v < target){
                i++;
            } else {
                break;
            }
        }
        if (i >= j) {
            throw new RuntimeException("not found");
        }

        int[] r = new int[2];
        r[0] = map.get(nums[i]).remove(0);
        r[1] = map.get(nums[j]).remove(0);

        return r;
    }

    public int[] twoSum3(int[] nums, int target) {
        if (nums == null || nums.length <= 1) {
            throw new InvalidParameterException("invliad param");
        }

        HashMap<Integer, List<Integer>> map = new HashMap<>();
        for (int i=0; i<nums.length; i++) {
            if (map.containsKey(nums[i])) {
                map.get(nums[i]).add(i);
            } else {
                List<Integer> list = new ArrayList<>();
                list.add(i);
                map.put(nums[i], list);
            }
        }

        int i = -1;
        int j = -1;
        for (int k=0; k<nums.length; k++) {
            int vi = nums[k];
            int vj = target - vi;
            if (!map.containsKey(vj)) {
                continue;
            }
            if (vi != vj) {
                i = k;
                j = map.get(vj).get(0);
                break;
            } else {
                if (map.get(vi).size() >= 2) {
                    i = map.get(vj).get(0);
                    j = map.get(vj).get(1);
                    break;
                }
            }
        }
        if (i == -1 || j == -1) {
            throw new RuntimeException("not found");
        }
        return new int[]{i, j};
    }
}
