package io.github.jogo;

import com.badlogic.gdx.math.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Room {
    public List<Rectangle> obstacles = new ArrayList<>();
    public List<Enemy> enemies = new ArrayList<>();
    private Random random = new Random();

    public void generate() {
        obstacles.clear();
        enemies.clear();

        // Obstáculos aleatórios
        int numObs = 3 + random.nextInt(4); // 3-6
        for (int i = 0; i < numObs; i++) {
            float x = random.nextInt(700);
            float y = random.nextInt(500);
            obstacles.add(new Rectangle(x, y, 32, 32));
        }

        // Inimigos aleatórios
        int numEnemies = 1 + random.nextInt(4); // 1-4
        for (int i = 0; i < numEnemies; i++) {
            float x = random.nextInt(700);
            float y = random.nextInt(500);
            enemies.add(new Enemy(x, y));
        }
    }

    public void dispose() {
        for (Enemy e : enemies) e.dispose();
    }
}
