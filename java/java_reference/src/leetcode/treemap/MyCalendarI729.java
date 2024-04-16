package leetcode.treemap;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class MyCalendarI729 {
    private TreeMap<Integer, Booking> bookingTreeMap;

    public MyCalendarI729() {
        bookingTreeMap = new TreeMap<>();
    }

    public boolean book(int start, int end) {
        for (Integer integer : bookingTreeMap.keySet()) {
            Booking last = bookingTreeMap.get(integer);
            if (last.start >= end) {
                break;
            }

            if (end <= last.end || ((start >= last.start) && (start < last.end))) {
                return false;
            }
        }

        this.bookingTreeMap.put(start, new Booking(start, end));
        return true;
    }

    private static class Booking implements Comparator {
        public final int start;
        public final int end;

        public Booking(int s, int e) {
            this.start = s;
            this.end = e;
        }

        @Override
        public int compare(Object o1, Object o2) {
            return ((Booking)o1).start - ((Booking)o2).start;
        }
    }

    public static class MyCalendar {
        private TreeMap<Integer, Integer> bookingTreeMap;

        public MyCalendar() {
            bookingTreeMap = new TreeMap<>();
        }

        public boolean book(int start, int end) {
            Map.Entry<Integer, Integer> entry = bookingTreeMap.floorEntry(start);
            if (entry != null && start < entry.getValue()) {
                return false;
            }
            entry = bookingTreeMap.ceilingEntry(start);
            if (entry != null && entry.getKey() < end) {
                return false;
            }

            bookingTreeMap.put(start, end);
            return true;
        }
    }
}
