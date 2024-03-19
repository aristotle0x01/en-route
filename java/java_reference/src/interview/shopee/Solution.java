package interview.shopee;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// 438
// https://leetcode.com/problems/find-all-anagrams-in-a-string/description/

//s = "abcdefbca",
//p = "abc"
//anagram of p in s
//
//anagrams: abc, acb, bca, bac, cab, cba
//
//index of anagrams in s
//[0, 6]
public class Solution {

    private final HashSet<String> valid = new HashSet<>();
    public List<Integer> findAnagrams(String s, String p) {
        if (s == null || p == null || p.isEmpty() || s.isEmpty()) {
            return new ArrayList<>();
        }
        if (p.length() > s.length()) {
            return new ArrayList<>();
        }

        valid.clear();
        for (int i=0; i<s.length(); i++) {
            valid.add(s.substring(i, i+1));
        }

        ArrayList<Integer> results = new ArrayList<>();

        List<String> permutations = permutations(p);
        Set<String> spermutations = new HashSet<>();
        for (int i =0;i<permutations.size(); i++) {
            spermutations.add(permutations.get(i));
        }
        spermutations.forEach(perm -> {
            String permutation = perm;
            int index=0;
            while (index < s.length()) {
                int temp = s.indexOf(permutation, index);
                if (temp == -1) {
                    break;
                }
                results.add(temp);
                System.out.println("" + temp + ": " +permutation);
                index = temp + 1;
            }
        });

        return results;
    }

    public List<String> permutations(String p) {
        return doPermutation(p);
    }

    private List<String> doPermutation(String p) {
        if (p.isEmpty()) {
            return new ArrayList<>();
        }

        ArrayList<String> permutations = new ArrayList<>();
        if (p.length() == 1) {
            permutations.add(p);
            return permutations;
        }

        for (int i=0; i<p.length(); i++) {
            if (!valid.contains(p.substring(i, i+1))) {
                continue;
            }

            StringBuilder left = new StringBuilder();
            if ((i-1) >=0) {
                left.append(p.substring(0, i));
            }
            if ((i+1) < p.length()) {
                left.append(p.substring(i+1));
            }

            List<String> subPermutations = doPermutation(left.toString());
            for (int j=0; j<subPermutations.size(); j++) {
                String s = p.substring(i, i+1)+subPermutations.get(j);
                permutations.add(s);
            }
        }

        return permutations;
    }
}
