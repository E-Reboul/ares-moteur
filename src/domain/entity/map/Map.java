import java.util.ArrayList;
import java.util.List;

public class Map {

    private final int height;
    private final int width;
    private final int columns;
    private final int rows;
    private final List<Cell> cells;

    public Map(int height, int width, int columns, int rows) {
        this.height = height;
        this.width = width;
        this.columns = columns;
        this.rows = rows;
        this.cells = new ArrayList<>();
    }

    public void generateCells() {
        Cell cell;
        double cellHeight = (double) this.height / this.rows;
        double cellWidth = (double) this.width / this.columns;
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.columns; j++) {
                double cellX = cellWidth * j;
                double cellY = cellHeight * i;
                cell = new Cell(cellX, cellY, cellHeight, cellWidth);
                this.cells.add(cell);
            }
        }
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public int getColumns() {
        return this.columns;
    }

    public int getRows() {
        return this.rows;
    }

    public List<Cell> getCells() {
        return List.copyOf(this.cells);
    }
}
