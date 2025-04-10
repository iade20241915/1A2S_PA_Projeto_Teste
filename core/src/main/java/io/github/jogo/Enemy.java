package io.github.jogo;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Enemy {
    private Vector2 position;
    private Texture texture;
    private float speed = 40;

    public Enemy(float x, float y) {
        this.position = new Vector2(x, y);
        this.texture = new Texture("images/skeleton.png");
    }

    public void update(float delta, Room room) {
        if (room.getPlayer().getHealth() <= 0) return;

        Vector2 dir = new Vector2(room.getPlayer().getPosition()).sub(position).nor();
        Vector2 newPos = new Vector2(position).mulAdd(dir, speed * delta);

        if (!room.isCollidingWithWalls(newPos)) {
            position.set(newPos);
        }

        if (position.dst(room.getPlayer().getPosition()) < 20) {
            room.getPlayer().takeDamage(10);
        }
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y);
    }
}