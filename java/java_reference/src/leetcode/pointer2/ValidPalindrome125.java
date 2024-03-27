package leetcode.pointer2;

public class ValidPalindrome125 {
    // https://leetcode.com/problems/valid-palindrome/description/

    public boolean isPalindrome(String s) {
        s = s.toLowerCase();
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<s.length(); i++) {
            char c = s.charAt(i);
            if ((c >= 'a' && c<='z') || (c>='0' && c <= '9')) {
                sb.append(c);
            }
        }

        s = sb.toString();
        if (s.isEmpty() || s.length() == 1) {
            return true;
        }

        int i = 0;
        int j = s.length()-1;
        while (i < j) {
            if (s.charAt(i) != s.charAt(j)) {
                return false;
            }
            i++;
            j--;
        }

        return true;
    }
}
