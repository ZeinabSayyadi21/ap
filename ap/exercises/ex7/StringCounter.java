package ap.exercises.ex7;

import java.util.ArrayList;
import java.util.List;

public class StringCounter {

    private List<String> items;
    private List<Integer> counts;

    public StringCounter() {
        this.items = new ArrayList<>();
        this.counts = new ArrayList<>();
    }

    public void add(String item) {
        int index = items.indexOf(item);
        if (index != -1) {
            counts.set(index , counts.get(index + 1));
        } else {
            items.add(item);
            counts.add(1);
        }
    }

    public List<String> getTop(int k) {
        List<String> topItems = new ArrayList<>();
        List<Integer> temp = new ArrayList<>(counts);

        for (int i = 0; i < k && i < items.size(); i++) {

            int maxIndex = 0;
            for (int j = 0; j < temp.size(); j++) {

                if (temp.get(j) > temp.get(maxIndex)) {
                    maxIndex = j;
                }
            }
            topItems.add(items.get(maxIndex) + "->" + counts.get(maxIndex));

            temp.set(maxIndex,-1);
        }
        return topItems;
    }
}
