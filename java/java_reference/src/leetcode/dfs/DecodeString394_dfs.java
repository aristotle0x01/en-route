package leetcode.dfs;

public class DecodeString394_dfs {

    // ref: https://blog.nowcoder.net/n/6f7996b450d643429c0ced65711c603d
    // the key to attack this problem in dfs lies in pattern recognition:
    //  sub string inside "[]" is simply another recursion

    private int index;
    public String decodeString(String s) {
        index = 0;
        return decode(s);
    }

    private String decode(String s) {
        StringBuilder sb = new StringBuilder();

        StringBuilder timesSb = new StringBuilder();
        while (index < s.length()) {
            char c = s.charAt(index);
            if (c >= '0' && c<= '9') {
                timesSb.delete(0, timesSb.length());
                while (c >= '0' && c<= '9') {
                    timesSb.append(c);
                    c = s.charAt(++index);
                }
            } else if (c == '[') {
                index++;
                String subStr = decode(s);
                for (int j = 0; j<Integer.parseInt(timesSb.toString()); j++) {
                    sb.append(subStr);
                }
            } else if (c == ']') {
                index++;
                return sb.toString();
            } else {
                sb.append(c);
                index++;
            }
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        DecodeString394_dfs s = new DecodeString394_dfs();
        // "3[a2[c]]"
        System.out.println(s.decodeString("3[z]2[2[y]pq4[2[jk]e1[f]]]ef"));
    }
}
