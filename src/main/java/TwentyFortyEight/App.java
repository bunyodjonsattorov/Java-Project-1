package TwentyFortyEight;

import processing.core.PApplet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class App extends PApplet {

    // Grid and visual settings
    private int GRID_SIZE;
    private final int CELL_SIZE = 100;
    private int[][] board;
    private boolean gameOver;
    private int startTime;
    private int endTime;

    // For random tile generation
    public static Random random = new Random();

    // Animation control
    private boolean animating = false;
    private int animationFrame = 0;
    private final int MAX_ANIMATION_FRAMES = 5;

    // Store tiles for animation
    private ArrayList<Tile> tiles = new ArrayList<>();
    private int[][] targetBoard;

    public App() {}

    @Override
    public void settings() {
        if (args != null && args.length > 0) {
            try {
                int n = Integer.parseInt(args[0]);
                GRID_SIZE = Math.max(2, n);
            } catch (NumberFormatException e) {
                GRID_SIZE = 4;
            }
        } else {
            GRID_SIZE = 4;
        }
        size(GRID_SIZE * CELL_SIZE, GRID_SIZE * CELL_SIZE);
    }

    @Override
    public void setup() {
        frameRate(30);
        resetGame();
    }

    private void resetGame() {
        board = new int[GRID_SIZE][GRID_SIZE];
        tiles.clear();
        addRandomTile();
        addRandomTile();
        gameOver = false;
        startTime = millis();
    }

    private void addRandomTile() {
        ArrayList<Cell> empty = new ArrayList<>();
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (board[i][j] == 0) empty.add(new Cell(i, j));
            }
        }
        if (!empty.isEmpty()) {
            Cell cell = empty.get(random.nextInt(empty.size()));
            int value = random.nextFloat() < 0.5f ? 2 : 4;
            board[cell.getX()][cell.getY()] = value;
            tiles.add(new Tile(value, cell.getY() * CELL_SIZE, cell.getX() * CELL_SIZE,
                    cell.getY() * CELL_SIZE, cell.getX() * CELL_SIZE));
        }
    }

    private int[] compressAndMergeRow(int[] row) {
        ArrayList<Integer> result = new ArrayList<>();
        for (int num : row) if (num != 0) result.add(num);
        for (int i = 0; i < result.size() - 1; i++) {
            if (result.get(i).equals(result.get(i + 1))) {
                result.set(i, result.get(i) * 2);
                result.remove(i + 1);
                result.add(0);
            }
        }
        while (result.size() < GRID_SIZE) result.add(0);
        return result.stream().mapToInt(i -> i).toArray();
    }

    private void moveLeft() {
        targetBoard = new int[GRID_SIZE][GRID_SIZE];
        ArrayList<Tile> newTiles = new ArrayList<>();
        boolean shouldAnimate = false;
        for (int i = 0; i < GRID_SIZE; i++) {
            int[] newRow = compressAndMergeRow(board[i]);
            targetBoard[i] = newRow;
            if (!Arrays.equals(board[i], newRow)) shouldAnimate = true;
            int targetJ = 0;
            for (int j = 0; j < GRID_SIZE && targetJ < GRID_SIZE; j++) {
                if (board[i][j] == 0) continue;
                if (targetJ < GRID_SIZE - 1 && board[i][j] == newRow[targetJ + 1]) {
                    newTiles.add(new Tile(newRow[targetJ], j * CELL_SIZE, i * CELL_SIZE,
                            targetJ * CELL_SIZE, i * CELL_SIZE));
                    j++;
                    targetJ++;
                } else {
                    newTiles.add(new Tile(board[i][j], j * CELL_SIZE, i * CELL_SIZE,
                            targetJ * CELL_SIZE, i * CELL_SIZE));
                    targetJ++;
                }
            }
        }
        if (shouldAnimate) {
            tiles = newTiles;
            animating = true;
            animationFrame = 0;
        }
    }

    private void moveRight() {
        targetBoard = new int[GRID_SIZE][GRID_SIZE];
        ArrayList<Tile> newTiles = new ArrayList<>();
        boolean shouldAnimate = false;
        for (int i = 0; i < GRID_SIZE; i++) {
            int[] reversed = reverseArray(board[i]);
            int[] merged = compressAndMergeRow(reversed);
            int[] newRow = reverseArray(merged);
            targetBoard[i] = newRow;
            if (!Arrays.equals(board[i], newRow)) shouldAnimate = true;
            int targetJ = GRID_SIZE - 1;
            for (int j = GRID_SIZE - 1; j >= 0 && targetJ >= 0; j--) {
                if (board[i][j] == 0) continue;
                if (targetJ > 0 && board[i][j] == newRow[targetJ - 1]) {
                    newTiles.add(new Tile(newRow[targetJ], j * CELL_SIZE, i * CELL_SIZE,
                            targetJ * CELL_SIZE, i * CELL_SIZE));
                    j--;
                    targetJ--;
                } else {
                    newTiles.add(new Tile(board[i][j], j * CELL_SIZE, i * CELL_SIZE,
                            targetJ * CELL_SIZE, i * CELL_SIZE));
                    targetJ--;
                }
            }
        }
        if (shouldAnimate) {
            tiles = newTiles;
            animating = true;
            animationFrame = 0;
        }
    }

    private void moveUp() {
        targetBoard = new int[GRID_SIZE][GRID_SIZE];
        ArrayList<Tile> newTiles = new ArrayList<>();
        boolean shouldAnimate = false;
        for (int j = 0; j < GRID_SIZE; j++) {
            int[] col = new int[GRID_SIZE];
            for (int i = 0; i < GRID_SIZE; i++) col[i] = board[i][j];
            int[] newCol = compressAndMergeRow(col);
            for (int i = 0; i < GRID_SIZE; i++) targetBoard[i][j] = newCol[i];
            if (!Arrays.equals(col, newCol)) shouldAnimate = true;
            int targetI = 0;
            for (int i = 0; i < GRID_SIZE && targetI < GRID_SIZE; i++) {
                if (board[i][j] == 0) continue;
                if (targetI < GRID_SIZE - 1 && board[i][j] == newCol[targetI + 1]) {
                    newTiles.add(new Tile(newCol[targetI], j * CELL_SIZE, i * CELL_SIZE,
                            j * CELL_SIZE, targetI * CELL_SIZE));
                    i++;
                    targetI++;
                } else {
                    newTiles.add(new Tile(board[i][j], j * CELL_SIZE, i * CELL_SIZE,
                            j * CELL_SIZE, targetI * CELL_SIZE));
                    targetI++;
                }
            }
        }
        if (shouldAnimate) {
            tiles = newTiles;
            animating = true;
            animationFrame = 0;
        }
    }

    private void moveDown() {
        targetBoard = new int[GRID_SIZE][GRID_SIZE];
        ArrayList<Tile> newTiles = new ArrayList<>();
        boolean shouldAnimate = false;
        for (int j = 0; j < GRID_SIZE; j++) {
            int[] col = new int[GRID_SIZE];
            for (int i = 0; i < GRID_SIZE; i++) col[i] = board[i][j];
            int[] newCol = reverseArray(compressAndMergeRow(reverseArray(col)));
            for (int i = 0; i < GRID_SIZE; i++) targetBoard[i][j] = newCol[i];
            if (!Arrays.equals(col, newCol)) shouldAnimate = true;
            int targetI = GRID_SIZE - 1;
            for (int i = GRID_SIZE - 1; i >= 0 && targetI >= 0; i--) {
                if (board[i][j] == 0) continue;
                if (targetI > 0 && board[i][j] == newCol[targetI - 1]) {
                    newTiles.add(new Tile(newCol[targetI], j * CELL_SIZE, i * CELL_SIZE,
                            j * CELL_SIZE, targetI * CELL_SIZE));
                    i--;
                    targetI--;
                } else {
                    newTiles.add(new Tile(board[i][j], j * CELL_SIZE, i * CELL_SIZE,
                            j * CELL_SIZE, targetI * CELL_SIZE));
                    targetI--;
                }
            }
        }
        if (shouldAnimate) {
            tiles = newTiles;
            animating = true;
            animationFrame = 0;
        }
    }

    private int[] reverseArray(int[] arr) {
        int[] out = new int[arr.length];
        for (int i = 0; i < arr.length; i++) out[i] = arr[arr.length - 1 - i];
        return out;
    }

    private boolean isGameOver() {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (board[i][j] == 0) return false;
                if (j < GRID_SIZE - 1 && board[i][j] == board[i][j + 1]) return false;
                if (i < GRID_SIZE - 1 && board[i][j] == board[i + 1][j]) return false;
            }
        }
        return true;
    }

    @Override
    public void keyPressed() {
        if (key == 'r') {
            resetGame();
            return;
        }
        if (!gameOver && key == CODED && !animating) {
            if (keyCode == LEFT) moveLeft();
            else if (keyCode == RIGHT) moveRight();
            else if (keyCode == UP) moveUp();
            else if (keyCode == DOWN) moveDown();
        }
    }

    @Override
    public void mousePressed() {
        if (!gameOver && !animating) {
            int row = mouseY / CELL_SIZE;
            int col = mouseX / CELL_SIZE;
            if (row < GRID_SIZE && col < GRID_SIZE && board[row][col] == 0) {
                int value = random.nextFloat() < 0.5f ? 2 : 4;
                board[row][col] = value;
                tiles.add(new Tile(value, col * CELL_SIZE, row * CELL_SIZE,
                        col * CELL_SIZE, row * CELL_SIZE));
            }
        }
    }

    @Override
    public void draw() {
        background(255);

        // Draw empty grid
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                fill(getTileColor(0));
                rect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }

        // Draw tiles
        if (animating) {
            float t = (float) animationFrame / MAX_ANIMATION_FRAMES;
            for (Tile tile : tiles) {
                tile.update(t);
                fill(getTileColor(tile.value));
                rect(tile.x, tile.y, CELL_SIZE, CELL_SIZE);
                fill(0);
                textAlign(CENTER, CENTER);
                textSize(32);
                text(tile.value, tile.x + CELL_SIZE / 2.0f, tile.y + CELL_SIZE / 2.0f);
            }
        } else {
            for (Tile tile : tiles) {
                fill(getTileColor(tile.value));
                rect(tile.x, tile.y, CELL_SIZE, CELL_SIZE);
                fill(0);
                textAlign(CENTER, CENTER);
                textSize(32);
                text(tile.value, tile.x + CELL_SIZE / 2.0f, tile.y + CELL_SIZE / 2.0f);
            }
        }

        // Hover effect
        fill(255, 200);
        rect((mouseX / CELL_SIZE) * CELL_SIZE, (mouseY / CELL_SIZE) * CELL_SIZE, CELL_SIZE, CELL_SIZE);

        // Show timer
        int elapsed = (gameOver ? endTime : millis()) - startTime;
        fill(0);
        textAlign(RIGHT, TOP);
        textSize(24);
        text((elapsed / 1000) + " s", width - 10, 10);

        // Animation logic
        if (animating) {
            animationFrame++;
            if (animationFrame >= MAX_ANIMATION_FRAMES) {
                animating = false;
                if (targetBoard != null) {
                    board = new int[GRID_SIZE][GRID_SIZE];
                    tiles.clear();
                    for (int i = 0; i < GRID_SIZE; i++) {
                        for (int j = 0; j < GRID_SIZE; j++) {
                            board[i][j] = targetBoard[i][j];
                            if (board[i][j] != 0) {
                                tiles.add(new Tile(board[i][j], j * CELL_SIZE, i * CELL_SIZE,
                                        j * CELL_SIZE, i * CELL_SIZE));
                            }
                        }
                    }
                    targetBoard = null;
                }
                addRandomTile();
                if (isGameOver()) {
                    gameOver = true;
                    endTime = millis();
                }
            }
        }

        // Game over message
        if (gameOver) {
            textAlign(CENTER, CENTER);
            fill(255, 0, 0);
            textSize(40);
            text("GAME OVER", width / 2, height / 2);
        }
    }

    private int getTileColor(int value) {
        switch (value) {
            case 0:    return color(205, 193, 180);
            case 2:    return color(238, 228, 218);
            case 4:    return color(237, 224, 200);
            case 8:    return color(242, 177, 121);
            case 16:   return color(245, 149, 99);
            case 32:   return color(246, 124, 95);
            case 64:   return color(246, 94, 59);
            case 128:  return color(237, 207, 114);
            case 256:  return color(237, 204, 97);
            case 512:  return color(237, 200, 80);
            case 1024: return color(237, 197, 63);
            case 2048: return color(237, 194, 46);
            default:   return color(237, 194, 46);
        }
    }

    public static void main(String[] args) {
        PApplet.main("TwentyFortyEight.App", args);
    }
}
