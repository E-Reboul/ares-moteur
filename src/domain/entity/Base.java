import java.util.ArrayList;
import java.util.List;

public class Base {

    private List<Crystal> crystals;
    private int capacity;

    public Base(int capacity) {
        this.crystals = new ArrayList<>();
        this.capacity = capacity;
    }

    public List<Crystal> storeIn(List<Crystal> incoming) {
        int freeSpace = this.capacity - this.crystals.size();
        int toStore = Math.min(freeSpace, incoming.size());

        this.crystals.addAll(incoming.subList(0, toStore));

        return new ArrayList<>(incoming.subList(toStore, incoming.size()));
    }

    public boolean isFull() {
        return this.crystals.size() >= this.capacity;
    }

    public int getAmountCrystals() {
        return this.crystals.size();
    }
}