class Robot extends Element implements Movable, Damageable {

    private int hp;
    private int energy;
    private double direction;
    private double vision;
    private double range;
    private Cargo cargo;

    Robot(int id, int x, int y, Color color, int hp, int energy, double vision, double range, Cargo cargo) {
        super(id, x, y, color);
        this.hp = hp;
        this.energy = energy;
        this.vision = vision;
        this.range = range;
        this.cargo = cargo;
    }

    public void move(double distance) {
        
    }

    public void takeDamage(int amount) {
        if (amount <= 0) return;
        
        this.hp -= amount;

        if (this.hp < 0) {
            this.hp = 0;
        }
    }

    public boolean isDestroyed() {
        return this.hp <= 0;
    }

    public int getHp() {
        return this.hp;
    }


}