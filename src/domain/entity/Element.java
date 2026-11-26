class Element {

    private double id;
    private double x;
    private double y;
    private Color color;

    Element(double id, double x, double y, Color color) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public getId() {
        return this.id;
    }
    
    public getX() {
        return this.x;
    }

    public getY() {
        return this.y;
    }

    public getColor() {
        return this.color;
    }
}