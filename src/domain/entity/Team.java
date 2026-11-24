class Team {
    
    private int id;
    private int score;
    private Robot[] robots;
    private Color color;

    Team(double id, Robot[] robots, Color color) {
        this.id = id;
        this.score = 0;
        this.robots = robots;
        this.color = color;
    }

    public boolean contains(Robot robot) {
        for (Robot r : this.robots) {
            if (r == robot) return true;
        }
        return false;
    }

    public int getId() {
        return this.id;
    }

    public int getScore() {
        return this.score;
    }

    public Robot[] getRobots() {
        return this.robots;
    }

    public Color getColor() {
        return this.color;
    }
} 