package io.github.jogo;

import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;
import java.util.List;

public class Room {
    private List<Enemy> enemies = new ArrayList<>();
    private List<Wall> walls = new ArrayList<>();
    private Player player;

    public Room(Player player) {
        this.player = player;
    }

    public void spawnEnemy(Enemy e) {
        enemies.add(e);
    }

    public void addWall(Wall wall) {
        walls.add(wall);
    }

    public boolean isCollidingWithWalls(Vector2 pos) {
        for (Wall wall : walls) {
            if (wall.getBounds().contains(pos)) {
                return true;
            }
        }
        return false;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public List<Wall> getWalls() {
        return walls;
    }

    public Player getPlayer() {
        return player;
    }
}