package leetcode.dfs;

import java.util.ArrayList;
import java.util.List;

public class RestoreIPAddresses93 {
    public List<String> restoreIpAddresses(String s) {
        List<String> list = new ArrayList<>();
        if (s == null || s.length() < 4) {
            return list;
        }

        restore(s, 0, 4, list, new StringBuilder());
        return list;
    }

    // 1. parameter passing
    // 2. backtrack delete, also accounting dots
    private void restore(String s, int startIndex, int leftAddresses, List<String> list, StringBuilder restored) {
        int n = s.length();
        if (startIndex >= n) {
            return;
        }

        if (leftAddresses == 1) {
            int left = n-startIndex;
            if (s.charAt(startIndex) == '0' && left > 1) {
                return;
            }
            if (left > 3) {
                return;
            }
            int addr = Integer.decode(s.substring(startIndex));
            if (addr > 255) {
                return;
            }
            restored.append(addr);
            list.add(restored.toString());
        } else {
            int max = startIndex + 3;

            for (int i=startIndex; i<n && i<max; i++) {
                int dots = 4-leftAddresses;
                restored.delete(startIndex+dots, restored.length());

                if (i == startIndex && s.charAt(i) == '0') {
                    restored.append('0');
                    restored.append('.');
                    restore(s, i+1, leftAddresses-1, list, restored);
                    break;
                } else {
                    int addr = Integer.decode(s.substring(startIndex, i+1));
                    if (addr > 255) {
                        break;
                    }
                    restored.append(addr);
                    restored.append('.');
                    restore(s, i+1, leftAddresses-1, list, restored);
                }
            }
        }
    }
}
