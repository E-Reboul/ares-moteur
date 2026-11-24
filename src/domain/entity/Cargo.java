import java.util.ArrayList;
import java.util.List;

public class Cargo {

    private int capacity;
    private List<Crystal> crystals;

    public Cargo(int capacity) {
        this.capacity = capacity;
        this.crystals = new ArrayList<>();
    }

    public boolean addCrystal(Crystal crystal) {
        if (this.crystals.size() >= this.capacity) {
            return false;
        }
        this.crystals.add(crystal);
        return true;
    }

    public void deliverTo(Base base) {
        this.crystals = base.storeIn(this.crystals);
    }

    public List<Crystal> getCrystals() {
        return new ArrayList<>(this.crystals);
    }
}