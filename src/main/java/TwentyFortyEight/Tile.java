package TwentyFortyEight;

public class Tile {
    int value;
    float x, y;
    float startX, startY;
    float targetX, targetY;
    boolean merged;

    public Tile(int value, float x, float y, float targetX, float targetY) {
        this.value = value;
        this.x = x;
        this.y = y;
        this.startX = x;
        this.startY = y;
        this.targetX = targetX;
        this.targetY = targetY;
        this.merged = false;
    }

    public void update(float t) {
        x = lerp(startX, targetX, t);
        y = lerp(startY, targetY, t);
    }

    private float lerp(float start, float stop, float amt) {
        return start + (stop - start) * amt;
    }
}
