package interview.liftoff;

import java.util.*;

public class RandCity {
    // 返回的数据概率需要根据人数比例
    /*
    Sample data:
    NYC - 100
    SF - 9
    London - 60

     "NYC" is 100 / 169
     radomCity() String
     NYC  100 / 169
     SF 9/169
     Lodon 60/169
     */

    private int sum;
    private List<City> list;
    private TreeMap<Integer, City> rangeMap;
    private Random rand;

    public RandCity() {
        this.sum = 0;
        this.list = Arrays.asList(
                new City("NYC", 60),
                new City("SF", 10),
                new City("London", 30));
        this.rangeMap = new TreeMap<>();
        this.rand = new Random();

        // 0 - 99
        // 100 - 108
        // 109 - 168
        for (City c : this.list) {
            this.rangeMap.put(this.sum, c);
            this.sum += c.population;
        }
    }

    public String getRandCity() {
        int range = rand.nextInt(this.sum);
        Map.Entry<Integer, City> entry = this.rangeMap.floorEntry(range);
        return String.format("%s %d/%d", entry.getValue().name, entry.getValue().population, this.sum);
    }

    public static void main(String[] args) {
        RandCity rc = new RandCity();
        for (int i=0; i<100; i++) {
            System.out.println(rc.getRandCity());
        }
    }

    private static class City {
        String name;
        int population;

        public City(String name, int population) {
            this.name = name;
            this.population = population;
        }
    }
}
