package interview.jerryai;

import java.security.InvalidParameterException;
import java.util.*;

public class IntensitySegments {
    // container for intensity segments
    private final TreeMap<Integer, Integer> container;

    public IntensitySegments() {
        container = new TreeMap<>();
    }

    /**
     * add an amount for range [from, to)
     */
    public void add(int from, int to, int amount) {
        if (to <= from) {
            throw new InvalidParameterException(String.format("%d(to) is no greater than %d(from)", to, from));
        }

        if (container.isEmpty()) {
            container.put(from, amount);
            container.put(to, 0);
            return;
        }

        Map.Entry<Integer, Integer> sentry = container.floorEntry(from);
        int oldFromValue;
        if (sentry == null) {
            container.put(from, amount);
            oldFromValue = 0;
        } else {
            oldFromValue = sentry.getValue();
            container.put(from, sentry.getValue() + amount);
        }
        if (!container.containsKey(to)) {
            Map.Entry<Integer, Integer> eentry = container.lowerEntry(to);
            if (eentry.getKey() != from) {
                container.put(to, eentry.getValue());
            } else {
                container.put(to, oldFromValue);
            }
        }

        // java.util.SortedMap<Integer, Integer> subMap = container.subMap(from, to);
        Iterator<Integer> descKeys = container.descendingKeySet().iterator();
        while (descKeys.hasNext()) {
            int key = descKeys.next();
            if (key > to) {
                continue;
            } else if (key == to) {
                continue;
            } else if (key > from) {
                // key -> (from, to)
                container.put(key, container.get(key) + amount);
            } else {
                // key <= from
                break;
            }
        }

        removeConsecutiveZeroSegments();
    }

    /**
     * set intensity to amount for range [from, to)
     */
    public void set(int from, int to, int amount) {
        if (to <= from) {
            throw new InvalidParameterException(String.format("%d(to) is no greater than %d(from)", to, from));
        }

        if (container.containsKey(to)) {
            // do nothing
        } else {
            Map.Entry<Integer, Integer> entry = container.lowerEntry(to);
            if (entry == null) {
                container.put(to, 0);
            } else {
                container.put(to, entry.getValue());
            }
        }

        List<Integer> removeRanges = new ArrayList<>();
        Iterator<Integer> it = container.descendingKeySet().iterator();
        while (it.hasNext()) {
            int range = it.next();
            if (range > to) {
               continue;
            } else if (range == to) {
                continue;
            } else if (range > from) {
                removeRanges.add(range);
            } else {
                // range <= from
                break;
            }
        }
        for (int r : removeRanges) {
            container.remove(r);
        }
        container.put(from, amount);

        removeConsecutiveZeroSegments();
    }

    /**
     * print all the intensity segments like "[[10, 1], [20, 0]]"
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("[");
        for (Map.Entry<Integer, Integer> p : container.entrySet()) {
            sb.append(String.format("[%d,%d],", p.getKey(), p.getValue()));
        }
        if (sb.charAt(sb.length()-1) == ',') {
            sb.deleteCharAt(sb.length()-1);
        }
        sb.append("]");

        return sb.toString();
    }

    private void removeConsecutiveZeroSegments() {
        if (container.isEmpty()) {
            return;
        }
        while (!container.isEmpty()) {
            int key = container.firstKey();
            if (container.get(key) == 0) {
                container.remove(key);
            }else {
                break;
            }
        }

        List<Integer> removeKeys = new ArrayList<>();
        Object[] keys = container.keySet().toArray();
        int n = keys.length;
        int i = 0;
        while (i < n) {
            // find first non-zero
            while (i < n) {
                int key = (int)keys[i];
                if (container.get(key) != 0) {
                    i++;
                } else {
                    break;
                }
            }
            if (i >= n) {
                break;
            }

            i++;
            while (i < n) {
                int key = (int)keys[i];
                if (container.get(key) == 0) {
                    removeKeys.add(key);
                    i++;
                } else {
                    break;
                }
            }
        }
        for (int k : removeKeys) {
            container.remove(k);
        }
    }

    public static void main(String[] args) {
        // Here is an example sequence:
        // (data stored as an array of start point and value for each segment.)
        IntensitySegments segments = new IntensitySegments();
        System.out.println(segments); // Should be "[]"
        segments.add(10, 30, 1);
        System.out.println(segments); // Should be: "[[10,1],[30,0]]"
        segments.add(20, 40, 1);
        System.out.println(segments); // Should be: "[[10,1],[20,2],[30,1],[40,0]]"
        segments.add(10, 40, -2);
        System.out.println(segments); // Should be: "[[10,-1],[20,0],[30,-1],[40,0]]"

        // Another example sequence:
        segments = new IntensitySegments();
        System.out.println(segments); // Should be "[]"
        segments.add(10, 30, 1);
        System.out.println(segments); // Should be "[[10,1],[30,0]]"
        segments.add(20, 40, 1);
        System.out.println(segments); // Should be "[[10,1],[20,2],[30,1],[40,0]]"
        segments.add(10, 40, -1);
        System.out.println(segments); // Should be "[[20,1],[30,0]]"
        segments.add(10, 40, -1);
        System.out.println(segments); // Should be "[[10,-1],[20,0],[30,-1],[40,0]]"

        segments.set(15, 35, 5);
        System.out.println(segments); // Should be "[[10,-1],[15,5],[35,-1],[40,0]]"

        segments.set(10, 30, 0);
        System.out.println(segments); // Should be "[[30,5],[35,-1],[40,0]]"
    }
}
