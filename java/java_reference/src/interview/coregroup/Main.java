package interview.coregroup;

import java.util.HashMap;
import java.util.HashSet;

public class Main {
    private static int findMinStart2(int[] tourPlan, int numOfSites) {
        int min = Integer.MAX_VALUE;
        int index = -1;
        int days = tourPlan.length;

        HashMap<Integer, Integer> map = new HashMap<>();
        int index1 = 0;
        int index2 = 0;
        int count = 0;
        while (index1 < days && index2 < days) {
            if (map.containsKey(tourPlan[index2])) {
                map.put(tourPlan[index2], map.get(tourPlan[index2]) + 1);
            } else {
                map.put(tourPlan[index2], 1);
            }
            count++;
            index2++;

            while (map.size() == numOfSites) {
                if (count < min) {
                    min = count;
                    index = index1;
                }

                if (map.get(tourPlan[index1]) == 1) {
                    map.remove(tourPlan[index1]);
                } else {
                    map.put(tourPlan[index1], map.get(tourPlan[index1]) - 1);
                }
                count--;
                index1++;
            }
        }

        return index;
    }

    private static int findMinStart(int[] tourPlan, int numOfSites) {
        int min = Integer.MAX_VALUE;
        int index = -1;
        int days = tourPlan.length;
        HashSet<Integer> set = new HashSet<>();
        int upper = days - numOfSites;
        for (int i=0; i<=upper; i++) {
            int count = 0;
            set.clear();
            for (int j=i; j<days; j++) {
                if (set.size() == numOfSites) {
                    break;
                }
                set.add(tourPlan[j]);
                count++;
            }

            if (count < min && count >= numOfSites) {
                min = count;
                index = i;
            }
        }

        return index;
    }

    public static void main(String[] args) {
        int[] plan = new int[]{1, 2, 1, 2, 5, 4, 3, 1, 2, 5};
        int numOfSites = 5;
        System.out.println(findMinStart2(plan, numOfSites));
        System.out.println(findMinStart(plan, numOfSites));
    }
}
