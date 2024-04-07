package leetcode.dfs;

import java.util.*;

public class LetterCombinationsOfAPhoneNumber17 {
    private int len;

    private HashMap<Character, List<Character>> map;

    public List<String> letterCombinations(String digits) {
        List<String> list = new ArrayList<>();
        if (digits == null || digits.isEmpty()) {
            return list;
        }
        this.len = digits.length();
        map = new HashMap<>();
        map.put('2', Arrays.asList('a', 'b', 'c'));
        map.put('3', Arrays.asList('d', 'e', 'f'));
        map.put('4', Arrays.asList('g', 'h', 'i'));
        map.put('5', Arrays.asList('j', 'k', 'l'));
        map.put('6', Arrays.asList('m', 'n', 'o'));
        map.put('7', Arrays.asList('p', 'q', 'r', 's'));
        map.put('8', Arrays.asList('t', 'u', 'v'));
        map.put('9', Arrays.asList('w', 'x', 'y', 'z'));

        dfs(digits, 0, new char[this.len], list);
        return list;
    }

    private void dfs(String digits, int index, char[] arr, List<String> list) {
        if (index >= this.len) {
            list.add(String.valueOf(arr));
            return;
        }

        List<Character> clist = map.get(digits.charAt(index));
        for (Character c : clist) {
            arr[index] = c;
            dfs(digits, index + 1, arr, list);
        }
    }

    public static void main(String[] args) {
        LetterCombinationsOfAPhoneNumber17 s = new LetterCombinationsOfAPhoneNumber17();
        List<String> list = s.letterCombinations("23");
        System.out.println(list.size());
    }
}
