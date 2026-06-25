
# Sky Defense

A 2D arcade game built in Java with Swing for the **Object-Oriented Paradigm** course. You control a plane that must dodge missiles launched by squadrons of enemy drones, surviving as many levels as possible.

## How to Play

The plane moves along the lower part of the screen while drones fly across the top firing missiles that explode at different altitudes. Each explosion affects you based on distance: from far away you score points, up close you lose energy, and a direct hit costs you a life.

### Controls

| Key                     | Action                  |
|-------------------------|-------------------------|
| `W A S D` or arrow keys | Move the plane          |
| `ESC`                   | Pause / resume          |
| `ENTER`                 | Restart after game over |
| `M`                     | Mute                    |

### Rules

- You start with **3 lives** and **100 energy**.
- If energy reaches 0, you lose a life and energy is fully restored.
- Every **1000 points** grants an extra life (up to level 4).
- Completing a level awards a **+300 point** bonus.
- There are **5 levels**: in each one, drones and missiles are 15% faster and shoot more frequently. Level 5 is **endless** — survive as long as you can.

## Running the Game

Requirements: JDK 17 or higher.

1. Clone the repository and open it in IntelliJ IDEA.
2. Mark `src` as the source folder (already configured in the `.iml` file).
3. Run the `main.Main` class.

## Architecture

The project follows an **MVC-style** separation:

```text
src/
├── main/
│   ├── Main.java              # Entry point
│   ├── Controller/
│   │   ├── GameController     # Core game logic (states, levels, collisions)
│   │   ├── GamePanel          # Rendering and 60 FPS game loop (Swing Timer + delta time)
│   │   ├── KeyHandler         # Keyboard input
│   │   └── InputSource        # Input interface (enables testing without a real keyboard)
│   ├── Model/
│   │   ├── Entity             # Abstract base class (position, size, speed, hitbox)
│   │   ├── Plane              # Player's plane (movement, energy)
│   │   ├── Drone / Squadron   # Enemies and their management (spawning, limits, direction)
│   │   ├── Missile / Explosion # Projectiles and distance-based impact resolution
│   │   ├── Player             # Lives, score and extra lives
│   │   ├── Level              # Difficulty progression
│   │   └── Collidable / Drawable / Updatable  # Behavior interfaces
│   └── audio/
│       ├── SoundManager       # Sound effects and music playback
│       ├── AudioPlayer        # Audio interface (enables testing without real sound)
│       └── Sound              # Enum of game sounds
└── test/
└── java/                  # Unit tests (JUnit 5)
```

### Design Decisions

- **Delta time**: movement is calculated using the time elapsed between frames rather than fixed ticks, keeping speed consistent regardless of frame rate.
- **Dependency injection**: `GameController` receives `InputSource` and `AudioPlayer` through its constructor, decoupling game logic from the real keyboard and audio system.
- **Decoupled impact logic**: `Explosion.resolveImpact()` returns an immutable `ExplosionResult` (score, damage, lethality) instead of modifying state directly.

## Tests

Unit tests use **JUnit 5** and cover the full model (`Plane`, `Player`, `Level`, `Missile`, `Explosion`, `Squadron`) as well as the `GameController` through input and audio fakes. Run them from IntelliJ by right-clicking `src/test` → *Run All Tests*.
