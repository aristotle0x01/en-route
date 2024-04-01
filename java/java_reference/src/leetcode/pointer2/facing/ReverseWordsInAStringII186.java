package leetcode.pointer2.facing;

public class ReverseWordsInAStringII186 {
    public String reverseWords(String s) {
        StringBuilder sb = new StringBuilder();

        int i = s.length()-1;
        int j = i;
        while (i >= 0) {
            while (i >= 0 && s.charAt(i) != ' ') i--;
            if (i > 0) {
               sb.append(s, i+1, j+1);
               sb.append(' ');
               i--;
               j = i;
            } else {
                // i < 0
                sb.append(s, i+1, j+1);
            }
        }

        return sb.toString();
    }

    // first reverse each word, then reverse sentence
    public void reverseWords2(char[] s) {
        int n = s.length;
        int i = 0;
        int j = 0;
        // "hello wold i am a guy!"
        while (i < n && j < n) {
            while (j < n && s[j] != ' ') j++;

            int start = i;
            int end = j-1;
            while (start < end) {
                char c = s[end];
                s[end] = s[start];
                s[start] = c;
                start++;
                end--;
            }

            j++;
            i = j;
        };

        i = 0;
        j = n-1;
        while (i < j) {
            char c = s[j];
            s[j] = s[i];
            s[i] = c;
            i++;
            j--;
        }
    }

    public static void main(String[] args) {
        ReverseWordsInAStringII186 re = new ReverseWordsInAStringII186();
        System.out.println(re.reverseWords("hello wold i am a guy!"));

        String s = "hello";
        char[] arr = s.toCharArray();
        re.reverseWords2(arr);
        for (int i=0; i<s.length();i++) {
            System.out.print(arr[i]);
        }
        System.out.println();
    }
}
