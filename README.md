# 2048 Game

A Java implementation of the popular 2048 puzzle game using the Processing library. This project features smooth animations, configurable grid sizes, and an intuitive user interface.

## 🎮 Game Overview

2048 is a sliding tile puzzle game where you combine numbered tiles to reach the 2048 tile. The game is played on a 4x4 grid (configurable), and you move tiles in four directions to merge identical adjacent tiles.

## ✨ Features

- **Smooth Animations**: Tiles animate smoothly when moving and merging
- **Configurable Grid Size**: Play on different grid sizes (minimum 2x2)
- **Real-time Timer**: Track your game time
- **Interactive Controls**: 
  - Arrow keys for tile movement
  - Mouse click to manually place tiles (debug feature)
  - 'R' key to reset the game
- **Visual Feedback**: Color-coded tiles and hover effects
- **Game Over Detection**: Automatic game over when no moves are possible

## 🚀 Getting Started

### Prerequisites

- Java 8 or higher
- Gradle 5.6.3 or higher

### Installation

1. Clone the repository:
```bash
git clone <your-repo-url>
cd "Java Project 1"
```

2. Build the project:
```bash
./gradlew build
```

3. Run the game:
```bash
./gradlew run
```

### Command Line Options

You can specify a custom grid size when running the game:

```bash
./gradlew run --args="5"  # Play on a 5x5 grid
./gradlew run --args="3"  # Play on a 3x3 grid
```

## 🎯 How to Play

1. **Objective**: Combine tiles with the same number to create tiles with higher numbers
2. **Movement**: Use arrow keys to slide all tiles in that direction
3. **Merging**: When two tiles with the same number touch, they merge into one tile with double the value
4. **Goal**: Try to create a tile with the value 2048 (or higher!)
5. **Game Over**: The game ends when the grid is full and no more moves are possible

## 🎨 Controls

| Key/Input | Action |
|-----------|--------|
| `↑` `↓` `←` `→` | Move tiles in respective direction |
| `R` | Reset the game |
| `Mouse Click` | Place a random tile (2 or 4) on empty cell |
| `ESC` | Close the game |

## 🏗️ Project Structure

```
src/
├── main/
│   ├── java/TwentyFortyEight/
│   │   ├── App.java          # Main game controller
│   │   ├── Cell.java         # Grid position data class
│   │   └── Tile.java         # Tile animation and rendering
│   └── resources/TwentyFortyEight/
│       └── 8.png            # Game assets
├── build.gradle             # Build configuration
└── README.md               # This file
```

## 🔧 Technical Details

### Architecture

- **App.java**: Main game controller extending PApplet (Processing)
- **Cell.java**: Immutable data class for grid coordinates
- **Tile.java**: Handles tile rendering and smooth animations

### Key Components

- **Game State Management**: 2D array representing the game board
- **Animation System**: Linear interpolation for smooth tile movements
- **Movement Logic**: Compress and merge algorithm for tile combinations
- **Input Handling**: Keyboard and mouse event processing

### Dependencies

- **Processing Core 3.3.7**: Graphics and animation framework
- **Google Guava 28.0**: Utility library
- **JUnit Jupiter 5.6.2**: Testing framework

## 🛠️ Development

### Building

```bash
# Build the project
./gradlew build

# Run tests
./gradlew test

# Generate code coverage report
./gradlew jacocoTestReport
```

### Creating JAR

```bash
./gradlew jar
```

The executable JAR will be created in `build/libs/` directory.

## 🎨 Customization

### Changing Grid Size

Modify the `GRID_SIZE` variable in `App.java` or pass it as a command line argument.

### Adjusting Animation Speed

Modify `MAX_ANIMATION_FRAMES` in `App.java` to change animation duration.

### Custom Colors

Update the `getTileColor()` method in `App.java` to customize tile colors.

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 🙏 Acknowledgments

- Inspired by the original 2048 game by Gabriele Cirulli
- Built with the Processing library
- Uses Gradle for build management

---

**Enjoy playing 2048! 🎮**
